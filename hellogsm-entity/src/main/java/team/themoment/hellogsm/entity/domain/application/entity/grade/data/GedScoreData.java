package team.themoment.hellogsm.entity.domain.application.entity.grade.data;

import java.math.BigDecimal;

public record GedScoreData (
    BigDecimal curriculumScoreSubtotal,
    BigDecimal nonCurriculumScoreSubtotal,
    BigDecimal rankPercentage,
    BigDecimal scoreTotal
) {}
