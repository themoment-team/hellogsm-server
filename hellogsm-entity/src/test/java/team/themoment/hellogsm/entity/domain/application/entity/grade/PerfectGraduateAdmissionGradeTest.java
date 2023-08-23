package team.themoment.hellogsm.entity.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

public class PerfectGraduateAdmissionGradeTest {
    PerfectGraduateAdmissionGradeMock perfectGraduateAdmissionGradeMock = new PerfectGraduateAdmissionGradeMock();


    @Test
    @DisplayName("일반 만점 테스트")
    public void perfectTest() throws JsonProcessingException {
        // given
        String value = perfectGraduateAdmissionGradeMock.perfectTest();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("60.000"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("240.000")); //TODO 저장돠는 데이터는 모두소수점 4째 자리에서 반올림해야 함
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("6.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("36.000"));
        assertThat(testScore.totalScore).isEqualTo(new BigDecimal("276.000"));
        assertThat(testScore.percentileRank).isEqualTo(new BigDecimal("8.000"));
    }

    // TODO 나머지도 동일하게 소수점 자리까지 확인하게 수정
    @Test
    @DisplayName("1학년 자유 학년제 만점 테스트")
    public void grad1FreeSchoolYearPerfectScoreTest() throws JsonProcessingException {
        // given
        String value = perfectGraduateAdmissionGradeMock.grad1FreeSchoolYearPerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(testScore.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(testScore.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
        assertThat(testScore.totalScore).isEqualTo(toBigDecimal(276, 3));
        assertThat(testScore.percentileRank).isEqualTo(toBigDecimal(8, 3));
    }

    @Test
    @DisplayName("1학년 1학기 자유 학기제 만점 테스트")
    public void grade1Semester1PerfectScoreTest() throws JsonProcessingException {
        // given
        String value = perfectGraduateAdmissionGradeMock.grade1Semester1PerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        assertThat(testScore.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(testScore.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(testScore.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
        assertThat(testScore.totalScore).isEqualTo(toBigDecimal(276, 3));
        assertThat(testScore.percentileRank).isEqualTo(toBigDecimal(8, 3));
    }

    @Test
    @DisplayName("1학년 2학기 자유 학기제 만점 테스트")
    public void grade1Semester2PerfectScoreTest() throws JsonProcessingException {
        // given
        String value = perfectGraduateAdmissionGradeMock.grade1Semester2PerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        assertThat(testScore.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(testScore.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(testScore.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
        assertThat(testScore.totalScore).isEqualTo(toBigDecimal(276, 3));
        assertThat(testScore.percentileRank).isEqualTo(toBigDecimal(8, 3));
    }

    @Test
    @DisplayName("2학년 1학기 자유 학기제 만점 테스트")
    public void grade2Semester1PerfectScoreTest() throws JsonProcessingException {
        // given
        String value = perfectGraduateAdmissionGradeMock.grade2Semester1PerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        assertThat(testScore.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(testScore.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(testScore.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
        assertThat(testScore.totalScore).isEqualTo(toBigDecimal(276, 3));
        assertThat(testScore.percentileRank).isEqualTo(toBigDecimal(8, 3));
    }

    private BigDecimal toBigDecimal(int value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
