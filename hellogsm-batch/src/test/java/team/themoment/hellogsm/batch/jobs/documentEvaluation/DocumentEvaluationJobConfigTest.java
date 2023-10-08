package team.themoment.hellogsm.batch.jobs.documentEvaluation;

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

@DisplayName("서류 평가(1차 평가) 배치 테스트")
@SpringBatchTest
@ActiveProfiles(value = {"test-bf"})
@SpringBootTest
class DocumentEvaluationJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    /*
    배치 결과 확인할 때 사용하는 SQL

    SELECT
        s.APPLICATION_STATUS_ID AS ID,
        i.SCREENING AS `최종제출 시 전형`,
        s.SCREENING_FIRST_EVALUATION_AT AS `서류(1차) 평가 이후 전형`,
        s.FIRST_EVALUATION AS `서류(1차) 평가 결과`,
        g.TOTAL_SCORE AS `총합`,
        gg.CURRICULAR_SUBTOTAL_SCORE AS `교과성적`,
        gg.ARTISTIC_SCORE AS `예체능`,
        gg.GRADE_3_SEMESTER_1_SCORE AS `3-1`,
        gg.GRADE_2_SEMESTER_2_SCORE AS `2-2`,
        gg.GRADE_2_SEMESTER_1_SCORE AS `2-1`,
        gg.GRADE_1_SEMESTER_2_SCORE AS `1-2`,
        gg.GRADE_1_SEMESTER_1_SCORE AS `1-1`,
        gg.EXTRACURRICULAR_SUBTOTAL_SCORE AS `비교과`,
        i.APPLICANT_BIRTH AS `지원자 생년월일`
    FROM ADMISSION_STATUS s
    INNER JOIN ADMISSION_GRADE g ON s.APPLICATION_STATUS_ID = g.ADMISSION_GRADE_ID
    INNER JOIN ADMISSION_INFO i ON s.APPLICATION_STATUS_ID = i.ADMISSION_INFO_ID
    LEFT OUTER JOIN GRADUATE_ADMISSION_GRADE gg ON s.APPLICATION_STATUS_ID = gg.ADMISSION_GRADE_ID
    WHERE   s.IS_FINAL_SUBMITTED = true
    AND     s.IS_PRINTS_ARRIVED = true
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
    public void successJob(@Qualifier(value = DocumentEvaluationJobConfig.JOB_NAME) @Autowired Job job) throws Exception {
        // given
        jobLauncherTestUtils.setJob(job);
        jobLauncherTestUtils.setJobRepository(jobLauncherTestUtils.getJobRepository());
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("VERSION", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .addLong("GENERAL", 48L)
                .addLong("SOCIAL", 24L)
                .addLong("SPECIAL_VETERANS", 2L)
                .addLong("SPECIAL_ADMISSION", 1L)
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}
