package kr.hellogsm.back_v2.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

public class GraduateAdmissionGradeTest {
    GraduateAdmissionGradeMock graduateAdmissionGradeMock = new GraduateAdmissionGradeMock();


    @Test
    @DisplayName("일반 만점 테스트")
    public void perfectTest() throws JsonProcessingException {
        // given
        String value = graduateAdmissionGradeMock.perfectTest();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade a = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(a.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(a.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(a.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(a.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(a.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
    }

    @Test
    @DisplayName("1학년 자유 학년제 만점 테스트")
    public void grad1FreeSchoolYearPerfectScoreTest() throws JsonProcessingException {
        // given
        String value = graduateAdmissionGradeMock.grad1FreeSchoolYearPerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade a = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(a.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(a.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(a.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(a.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(a.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
    }

    @Test
    @DisplayName("2학년 자유 학년제 만점 테스트")
    public void grad2FreeSchoolYearPerfectScoreTest() throws JsonProcessingException {
        // given
        String value = graduateAdmissionGradeMock.grad2FreeSchoolYearPerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade a = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(a.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(a.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(a.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(a.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(a.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
    }

    @Test
    @DisplayName("1학년 1학기 자유 학기제 만점 테스트")
    public void grade1Semester1PerfectScoreTest() throws JsonProcessingException {
        // given
        String value = graduateAdmissionGradeMock.grade1Semester1PerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade a = new GraduateAdmissionGrade(middleSchoolGrade);

        assertThat(a.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(a.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(a.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(a.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(a.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
    }

    @Test
    @DisplayName("1학년 2학기 자유 학기제 만점 테스트")
    public void grade1Semester2PerfectScoreTest() throws JsonProcessingException {
        // given
        String value = graduateAdmissionGradeMock.grade1Semester2PerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade a = new GraduateAdmissionGrade(middleSchoolGrade);

        assertThat(a.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(a.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(a.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(a.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(a.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
    }

    @Test
    @DisplayName("2학년 1학기 자유 학기제 만점 테스트")
    public void grade2Semester1PerfectScoreTest() throws JsonProcessingException {
        // given
        String value = graduateAdmissionGradeMock.grade2Semester1PerfectScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade a = new GraduateAdmissionGrade(middleSchoolGrade);

        assertThat(a.getArtisticScore()).isEqualTo(toBigDecimal(60, 3));
        assertThat(a.getCurricularSubtotalScore()).isEqualTo(toBigDecimal(180 + 60, 4));
        assertThat(a.getAttendanceScore()).isEqualTo(toBigDecimal(30, 0));
        assertThat(a.getVolunteerScore()).isEqualTo(toBigDecimal(6, 0));
        assertThat(a.getExtracurricularSubtotalScore()).isEqualTo(toBigDecimal(36, 4));
    }

    private BigDecimal toBigDecimal(int value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
