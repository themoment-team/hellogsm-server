package team.themoment.hellogsm.batch.jobs.setRegistrationNumber;

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

@DisplayName("접수번호 할당 배치 Job 테스트")
@SpringBatchTest
@ActiveProfiles(value = {"test"})
@SpringBootTest
class SetRegistrationNumberJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    // 작업 성공 여부만 알려주기 때문에, 실행 결과가 정상적인지 직접 확인해야 함(단위테스트를 추가하면 해당 내용 지우기)
    @Test
    @DisplayName("Job 성공")
    public void successJob(@Qualifier(value = SetRegistrationNumberJobConfig.JOB_NAME) @Autowired Job job) throws Exception {
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
