package kr.hellogsm.back_v2.domain.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        String applicantName,

        @NotBlank
        Gender applicantGender,

        @NotBlank
        LocalDate applicantBirth,

        @NotBlank
        String address,

        @NotBlank
        String detailAddress,

        @NotBlank
        GraduationStatus graduation,

        @Pattern(regexp = "^ +$")
        String telephone,

        @NotBlank
        String applicantPhoneNumber,

        @NotBlank
        String guardianName,

        @NotBlank
        String relationWithApplicant,

        @NotBlank
        String guardianPhoneNumber,

        @Pattern(regexp = "^ +$")
        String teacherName,

        @Pattern(regexp = "^ +$")
        String teacherPhoneNumber,

        @Valid
        DesiredMajorReqDto DesiredMajorResponseDto,

        @NotBlank
        String MiddleSchoolGrade
) {
}
