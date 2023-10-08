package team.themoment.hellogsm.web.domain.application.dto.response;

import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

public record TicketResDto(
        String applicantName,
        String applicantBirth,
        String applicantImageUri,
        String schoolName,
        Screening screening,
        Long registrationNumber
) {
}
