package team.themoment.hellogsm.entity.domain.application.enums;

import team.themoment.hellogsm.entity.domain.application.entity.grade.ScreeningCategory;

public enum Screening {
    GENERAL(ScreeningCategory.GENERAL), // 일반전형
    SOCIAL(ScreeningCategory.SOCIAL), // 특별전형(사회통합전형)
    SPECIAL_VETERANS(ScreeningCategory.SPECIAL), // 국가보훈대상자
    SPECIAL_ADMISSION(ScreeningCategory.SPECIAL); // 특례입학대상자

    private final ScreeningCategory category;

    Screening(ScreeningCategory category) {
        this.category = category;
    }

    public ScreeningCategory getCategory() {
        return category;
    }
}
