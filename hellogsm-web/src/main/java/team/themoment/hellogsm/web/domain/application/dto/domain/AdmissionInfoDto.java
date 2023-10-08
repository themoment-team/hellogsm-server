package team.themoment.hellogsm.web.domain.application.dto.domain;

import team.themoment.hellogsm.entity.domain.application.entity.admission.DesiredMajor;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

public record AdmissionInfoDto(
        String applicantName,
        String applicantGender,
        String applicantBirth,
        String address,
        String detailAddress,
        String graduation,
        String telephone,
        String applicantPhoneNumber,
        String guardianName,
        String relationWithApplicant,
        String guardianPhoneNumber,
        String teacherName,
        String teacherPhoneNumber,
        String schoolName,
        String schoolLocation,
        String applicantImageUri,
        DesiredMajor desiredMajor,
        Screening screening
) {
}
