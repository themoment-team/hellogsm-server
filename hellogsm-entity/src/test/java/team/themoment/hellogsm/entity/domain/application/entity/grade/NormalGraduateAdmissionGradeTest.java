package team.themoment.hellogsm.entity.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

public class NormalGraduateAdmissionGradeTest {
    NormalGraduateAdmissionGradeMock normalGraduateAdmissionGradeMock = new NormalGraduateAdmissionGradeMock();

    @Test
    @DisplayName("추가과목 X 1학년 자유학년제 테스트")
    public void 추가과목_X_1학년() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_X_1학년();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("45.900"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("43.200"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("48.600"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("52.500"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("190.200"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("21.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("16.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("37.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("227.200"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("24.267"));
    }

    @Test
    @DisplayName("추가과목_O 1학년 자유학년제 테스트")
    public void 추가과목_O_1학년() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_O_1학년();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("43.200"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("42.000"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("46.400"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("52.020"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("183.620"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("21.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("51.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("234.620"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("21.793"));
    }

    @Test
    @DisplayName("추가과목_X 1학년 1학기 자유학기제 테스트")
    public void 추가과목_X_1학년_1학기() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_X_1학년_1학기();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("12.600"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("24.300"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("39.150"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("52.200"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("53.340"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("181.590"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("3.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("14.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("17.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("198.590"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("33.803"));

    }

    @Test
    @DisplayName("추가과목_O 1학년 1학기 자유학기제 테스트")
    public void 추가과목_O_1학년_1학기() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_O_1학년_1학기();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("12.600"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("23.040"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("37.309"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("53.280"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("53.340"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("179.569"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("3.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("14.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("17.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("196.569"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("34.477"));
    }

    @Test
    @DisplayName("추가과목_X 1학년 2학기 자유학기제 테스트")
    public void 추가과목_X_1학년_2학기() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_X_1학년_2학기();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("13.950"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("30.857"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("44.550"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("55.800"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("56.580"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("201.737"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("60.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("261.737"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("12.754"));
    }

    @Test
    @DisplayName("추가과목_O 1학년 2학기 자유학기제 테스트")
    public void 추가과목_O_1학년_2학기() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_O_1학년_2학기();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("13.800"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("28.080"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("42.300"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("51.600"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("56.580"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("192.360"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("60.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("252.360"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("15.880"));
    }


    @Test
    @DisplayName("추가과목_X 2학년 1학기 자유학기제 테스트")
    public void 추가과목_X_2학년_1학기() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_X_2학년_1학기();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("12.150"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("34.200"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("40.500"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("45.257"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("56.580"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("188.687"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("60.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("248.687"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("17.104"));
    }


    @Test
    @DisplayName("추가과목_O 2학년 1학기 자유학기제 테스트")
    public void 추가과목_O_2년_1학기() throws JsonProcessingException {
        // given
        String value = normalGraduateAdmissionGradeMock.추가과목_O_2년_1학기();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, value);

        // when
        GraduateAdmissionGrade testScore = new GraduateAdmissionGrade(middleSchoolGrade);

        // then
        assertThat(testScore.getGrade1Semester1Score()).isEqualTo(new BigDecimal("13.800"));
        assertThat(testScore.getGrade1Semester2Score()).isEqualTo(new BigDecimal("36.000"));
        assertThat(testScore.getGrade2Semester1Score()).isEqualTo(new BigDecimal("0.000"));
        assertThat(testScore.getGrade2Semester2Score()).isEqualTo(new BigDecimal("42.300"));
        assertThat(testScore.getGrade3Semester1Score()).isEqualTo(new BigDecimal("51.600"));
        assertThat(testScore.getArtisticScore()).isEqualTo(new BigDecimal("56.580"));
        assertThat(testScore.getCurricularSubtotalScore()).isEqualTo(new BigDecimal("200.280"));
        assertThat(testScore.getAttendanceScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getVolunteerScore()).isEqualTo(new BigDecimal("30.000"));
        assertThat(testScore.getExtracurricularSubtotalScore()).isEqualTo(new BigDecimal("60.000"));
        assertThat(testScore.getTotalScore()).isEqualTo(new BigDecimal("260.280"));
        assertThat(testScore.getPercentileRank()).isEqualTo(new BigDecimal("13.240"));

    }


    private BigDecimal toBigDecimal(int value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
