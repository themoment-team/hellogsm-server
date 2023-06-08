package team.themoment.hellogsm.web.domain.application.dto.domain;

import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;

import java.math.BigDecimal;

public record AdmissionStatusDto(
        Boolean isFinalSubmitted,
        Boolean isPrintsArrived,
        EvaluationStatus firstEvaluation,
        EvaluationStatus secondEvaluation,
        Long registrationNumber,
        BigDecimal secondScore,
        Major finalMajor
) {
}
