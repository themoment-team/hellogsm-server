package team.themoment.hellogsm.entity.domain.application.entity.grade.data;

import java.math.BigDecimal;

/**
 * 검정고시 학샐의 중학교 성적을 저장하는 record 입니다. <br/>
 * {@link team.themoment.hellogsm.entity.domain.application.entity.grade.GedAdmissionGrade} 생성에 사용됩니다.
 */
public record GedScoreData (
    BigDecimal curriculumScoreSubtotal,
    BigDecimal nonCurriculumScoreSubtotal,
    BigDecimal rankPercentage,
    BigDecimal scoreTotal
) {}
