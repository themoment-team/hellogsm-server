package team.themoment.hellogsm.entity.domain.application.enums;

import team.themoment.hellogsm.entity.domain.application.entity.grade.ScreeningCategory;

/**
 * 입학전형의 종류를 나타내는 enum class 입니다.
 */
public enum Screening {

    /**
     * 일반전형
     */
    GENERAL(ScreeningCategory.GENERAL),

    /**
     * 특별전형(사회통합전형)
     */
    SOCIAL(ScreeningCategory.SOCIAL),

    /**
     * 국가보훈대상자
     */
    SPECIAL_VETERANS(ScreeningCategory.SPECIAL),

    /**
     * 특례입학대상자
     */
    SPECIAL_ADMISSION(ScreeningCategory.SPECIAL);

    private final ScreeningCategory category;

    Screening(ScreeningCategory category) {
        this.category = category;
    }

    public ScreeningCategory getCategory() {
        return category;
    }
}
