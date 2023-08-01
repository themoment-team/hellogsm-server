package team.themoment.hellogsm.entity.domain.application.enums;

import team.themoment.hellogsm.entity.domain.application.entity.grade.ScreeningCategory;

public enum Screening {
    GENERAL(ScreeningCategory.GENERAL),
    SOCIAL(ScreeningCategory.SOCIAL),
    SPECIAL_VETERANS(ScreeningCategory.SPECIAL), // 국가보훈대상자
    SPECIAL_ADMISSION(ScreeningCategory.SPECIAL); // 특례입학대상자

    private final ScreeningCategory category;

    Screening(ScreeningCategory category) {
        this.category = category;
    }

    // Getter 메소드 (선택 사항)
    public ScreeningCategory getCategory() {
        return category;
    }
}
