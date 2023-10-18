package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.math.BigDecimal;

public record SearchApplicationResDto(
        Long applicationId,
        Boolean isFinalSubmitted,
        Boolean isPrintsArrived,
        String applicantName,
        Screening screening,
        String schoolName,
        String applicantPhoneNumber,
        String guardianPhoneNumber,
        String teacherPhoneNumber,
        EvaluationStatus firstEvaluation,
        EvaluationStatus secondEvaluation,
        Long registrationNumber,
        BigDecimal secondScore
){

}
