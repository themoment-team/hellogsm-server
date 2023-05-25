package team.themoment.hellogsm.web.domain.application.service.data;

import java.math.BigDecimal;

public record GedScoreData (
    BigDecimal curriculumScoreSubtotal,
    BigDecimal nonCurriculumScoreSubtotal,
    BigDecimal rankPercentage,
    BigDecimal scoreTotal
) {}
