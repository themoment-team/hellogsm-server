package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("최종학과 할당 배치 테스트")
@SpringBatchTest
@ActiveProfiles(value = {"test"})
@SpringBootTest
class SetFinalMajorJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("Job 성공")
    public void successJob(@Qualifier(value = SetFinalMajorJobConfig.JOB_NAME) @Autowired Job job) throws Exception {
        // given
        jobLauncherTestUtils.setJob(job);
        jobLauncherTestUtils.setJobRepository(jobLauncherTestUtils.getJobRepository());
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("VERSION", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) // Unique Job 실행을 위해서 사용
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}
