package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.math.BigDecimal;

public record ApplicationsDto(
        Long applicationId,
        String applicantName,
        GraduationStatus graduation,
        String applicantPhoneNumber,
        String guardianPhoneNumber,
        String teacherName,
        String teacherPhoneNumber,
        Boolean isFinalSubmitted,
        Boolean isPrintsArrived,
        EvaluationStatus firstEvaluation,
        EvaluationStatus secondEvaluation,
        Screening screeningFirstEvaluationAt,
        Screening screeningSecondEvaluationAt,
        Long registrationNumber,
        BigDecimal secondScore
) {
}
