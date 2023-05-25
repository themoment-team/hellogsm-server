package team.themoment.hellogsm.web.domain.application.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.admission.DesiredMajor;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.entity.grade.MiddleSchoolGrade;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.web.domain.application.annotation.NotSpace;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 원서 생성을 담당하는 dto 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public record ApplicationReqDto(
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

        @NotBlank
        @Pattern(regexp = "^(CANDIDATE|GRADUATE|GED)$")
        String graduation,

        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String telephone,

        @NotBlank
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String applicantPhoneNumber,

        @NotBlank
        String guardianName,

        @NotBlank
        String relationWithApplicant,

        @NotBlank
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String guardianPhoneNumber,

        @Pattern(regexp = "^(?!\\s*$).+")
        String teacherName,

        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String teacherPhoneNumber,

        @Pattern(regexp = "^(AI|SW|IOT)$")
        @NotBlank
        String firstDesiredMajor,

        @Pattern(regexp = "^(AI|SW|IOT)$")
        @NotBlank
        String secondDesiredMajor,

        @Pattern(regexp = "^(AI|SW|IOT)$")
        @NotBlank
        String thirdDesiredMajor,

        @NotBlank
        String MiddleSchoolGrade,

        @NotSpace
        String schoolName,

        @NotSpace
        String schoolLocation
) {
    public Application toEntity(Long id, Long userId) {
        GraduationStatus graduationStatus = null;
        try {
            graduationStatus = GraduationStatus.valueOf(this.graduation);
        } catch (IllegalArgumentException e) {
            throw new ExpectedException("graduation 값이 올바르지 않습니다", HttpStatus.BAD_REQUEST);
        }

        DesiredMajor desiredMajor = DesiredMajor.builder()
                .firstDesiredMajor(Major.valueOf(this.firstDesiredMajor))
                .secondDesiredMajor(Major.valueOf(this.secondDesiredMajor))
                .thirdDesiredMajor(Major.valueOf(this.thirdDesiredMajor))
                .build();

        AdmissionInfo admissionInfo = AdmissionInfo.builder()
                .id(id)
                .applicantImageUri(this.applicantImageUri)
                .applicantName(this.applicantName)
                .applicantGender(this.applicantGender)
                .applicantBrith(this.applicantBirth)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .graduation(graduationStatus)
                .telephone(this.telephone)
                .applicantPhoneNumber(this.applicantPhoneNumber)
                .guardianName(this.guardianName)
                .relationWithApplicant(this.relationWithApplicant)
                .guardianPhoneNumber(this.guardianPhoneNumber)
                .teacherName(this.teacherName)
                .teacherPhoneNumber(this.teacherPhoneNumber)
                .desiredMajor(desiredMajor)
                .build();

        AdmissionStatus admissionStatus = AdmissionStatus.init(id);
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(id, this.MiddleSchoolGrade);

        return new Application(
                id,
                admissionInfo,
                admissionStatus,
                middleSchoolGrade,
                userId
        );
    }

    @AssertTrue(message = "중복된 전공이 있습니다")
    private boolean isDuplicateMajor() {
        Set<String> majorSet = new HashSet<>(List.of(firstDesiredMajor, secondDesiredMajor, thirdDesiredMajor));

        return majorSet.size() == 3;
    }
}
