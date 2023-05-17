package kr.hellogsm.back_v2.domain.application.service.data;

import java.math.BigDecimal;

public record GedScoreData (
    BigDecimal curriculumScoreSubtotal,
    BigDecimal nonCurriculumScoreSubtotal,
    BigDecimal rankPercentage,
    BigDecimal scoreTotal
) {}
