package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;

public record TicketResDto(
        Long applicationId,
        String applicantName,
        Gender applicantGender,
        String applicantBirth,
        String applicantImageUri,
        String address,
        GraduationStatus graduation,
        Long registrationNumber
) {
}
