package team.themoment.hellogsm.batch.jobs.setRegistrationNumber;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
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
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    private final Parameter parameter;

    @Getter
    @AllArgsConstructor
    public class Parameter {
        @NonNull LocalDateTime dateTime;
    }

    @Bean(BEAN_PREFIX + "registrationNumberSequence")
    @JobScope
    public RegistrationNumberSequence registrationNumberSequence() {
        return new RegistrationNumberSequence();
    }

    @Bean(BEAN_PREFIX + "parameter")
    @JobScope
    public Parameter parameter(
            @Value("#{jobParameters[DATE_TIME]}") String dateTime
    ) {
        ZonedDateTime zonedDateTime =
                ZonedDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss-z"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        return new Parameter(localDateTime);
    }

    @Bean(JOB_NAME)
    public Job setRegistrationNumberJobConfig() {
        return new JobBuilder(JOB_NAME, this.jobRepository)
                .start(setRegistrationNumberStep())
                .build();
    }

    @Bean
    @JobScope
    public Step setRegistrationNumberStep() {
        return new StepBuilder(BEAN_PREFIX + "setRegistrationNumberStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        registrationNumberSequence.init();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        registrationNumberSequence.clear();
                    }
                })
                .reader(setRegistrationNumberItemReader())
                .processor(setRegistrationNumberProcessor())
                .writer(setRegistrationNumberItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Application> setRegistrationNumberItemReader() {
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
    public ItemProcessor<Application, AdmissionStatus> setRegistrationNumberProcessor() {
        return application -> {
            AdmissionStatus admissionStatus = application.getAdmissionStatus();
            Screening screening = application.getAdmissionInfo().getScreening();
            registrationNumberSequence.add(screening);
            Long registrationNumber = getRegistrationNumber(screening);
            AdmissionStatus newAdmissionStatus = AdmissionStatus.builder()
                    .id(admissionStatus.getId())
                    .isFinalSubmitted(admissionStatus.isFinalSubmitted())
                    .isPrintsArrived(admissionStatus.isPrintsArrived())
                    .firstEvaluation(admissionStatus.getFirstEvaluation())
                    .secondEvaluation(admissionStatus.getSecondEvaluation())
                    .registrationNumber(registrationNumber)
                    .secondScore(admissionStatus.getSecondScore())
                    .finalMajor(admissionStatus.getFinalMajor())
                    .build();
            return newAdmissionStatus;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<AdmissionStatus> setRegistrationNumberItemWriter() {
        return new JpaItemWriterBuilder<AdmissionStatus>()
                .usePersist(false)
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }

    private Long getRegistrationNumber(Screening screening) {
        Long prefix = switch (screening) {
            case GENERAL: yield 1000L;
            case SOCIAL: yield 2000L;
            case SPECIAL: yield 3000L;
            default:
                throw new IllegalArgumentException("Invalid screening: " + screening);
        };
        return registrationNumberSequence.get(screening) + prefix;
    }
}