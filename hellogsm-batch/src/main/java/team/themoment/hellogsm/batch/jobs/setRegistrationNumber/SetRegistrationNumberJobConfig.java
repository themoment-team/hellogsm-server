package team.themoment.hellogsm.batch.jobs.setRegistrationNumber;

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
import team.themoment.hellogsm.batch.common.LoggingExceptionHandler;
import team.themoment.hellogsm.entity.common.util.OptionalUtils;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SetRegistrationNumberJobConfig {
    public final static int CHUNK_SIZE = 100;
    public final static String JOB_NAME = "setRegistrationNumberJob";
    public final static String BEAN_PREFIX = JOB_NAME + "_";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final RegistrationNumberSequence registrationNumberSequence;
    private final RegistrationNumberParameter parameter;

    @Bean(BEAN_PREFIX + "registrationNumberSequence")
    @JobScope
    public RegistrationNumberSequence registrationNumberSequence() {
        return new RegistrationNumberSequence();
    }

    @Bean(BEAN_PREFIX + "parameter")
    @JobScope
    public RegistrationNumberParameter parameter(
            @Value("#{jobParameters[VERSION]}") Long version
    ) {
        return new RegistrationNumberParameter(version);
    }

    @Bean(JOB_NAME)
    public Job setRegistrationNumberJobConfig() {
        return new JobBuilder(JOB_NAME, this.jobRepository)
                .preventRestart() // 재시작 false
                .start(setRegistrationNumberStep()) // 접수번호 할당 Job 시작
                .on("FAILED") // 만약 접수번호 할당 Job을 실패하면
                .to(clearRegistrationNumberStep()) // 접수번호 초기화 Step
                .on("FAILED") // 접수번호 초기화 실패 시
                .fail() // Job fail
                .on("*") // 접수번호 초기화 결과가 FAILED를 제외한 모든 경우에
                .end() // Flow 종료
                .from(setRegistrationNumberStep()) // 접수번호 할당 Job으로부터
                .on("*") // FAILED를 제외한 모든 경우에
                .end() // Flow 종료
                .end() // Job 종료
                .build();
    }

    @Bean
    @JobScope
    public Step clearRegistrationNumberStep() {
        return new StepBuilder(BEAN_PREFIX + "initStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("beforeStep clearRegistrationNumberStep"); //TODO 문구 수정
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        final String exitCode = stepExecution.getExitStatus().getExitCode();
                        log.info("afterStep clearRegistrationNumberStep - Status : {}", exitCode); //TODO 문구 수정
                        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()))
                            log.error("clearRegistrationNumberStep FAIL!!"); //TODO 문구 수정
                        return null;
                    }
                })
                .exceptionHandler(new LoggingExceptionHandler())
                .faultTolerant()
                .skipLimit(0)
                .noSkip(Exception.class)
                .reader(finalSubmittedApplicationIR())
                .processor(clearRegistrationNumberIP())
                .writer(admissionStatusIW())
                .transactionManager(this.platformTransactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Step setRegistrationNumberStep() {
        return new StepBuilder(BEAN_PREFIX + "setRegistrationNumberStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("beforeStep setRegistrationNumberStep"); //TODO 문구 수정
                        registrationNumberSequence.init();
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        final String exitCode = stepExecution.getExitStatus().getExitCode();
                        log.info("afterStep setRegistrationNumberStep - Status : {}", exitCode); //TODO 문구 수정
                        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()))
                            log.error("setRegistrationNumberStep FAIL!!"); //TODO 문구 수정
                        registrationNumberSequence.clear();
                        return null; // ExitStatus을 수정하지 않고 그대로 리턴
                    }
                })
                .exceptionHandler(new LoggingExceptionHandler())
                .faultTolerant()
                .skipLimit(0)
                .noSkip(Exception.class)
                .reader(finalSubmittedApplicationIR())
                .processor(setRegistrationNumberIP())
                .writer(admissionStatusIW())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Application> finalSubmittedApplicationIR() {
        return new JpaPagingItemReaderBuilder<Application>()
                .name(BEAN_PREFIX + "setRegistrationNumberItemReader")
                .entityManagerFactory(this.entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString(
                        "SELECT a " +
                                "FROM Application a " +
                                "WHERE a.admissionStatus.isPrintsArrived = true " +
                                "AND a.admissionStatus.isFinalSubmitted = true"
                )
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Application, AdmissionStatus> clearRegistrationNumberIP() {
        return application -> {
            AdmissionStatus admissionStatus = application.getAdmissionStatus();
            AdmissionStatus clearAdmissionStatus = AdmissionStatus.builder()
                    .id(admissionStatus.getId())
                    .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                    .isPrintsArrived(admissionStatus.isPrintsArrived())
                    .firstEvaluation(admissionStatus.getFirstEvaluation())
                    .secondEvaluation(admissionStatus.getSecondEvaluation())
                    .registrationNumber(null)
                    .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                    .finalMajor(OptionalUtils.fromOptional(admissionStatus.getFinalMajor()))
                    .build();
            return clearAdmissionStatus;
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<Application, AdmissionStatus> setRegistrationNumberIP() {
        return application -> {
            AdmissionStatus admissionStatus = application.getAdmissionStatus();
            Screening screening = application.getAdmissionInfo().getScreening();
            registrationNumberSequence.add(screening);
            Long registrationNumber = Long.valueOf(registrationNumberSequence.get(screening));
            AdmissionStatus newAdmissionStatus = AdmissionStatus.builder()
                    .id(admissionStatus.getId())
                    .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                    .isPrintsArrived(admissionStatus.isPrintsArrived())
                    .firstEvaluation(admissionStatus.getFirstEvaluation())
                    .secondEvaluation(admissionStatus.getSecondEvaluation())
                    .registrationNumber(registrationNumber)
                    .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                    .finalMajor(OptionalUtils.fromOptional(admissionStatus.getFinalMajor()))
                    .build();
            return newAdmissionStatus;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<AdmissionStatus> admissionStatusIW() {
        return new JpaItemWriterBuilder<AdmissionStatus>()
                .usePersist(false) // update
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }
}
