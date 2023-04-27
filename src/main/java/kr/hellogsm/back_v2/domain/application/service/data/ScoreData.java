package kr.hellogsm.back_v2.domain.application.service.data;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 학기별 성적을 저장하는 record 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public record ScoreData(
        // 국어, 도덕, 사회, 역사, 수학, 과학, 기가, 영어, ...
        List<BigDecimal> score1_1,

        List<BigDecimal> score1_2,

        @NotBlank
        List<BigDecimal> score2_1,

        @NotBlank
        List<BigDecimal> score2_2,

        @NotBlank
        List<BigDecimal> score3_1,

        @NotBlank
        List<BigDecimal> artSportsScore,

        @NotBlank
        List<BigDecimal> absentScore,

        @NotBlank
        List<BigDecimal> attendanceScore,

        @NotBlank
        List<BigDecimal> volunteerScore,

        @NotBlank
        List<String> subjects,

        @NotBlank
        List<String> newSubjects,

        @NotBlank
        List<String> nonSubjects,

        @NotBlank
        String system,

        String freeSemester
) {
        public String freeSemester() {
                return freeSemester == null ? "0" : freeSemester;
        }

        public String system() {
                return system == null ? "0" : system;
        }
}
