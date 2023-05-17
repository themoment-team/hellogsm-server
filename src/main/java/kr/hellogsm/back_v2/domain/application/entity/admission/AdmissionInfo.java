package kr.hellogsm.back_v2.domain.application.entity.admission;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertFalse;
import kr.hellogsm.back_v2.domain.application.enums.Gender;
import kr.hellogsm.back_v2.domain.application.enums.GraduationStatus;
import lombok.*;

import java.time.LocalDate;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_info_id")
    private Long id;

    @Column(name = "applicant_image_uri", nullable = false)
    private String applicantImageUri;

    @Column(name = "applicant_name", nullable = false)
    private String applicantName;


    @Enumerated(EnumType.STRING)
    @Column(name = "applicant_gender", nullable = false)
    private Gender applicantGender;

    
    @Column(name = "applicant_brith", nullable = false)
    private LocalDate applicantBrith;

    
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

    
    @Column(name = "teacher_name", nullable = true)
    private String teacherName;

    
    @Column(name = "teacher_phone_number", nullable = true)
    private String teacherPhoneNumber;

    @Valid
    @Embedded
    DesiredMajor desiredMajor;

    @AssertFalse(message = "DesiredMajor 가 null 임")
    private boolean isDesiredMajorNull() {
        return desiredMajor == null;
    }

}

