package kr.hellogsm.back_v2.domain.application.entity.grade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 검정고시 성적 테스트입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public class GedAdmissionGradeTest {
    private final GedAdmissionGradeMock mock = new GedAdmissionGradeMock();
    @Test
    @DisplayName("검정고시 만점 테스트")
    public void maxScore() throws JsonProcessingException {
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, mock.maxScore());
        GedAdmissionGrade gedAdmissionGrade = new GedAdmissionGrade(middleSchoolGrade);

        Assertions.assertThat(gedAdmissionGrade.percentileRank).isEqualTo(toBigDecimal(0, 3));
        Assertions.assertThat(gedAdmissionGrade.totalScore).isEqualTo(toBigDecimal(261, 3));
    }

    private BigDecimal toBigDecimal(int value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
