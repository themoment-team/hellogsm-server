package team.themoment.hellogsm.entity.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GedAdmissionGradeTest {

    GedAdmissionGradeMock gedAdmissionGradeMock = new GedAdmissionGradeMock();

    @Test
    @DisplayName("GED 만점 테스트")
    public void perfectTest() throws JsonProcessingException {
        // given
        String value = gedAdmissionGradeMock.maxScore();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GedAdmissionGrade testScore = new GedAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGedMaxScore()).isEqualTo(new BigDecimal("100"));
        assertThat(testScore.getGedTotalScore()).isEqualTo(new BigDecimal("100"));
        assertThat(testScore.totalScore).isEqualTo(new BigDecimal("261.000"));
        assertThat(testScore.percentileRank).isEqualTo(new BigDecimal("0.000"));
    }

    @Test
    @DisplayName("GED 일반 테스트1")
    public void nomal1() throws JsonProcessingException {
        // given
        String value = gedAdmissionGradeMock.nomal1();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GedAdmissionGrade testScore = new GedAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGedTotalScore()).isEqualTo(new BigDecimal("123"));
        assertThat(testScore.getGedMaxScore()).isEqualTo(new BigDecimal("1234"));
        assertThat(testScore.totalScore).isEqualTo(new BigDecimal("26.016"));
        assertThat(testScore.percentileRank).isEqualTo(new BigDecimal("90.032"));
    }

    @Test
    @DisplayName("GED 일반 테스트2")
    public void nomal2() throws JsonProcessingException {
        // given
        String value = gedAdmissionGradeMock.nomal2();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GedAdmissionGrade testScore = new GedAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGedTotalScore()).isEqualTo(new BigDecimal("1200"));
        assertThat(testScore.getGedMaxScore()).isEqualTo(new BigDecimal("1234"));
        assertThat(testScore.totalScore).isEqualTo(new BigDecimal("253.809"));
        assertThat(testScore.percentileRank).isEqualTo(new BigDecimal("2.755"));
    }
}
