package team.themoment.hellogsm.entity.domain.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.grade.*;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;

/**
 * 원서의 신청 정보를 저장하는 Entity입니다. <br>
 * cascade = CascadeType.ALL, orphanRemoval = true 설정으로 관련된 엔티티들의 라이프사이클을 관리합니다.
 * FetchType.LAZY 설정으로 필요한 경우에만 연관된 엔티티를 로딩합니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Entity
@Table(name = "application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private AdmissionInfo admissionInfo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private AdmissionStatus admissionStatus;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private MiddleSchoolGrade middleSchoolGrade;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private AdmissionGrade admissionGrade;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Application(Long id, AdmissionInfo admissionInfo, AdmissionStatus admissionStatus, MiddleSchoolGrade middleSchoolGrade, Long userId) {
        this.id = id;
        this.admissionInfo = admissionInfo;
        this.admissionStatus = admissionStatus;
        this.middleSchoolGrade = middleSchoolGrade;
        this.admissionGrade = AdmissionGradeFactory.create(admissionInfo.getGraduation(), middleSchoolGrade);
        this.userId = userId;
    }

    /**
     * 원서의 신청 정보와, 중학교 성적 구현체의 일관성을 검증합니다.
     *
     * @return 일관성 여부 (true: 일관성 만족, false: 일관성 불만족)
     */
    @AssertTrue(message = "admissionInfo의 graduationStatus와 AdmissionGrade의 형식이 일치하지 않습니다.")
    private boolean hasConsistencyWithGraduationStatusAndAdmissionGrade() { // 이름;;
        GraduationStatus graduationStatus = admissionInfo.getGraduation();
        boolean isGedGraduationStatus = graduationStatus.equals(GraduationStatus.GED);
        boolean isGraduateOrCandidateGraduationStatus = graduationStatus.equals(GraduationStatus.GRADUATE)
                || graduationStatus.equals(GraduationStatus.CANDIDATE);

        boolean isGedAdmissionGrade = admissionGrade instanceof GedAdmissionGrade;
        boolean isGraduateAdmissionGrade = admissionGrade instanceof GraduateAdmissionGrade;

        return (isGedGraduationStatus && isGedAdmissionGrade)
                || (isGraduateOrCandidateGraduationStatus && isGraduateAdmissionGrade);
    }
}
