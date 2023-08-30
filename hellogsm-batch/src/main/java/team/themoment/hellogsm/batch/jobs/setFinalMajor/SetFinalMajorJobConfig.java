package team.themoment.hellogsm.batch.jobs.setFinalMajor;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import team.themoment.hellogsm.entity.common.util.OptionalUtils;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.DesiredMajor;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SetFinalMajorJobConfig {
    public final static int CHUNK_SIZE = 10;
    public final static String JOB_NAME = "setFinalMajorJob";
    public final static String BEAN_PREFIX = JOB_NAME + "_";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final SetFinalMajorParameter parameter;
    private final MajorCapacity majorCapacity;

    @Bean(BEAN_PREFIX + "major_capacity")
    @JobScope
    public MajorCapacity majorCapacity() {
        return new MajorCapacity(parameter.getGiot(), parameter.getGsw(), parameter.getGai(),
                parameter.getSiot(), parameter.getSsw(), parameter.getSai());
    }

    @Bean(BEAN_PREFIX + "parameter")
    @JobScope
    public SetFinalMajorParameter parameter(
            @Value("#{jobParameters[VERSION]}") Long version,
            @Value("#{jobParameters[G_IOT]}") Integer giot,
            @Value("#{jobParameters[G_SW]}") Integer gsw,
            @Value("#{jobParameters[G_AI]}") Integer gai,
            @Value("#{jobParameters[S_IOT]}") Integer siot,
            @Value("#{jobParameters[S_SW]}") Integer ssw,
            @Value("#{jobParameters[S_AI]}") Integer sai
    ) {
        return new SetFinalMajorParameter(version, giot, gsw, gai, siot, ssw, sai);
    }

    @Bean(JOB_NAME)
    public Job setFinalMajorJobConfig() {
        return new JobBuilder(JOB_NAME, this.jobRepository)
                .preventRestart() // 재시작 false
                .start(setFinalMajorStep()) // 최종학과 할당 Step 시작
                    .on("FAILED") // 만약 할당 Step을 실패하면
                    .to(clearFinalMajorStep()) // 초기화 Step
                        .on("FAILED") // 초기화 Step 실패 시
                        .fail() // Job fail
                        .on("*") // 초기화 Step 결과가 FAILED를 제외한 모든 경우에
                        .end() // Flow 종료
                .from(setFinalMajorStep()) // 할당 Job으로부터
                    .on("*") // FAILED를 제외한 모든 경우에
                    .end() // Flow 종료
                .end() // Job 종료
                .build();
    }

    @Bean
    @JobScope
    public Step clearFinalMajorStep() {
        return new StepBuilder(BEAN_PREFIX + " ", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("beforeStep clearFinalMajorStep");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        final String exitCode = stepExecution.getExitStatus().getExitCode();
                        log.info("afterStep clearFinalMajorStep - Status : {}", exitCode);
                        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()))
                            log.error("clearFinalMajorStep FAIL!!");
                        return null;
                    }
                })
                .reader(finalPassedApplicationOrderByScoreIR())
                .processor(clearFinalMajorIP())
                .writer(finalMajorIW())
                .build();
    }

    @Bean
    @JobScope
    public Step setFinalMajorStep() {
        return new StepBuilder(BEAN_PREFIX + "setFinalMajorStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("beforeStep setFinalMajorStep");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        final String exitCode = stepExecution.getExitStatus().getExitCode();
                        log.info("afterStep setFinalMajorStep - Status : {}", exitCode);
                        log.info("afterStep majorCapacity - Status : {}", majorCapacity);
                        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()))
                            log.error("setFinalMajorStep FAIL!!");
                        return null; // ExitStatus을 수정하지 않고 그대로 리턴
                    }
                })
                .reader(finalPassedApplicationOrderByScoreIR())
                .processor(setFinalMajorIP())
                .writer(finalMajorIW())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Application> finalPassedApplicationOrderByScoreIR() {
        return new JpaPagingItemReaderBuilder<Application>()
                .name(BEAN_PREFIX + "finalPassedApplicationIR")
                .entityManagerFactory(this.entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString(
                        "SELECT a " +
                                "FROM Application a " +
                                "WHERE a.admissionStatus.secondEvaluation = 'PASS' " +
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
    public ItemProcessor<Application, AdmissionStatus> setFinalMajorIP() {
        return application -> {
            Screening screening = application.getAdmissionStatus().getScreeningSecondEvaluationAt().get();
            DesiredMajor desiredMajor = application.getAdmissionInfo().getDesiredMajor();

            Major finalMajor = majorCapacity.assignMajor(screening,desiredMajor);

            AdmissionStatus admissionStatus = application.getAdmissionStatus();
            AdmissionStatus newAdmissionStatus = AdmissionStatus.builder()
                    .id(admissionStatus.getId())
                    .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                    .isPrintsArrived(admissionStatus.isPrintsArrived())
                    .firstEvaluation(admissionStatus.getFirstEvaluation())
                    .secondEvaluation(admissionStatus.getSecondEvaluation())
                    .screeningFirstEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningFirstEvaluationAt()))
                    .screeningSecondEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningSecondEvaluationAt()))
                    .registrationNumber(OptionalUtils.fromOptional(admissionStatus.getRegistrationNumber()))
                    .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                    .finalMajor(finalMajor)
                    .build();
            return newAdmissionStatus;
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<Application, AdmissionStatus> clearFinalMajorIP() {
        return application -> {
            AdmissionStatus admissionStatus = application.getAdmissionStatus();
            AdmissionStatus clearAdmissionStatus = AdmissionStatus.builder()
                    .id(admissionStatus.getId())
                    .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                    .isPrintsArrived(admissionStatus.isPrintsArrived())
                    .firstEvaluation(admissionStatus.getFirstEvaluation())
                    .secondEvaluation(admissionStatus.getSecondEvaluation())
                    .screeningFirstEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningFirstEvaluationAt()))
                    .screeningSecondEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningSecondEvaluationAt()))
                    .registrationNumber(OptionalUtils.fromOptional(admissionStatus.getRegistrationNumber()))
                    .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                    .finalMajor(null)
                    .build();
            return clearAdmissionStatus;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<AdmissionStatus> finalMajorIW() {
        return new JpaItemWriterBuilder<AdmissionStatus>()
                .usePersist(false) // update
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }


}
