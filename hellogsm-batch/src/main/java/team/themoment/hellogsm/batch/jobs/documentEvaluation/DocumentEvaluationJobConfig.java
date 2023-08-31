package team.themoment.hellogsm.batch.jobs.documentEvaluation;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DocumentEvaluationJobConfig {
    public final static int CHUNK_SIZE = 10;
    public final static String JOB_NAME = "documentEvaluationJob";
    public final static String BEAN_PREFIX = JOB_NAME + "_";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DocumentEvaluationParameter parameter;

    @Bean(BEAN_PREFIX + "parameter")
    @JobScope
    public DocumentEvaluationParameter parameter(
            @Value("#{jobParameters[VERSION]}") Long version,
            @Value("#{jobParameters[GENERAL]}") Integer general,
            @Value("#{jobParameters[SOCIAL]}") Integer social,
            @Value("#{jobParameters[SPECIAL]}") Integer special
    ) {
        return new DocumentEvaluationParameter(version, general, social, special);
    }

}
