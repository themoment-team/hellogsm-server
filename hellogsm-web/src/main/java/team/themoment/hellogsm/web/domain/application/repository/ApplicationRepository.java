package team.themoment.hellogsm.web.domain.application.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;

import java.util.List;
import java.util.Optional;

/**
 * application을 위한 repository 입니다
 */
@XRayEnabled
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUserId(Long userId);
    Boolean existsByUserId(Long userId);

    @Query("select a from Application a join fetch a.admissionGrade join fetch a.admissionInfo join fetch a.admissionStatus join fetch a.middleSchoolGrade where a.userId = :userId")
    Optional<Application> findByUserIdEagerFetch(Long userId);

    Page<Application> findAll(Pageable pageable);

    Page<Application> findAllByAdmissionInfoApplicantNameContainingAndAdmissionStatus_IsFinalSubmitted(String keyword, Boolean isFinalSubmitted, Pageable pageable);

    Page<Application> findAllByAdmissionInfoSchoolNameContainingAndAdmissionStatus_IsFinalSubmitted(String keyword, Boolean isFinalSubmitted, Pageable pageable);

    @Query("SELECT a FROM Application a WHERE " +
            "(a.admissionInfo.applicantPhoneNumber LIKE %:keyword% OR " +
            "a.admissionInfo.guardianPhoneNumber LIKE %:keyword% OR " +
            "a.admissionInfo.teacherPhoneNumber LIKE %:keyword%) " +
            "AND a.admissionStatus.isFinalSubmitted = TRUE")
    Page<Application> findAllByIsFinalSubmittedAndPhoneNumberContaining(@Param("keyword") String keyword, Pageable pageable);

    Page<Application> findAllByAdmissionStatusIsFinalSubmitted(Boolean isFinalSubmitted, Pageable pageable);

    void deleteApplicationByUserId(Long userId);

    List<Application> findAllByAdmissionStatus_FirstEvaluation(EvaluationStatus evaluationStatus);
}
