package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
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
import team.themoment.hellogsm.entity.common.util.OptionalUtils;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;

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

//    @Bean(BEAN_PREFIX + "major_capacity")
//    @JobScope
//    public MajorCapacity majorCapacity() {
//    }

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
                .start(setFinalMajorStep())
                .on("*")
                .end()
                .end()
                .build();
    }

//    @Bean
//    @JobScope
//    public Step clearFinalMajorStep() {
//
//    }

    @Bean
    @JobScope
    public Step setFinalMajorStep() {
        return new StepBuilder(BEAN_PREFIX+"setFinalMajorStep", this.jobRepository)
                .<Application, AdmissionStatus>chunk(CHUNK_SIZE, this.platformTransactionManager)
                .reader(finalPassedApplicationOrderByScoreIR())
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
                                "WHERE a.admissionStatus.screeningSecondEvaluationAt IS NOT NULL " +
                                "AND ( a.admissionStatus.screeningSecondEvaluationAt = GENERAL " +
                                "OR a.admissionStatus.screeningSecondEvaluationAt = SOCIAL )" +
                                "ORDER BY a.admissionGrade.totalScore DESC, " +
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
    public JpaItemWriter<AdmissionStatus> finalMajorIW() {
        return new JpaItemWriterBuilder<AdmissionStatus>()
                .usePersist(false) // update
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }


}
