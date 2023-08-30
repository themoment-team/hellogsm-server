package team.themoment.hellogsm.entity.domain.application.entity.admission;


import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import lombok.*;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

import java.time.LocalDate;
import java.util.Optional;

/**
 * 입학 원서의 인적사항을 저장하는 Entity입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */

@Entity
@Table(name = "admission_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
public class AdmissionInfo {

    @Id
    @Column(name = "admission_info_id")
    private Long id;

    @Column(name = "applicant_image_uri", nullable = false)
    private String applicantImageUri;

    @Column(name = "applicant_name", nullable = false)
    private String applicantName;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicant_gender", nullable = false)
    private Gender applicantGender;

    @Column(name = "applicant_birth", nullable = false)
    private LocalDate applicantBirth;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "graduation", nullable = false)
    private GraduationStatus graduation;

    @Column(name = "telephone", nullable = true)
    private String telephone;

    @Column(name = "applicant_phone_number", nullable = false)
    private String applicantPhoneNumber;

    @Column(name = "guardian_name", nullable = false)
    private String guardianName;

    @Column(name = "relation_with_applicant", nullable = false)
    private String relationWithApplicant;

    @Column(name = "guardian_phone_number", nullable = false)
    private String guardianPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "screening", nullable = false)
    private Screening screening;

    @Column(name = "school_name", nullable = true)
    private String schoolName;

    @Column(name = "school_location", nullable = true)
    private String schoolLocation;

    @Column(name = "teacher_name", nullable = true)
    private String teacherName;

    @Column(name = "teacher_phone_number", nullable = true)
    private String teacherPhoneNumber;

    @Embedded
    private DesiredMajor desiredMajor;

    public Optional<String> getTelephone() {
        return Optional.ofNullable(telephone);
    }

    public Optional<String> getSchoolName() {
        return Optional.ofNullable(schoolName);
    }

    public Optional<String> getSchoolLocation() {
        return Optional.ofNullable(schoolLocation);
    }

    public Optional<String> getTeacherName() {
        return Optional.ofNullable(teacherName);
    }

    public Optional<String> getTeacherPhoneNumber() {
        return Optional.ofNullable(teacherPhoneNumber);
    }

    @AssertFalse(message = "모든 Major 필드는 정의되어야 합니다.")
    private boolean isDesiredMajorNull() {
        return desiredMajor == null;
    }
}
