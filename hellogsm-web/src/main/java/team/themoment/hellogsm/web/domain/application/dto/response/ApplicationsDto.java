package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;

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
        Major majorSubmittedAt,
        Major majorFirstEvaluationAt,
        Major majorSecondEvaluationAt,
        Long registrationNumber,
        String secondScore
) {
}
