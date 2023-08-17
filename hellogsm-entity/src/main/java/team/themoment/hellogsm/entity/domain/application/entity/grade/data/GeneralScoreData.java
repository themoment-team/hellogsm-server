package team.themoment.hellogsm.entity.domain.application.entity.grade.data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 학기별 성적을 저장하는 record 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public record GeneralScoreData(
        List<BigDecimal> score1_1,
        List<BigDecimal> score1_2,
        List<BigDecimal> score2_1,
        List<BigDecimal> score2_2,
        List<BigDecimal> score3_1,
        List<BigDecimal> artSportsScore,
        List<BigDecimal> absentScore,
        List<BigDecimal> attendanceScore,
        List<BigDecimal> volunteerScore,
        List<String> subjects,
        List<String> newSubjects,
        List<String> nonSubjects,
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
