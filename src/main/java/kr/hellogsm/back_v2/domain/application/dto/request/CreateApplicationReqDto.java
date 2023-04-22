package kr.hellogsm.back_v2.domain.application.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.hellogsm.back_v2.domain.application.entity.Application;
import kr.hellogsm.back_v2.domain.application.entity.admission.AdmissionInfo;
import kr.hellogsm.back_v2.domain.application.entity.admission.DesiredMajor;
import kr.hellogsm.back_v2.domain.application.entity.grade.MiddleSchoolGrade;
import kr.hellogsm.back_v2.domain.application.entity.status.AdmissionStatus;
import kr.hellogsm.back_v2.domain.application.enums.Gender;
import kr.hellogsm.back_v2.domain.application.enums.GraduationStatus;

import java.time.LocalDate;

/**
 * 원서 생성을 담당하는 dto 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public record CreateApplicationReqDto(
        @NotBlank
        String applicantImageUri,

        @NotBlank
        String applicantName,

        @Enumerated(EnumType.STRING)
        Gender applicantGender,

        LocalDate applicantBirth,

        @NotBlank
        String address,

        @NotBlank
        String detailAddress,

        @Enumerated(EnumType.STRING)
        GraduationStatus graduation,

        @Pattern(regexp = "^(?!\\s*$).+")
        String telephone,

        @NotBlank
        String applicantPhoneNumber,

        @NotBlank
        String guardianName,

        @NotBlank
        String relationWithApplicant,

        @NotBlank
        String guardianPhoneNumber,

        @Pattern(regexp = "^(?!\\s*$).+")
        String teacherName,

        @Pattern(regexp = "^(?!\\s*$).+")
        String teacherPhoneNumber,

        DesiredMajor desiredMajorResponseDto,

        @NotBlank
        String MiddleSchoolGrade
) {
    public Application toEntity() {
        AdmissionInfo admissionInfo = AdmissionInfo.builder()
                .applicantImageUri(this.applicantImageUri)
                .applicantName(this.applicantName)
                .applicantGender(this.applicantGender)
                .applicantBrith(this.applicantBirth)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .graduation(this.graduation)
                .telephone(this.telephone)
                .applicantPhoneNumber(this.applicantPhoneNumber)
                .guardianName(this.guardianName)
                .relationWithApplicant(this.relationWithApplicant)
                .guardianPhoneNumber(this.guardianPhoneNumber)
                .teacherName(this.teacherName)
                .teacherPhoneNumber(this.teacherPhoneNumber)
                .desiredMajor(this.desiredMajorResponseDto)
                .build();

        AdmissionStatus admissionStatus = AdmissionStatus.init();
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(null, this.MiddleSchoolGrade);

        return new Application(
                null,
                admissionInfo,
                admissionStatus,
                middleSchoolGrade
        );
    }
}
