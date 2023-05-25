package team.themoment.hellogsm.entity.domain.application.entity.grade.data;

import java.math.BigDecimal;

public record GedScoreData ( //TODO 유효성 검증 필요; 어느 레이어에서 할지는 미정임
    BigDecimal curriculumScoreSubtotal,
    BigDecimal nonCurriculumScoreSubtotal,
    BigDecimal rankPercentage,
    BigDecimal scoreTotal
) {}
