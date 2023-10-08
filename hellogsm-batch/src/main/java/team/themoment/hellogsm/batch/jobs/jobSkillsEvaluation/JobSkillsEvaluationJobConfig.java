package team.themoment.hellogsm.batch.jobs.jobSkillsEvaluation;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import team.themoment.hellogsm.batch.common.EvaluationDecisionProvider;
import team.themoment.hellogsm.batch.common.EvaluationResult;
import team.themoment.hellogsm.entity.common.util.OptionalUtils;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

@Slf4j
@Configuration
public class JobSkillsEvaluationJobConfig {
    public final static int CHUNK_SIZE = 10;
    public final static String JOB_NAME = "jobSkillsEvaluationJob";
    public final static String BEAN_PREFIX = JOB_NAME + "_";

    public JobSkillsEvaluationJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            EntityManagerFactory entityManagerFactory,
            @Qualifier(BEAN_PREFIX + "decision_provider") EvaluationDecisionProvider decisionProvider,
            JobSkillsEvaluationParameter parameter) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.decisionProvider = decisionProvider;
        this.parameter = parameter;
    }

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final EvaluationDecisionProvider decisionProvider;
    private final JobSkillsEvaluationParameter parameter;

    @Bean(BEAN_PREFIX + "decision_provider")
    @Qualifier(BEAN_PREFIX + "decision_provider")
    @JobScope
    public EvaluationDecisionProvider decisionProvider() {
        return new EvaluationDecisionProvider(parameter.getGeneral(),
                parameter.getSocial(), parameter.getSpecialVeterans(), parameter.getSpecialAdmission());
    }

    @Bean(BEAN_PREFIX + "parameter")
    @JobScope
    public JobSkillsEvaluationParameter parameter(
            @Value("#{jobParameters[VERSION]}") Long version,
            @Value("#{jobParameters[GENERAL]}") Integer general,
            @Value("#{jobParameters[SOCIAL]}") Integer social,
            @Value("#{jobParameters[SPECIAL_VETERANS]}") Integer specialVeterans,
            @Value("#{jobParameters[SPECIAL_ADMISSION]}") Integer specialAdmission
    ) {
        return new JobSkillsEvaluationParameter(version, general, social, specialVeterans, specialAdmission);
    }

    @Bean(JOB_NAME)
    public Job jobSkillsEvaluationJobConfig() {
        return new JobBuilder(JOB_NAME, this.jobRepository)
                .preventRestart() // 재시작 false
                .start(jobSkillsEvaluationStep()) // 직무적성 소양평가(2차 평가) Step 시작
                .on("FAILED") // 만약 할당 Step을 실패하면
                .to(clearJobSkillsEvaluationStep()) // 초기화 Step
                .on("FAILED") // 초기화 Step 실패 시
                .fail() // Job fail
                .on("*") // 초기화 Step 결과가 FAILED를 제외한 모든 경우에
                .end() // Flow 종료
                .from(jobSkillsEvaluationStep()) // 할당 Job으로부터
                .on("*") // FAILED를 제외한 모든 경우에
                .end() // Flow 종료
                .end() // Job 종료
                .build();
    }

    @Bean
    @JobScope
    public Step jobSkillsEvaluationStep() {
        return new StepBuilder(BEAN_PREFIX + "jobSkillsEvaluationStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("beforeStep jobSkillsEvaluationStep");
                        log.info("beforeStep EvaluationDecisionProvider - Status : {}", decisionProvider);
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        final String exitCode = stepExecution.getExitStatus().getExitCode();
                        log.info("afterStep jobSkillsEvaluationStep - Status : {}", exitCode);
                        log.info("afterStep EvaluationDecisionProvider - Status : {}", decisionProvider);
                        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()))
                            log.error("jobSkillsEvaluationStep FAIL!!");
                        return null;
                    }
                })
                .reader(firstEvalPassedApplicationOrderByScoreIR())
                .processor(jobSkillsEvaluationIP())
                .writer(admissionStatusIW())
                .build();
    }

    @Bean
    @JobScope
    public Step clearJobSkillsEvaluationStep() {
        return new StepBuilder(BEAN_PREFIX + "clearJobSkillsEvaluationStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("beforeStep clearJobSkillsEvaluationStep");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        final String exitCode = stepExecution.getExitStatus().getExitCode();
                        log.info("afterStep clearJobSkillsEvaluationStep - Status : {}", exitCode);
                        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()))
                            log.error("clearJobSkillsEvaluationStep FAIL!!");
                        return null;
                    }
                })
                .reader(firstEvalPassedApplicationOrderByScoreIR())
                .processor(clearJobSkillsEvaluationIP())
                .writer(admissionStatusIW())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Application> firstEvalPassedApplicationOrderByScoreIR() {
        return new JpaPagingItemReaderBuilder<Application>()
                .name(BEAN_PREFIX + "finalSubmittedApplicationIR")
                .entityManagerFactory(this.entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString(
                        "SELECT a " +
                                "FROM Application a " + // TODO 테스트 코드 추가하기
                                "WHERE a.admissionStatus.firstEvaluation = 'PASS' AND a.admissionStatus.screeningFirstEvaluationAt IS NOT NULL " +
                                "ORDER BY a.admissionGrade.totalScore DESC ," +
                                "(a.admissionGrade.curricularSubtotalScore + a.admissionGrade.artisticScore) DESC, " +
                                "a.admissionGrade.grade3Semester1Score DESC, " +
                                "(a.admissionGrade.grade2Semester1Score + a.admissionGrade.grade2Semester2Score) DESC, " +
                                "a.admissionGrade.grade2Semester2Score DESC, " +
                                "a.admissionGrade.grade2Semester1Score DESC, " +
                                "a.admissionGrade.extracurricularSubtotalScore DESC, " +
                                "a.admissionInfo.applicantBirth ASC"
                )
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Application, AdmissionStatus> jobSkillsEvaluationIP() {
        return application -> {
            AdmissionStatus oldAdmissionStatus = application.getAdmissionStatus();
            Screening screening = oldAdmissionStatus.getScreeningFirstEvaluationAt().get();

            EvaluationResult rs = decisionProvider.evaluate(screening);
            if (rs.evaluationStatus().equals(EvaluationStatus.FALL)) {
                rs = new EvaluationResult(
                        EvaluationStatus.FALL,
                        null
                );
            }

            return newAdmissionStatus(oldAdmissionStatus, rs);
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<Application, AdmissionStatus> clearJobSkillsEvaluationIP() {
        return application -> clearAdmissionStatus(application.getAdmissionStatus());
    }

    @Bean(BEAN_PREFIX + "admissionStatusIW")
    @StepScope
    public JpaItemWriter<AdmissionStatus> admissionStatusIW() {
        return new JpaItemWriterBuilder<AdmissionStatus>()
                .usePersist(false) // update
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }

    private AdmissionStatus newAdmissionStatus(AdmissionStatus admissionStatus, EvaluationResult result) {
        AdmissionStatus newAdmissionStatus = AdmissionStatus.builder()
                .id(admissionStatus.getId())
                .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                .isPrintsArrived(admissionStatus.isPrintsArrived())
                .firstEvaluation(admissionStatus.getFirstEvaluation())
                .secondEvaluation(result.evaluationStatus())
                .screeningFirstEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningFirstEvaluationAt()))
                .screeningSecondEvaluationAt(result.screening())
                .registrationNumber(OptionalUtils.fromOptional(admissionStatus.getRegistrationNumber()))
                .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                .finalMajor(OptionalUtils.fromOptional(admissionStatus.getFinalMajor()))
                .build();
        return newAdmissionStatus;
    }

    private AdmissionStatus clearAdmissionStatus(AdmissionStatus admissionStatus) {
        AdmissionStatus clearAdmissionStatus = AdmissionStatus.builder()
                .id(admissionStatus.getId())
                .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                .isPrintsArrived(admissionStatus.isPrintsArrived())
                .firstEvaluation(admissionStatus.getFirstEvaluation())
                .secondEvaluation(EvaluationStatus.NOT_YET)
                .screeningFirstEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningFirstEvaluationAt()))
                .screeningSecondEvaluationAt(null)
                .registrationNumber(OptionalUtils.fromOptional(admissionStatus.getRegistrationNumber()))
                .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                .finalMajor(OptionalUtils.fromOptional(admissionStatus.getFinalMajor()))
                .build();
        return clearAdmissionStatus;
    }
}
