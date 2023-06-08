package team.themoment.hellogsm.web.domain.application.dto.domain;

import java.math.BigDecimal;

public record GeneralAdmissionGradeDto(
        BigDecimal totalScore,
        BigDecimal percentileRank,
        BigDecimal grade1Semester1Score,
        BigDecimal grade1Semester2Score,
        BigDecimal grade2Semester1Score,
        BigDecimal grade2Semester2Score,
        BigDecimal grade3Semester1Score,
        BigDecimal artisticScore,
        BigDecimal curricularSubtotalScore,
        BigDecimal attendanceScore,
        BigDecimal volunteerScore,
        BigDecimal extracurricularSubtotalScore
) {
}
