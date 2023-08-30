package team.themoment.hellogsm.batch.jobs.setFinalMajor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("최종학과 할당 배치 테스트")
@SpringBatchTest
@ActiveProfiles(value = {"test-two"})
@SpringBootTest
class SetFinalMajorJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

/*
    배치 결과 확인할 때 사용하는 SQL

    SELECT s.APPLICATION_STATUS_ID as ID,
            s.SCREENING_SECOND_EVALUATION_AT as `최종 전형`,
            s.FINAL_MAJOR as `최종 학과`,
            i.FIRST_DESIRED_MAJOR as `1지망 학과`,
            i.SECOND_DESIRED_MAJOR as `2지망 학과`,
            i.THIRD_DESIRED_MAJOR as `2지망 학과`,
            g.TOTAL_SCORE as `총합`,
            gg.CURRICULAR_SUBTOTAL_SCORE as `교과성적`,
            gg.ARTISTIC_SCORE as `예체능`,
            gg.GRADE_3_SEMESTER_1_SCORE as `3-1`,
            gg.GRADE_2_SEMESTER_2_SCORE as `2-2`,
            gg.GRADE_2_SEMESTER_1_SCORE as `2-1`,
            gg.GRADE_1_SEMESTER_2_SCORE as `1-2`,
            gg.GRADE_1_SEMESTER_1_SCORE as `1-1`,
            gg.EXTRACURRICULAR_SUBTOTAL_SCORE  as `비교과`,
            i.APPLICANT_BIRTH as `지원자 생년월일`
    FROM ADMISSION_STATUS s
    INNER JOIN ADMISSION_GRADE g ON s.APPLICATION_STATUS_ID = g.ADMISSION_GRADE_ID
    INNER JOIN ADMISSION_INFO i ON s.APPLICATION_STATUS_ID = i.ADMISSION_INFO_ID
    LEFT OUTER JOIN GRADUATE_ADMISSION_GRADE gg ON s.APPLICATION_STATUS_ID = gg.ADMISSION_GRADE_ID
    WHERE s.SECOND_EVALUATION  = 'PASS'
    ORDER BY g.TOTAL_SCORE DESC,
            (gg.CURRICULAR_SUBTOTAL_SCORE + gg.ARTISTIC_SCORE) DESC,
            gg.GRADE_3_SEMESTER_1_SCORE DESC,
            (gg.GRADE_2_SEMESTER_1_SCORE + gg.GRADE_2_SEMESTER_2_SCORE) DESC,
            gg.GRADE_2_SEMESTER_2_SCORE DESC,
            gg.GRADE_2_SEMESTER_1_SCORE DESC,
            gg.EXTRACURRICULAR_SUBTOTAL_SCORE DESC,
            i.APPLICANT_BIRTH ASC;
    */

    @Test
    @DisplayName("Job 성공")
    public void successJob(@Qualifier(value = SetFinalMajorJobConfig.JOB_NAME) @Autowired Job job) throws Exception {
        // given
        jobLauncherTestUtils.setJob(job);
        jobLauncherTestUtils.setJobRepository(jobLauncherTestUtils.getJobRepository());
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("VERSION", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) // Unique Job 실행을 위해서 사용
                .addLong("G_IOT", 12L)
                .addLong("G_SW", 24L)
                .addLong("G_AI", 12L)
                .addLong("S_IOT", 2L)
                .addLong("S_SW", 2L)
                .addLong("S_AI", 2L)
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}
