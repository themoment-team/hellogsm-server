package team.themoment.hellogsm.batch.common;

import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

public record EvaluationResult(
        EvaluationStatus evaluationStatus,
        Screening screening
) {
}
