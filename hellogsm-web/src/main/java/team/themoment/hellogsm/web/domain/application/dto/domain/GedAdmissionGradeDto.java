package team.themoment.hellogsm.web.domain.application.dto.domain;

import java.math.BigDecimal;

public record GedAdmissionGradeDto (
        BigDecimal totalScore,
        BigDecimal percentileRank,
        BigDecimal gedTotalScore,
        BigDecimal gedMaxScore
) {
}
