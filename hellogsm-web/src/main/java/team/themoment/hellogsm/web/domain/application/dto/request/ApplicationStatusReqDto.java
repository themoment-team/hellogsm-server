package team.themoment.hellogsm.web.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsm.entity.domain.application.enums.Major;

import java.math.BigDecimal;

public record ApplicationStatusReqDto(
        @NotNull
        Boolean isFinalSubmitted,

        @NotNull
        Boolean isPrintsArrived,

        @Pattern(regexp = "^(NOT_YET|PASS|FALL)$")
        @NotBlank
        String firstEvaluation,

        @Pattern(regexp = "^(NOT_YET|PASS|FALL)$")
        @NotBlank
        String secondEvaluation,

        @Pattern(regexp = "^(AI|IOT|SW|)$") // null 포함
        String majorSubmittedAt,

        @Pattern(regexp = "^(AI|IOT|SW|)$") // null 포함
        String majorFirstEvaluationAt,

        @Pattern(regexp = "^(AI|IOT|SW|)$") // null 포함
        String majorSecondEvaluationAt,

        Long registrationNumber,

        BigDecimal secondScore,

        @Pattern(regexp = "^(AI|IOT|SW|)$") // null 포함
        String finalMajor
) {
}
