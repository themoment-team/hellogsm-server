package team.themoment.hellogsm.web.domain.application.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;

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

    @Query("SELECT a FROM Application a " +
            "WHERE a.admissionInfo.applicantName LIKE %:keyword% " +
            "AND a.admissionStatus.isFinalSubmitted = :isFinalSubmitted " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN a.admissionStatus.secondEvaluation = 'PASS' THEN 30 " +
            "       WHEN a.admissionStatus.secondEvaluation = 'FALL' THEN 20 " +
            "       ELSE 10 " +
            "   END DESC, " +
            "   CASE " +
            "       WHEN a.admissionStatus.firstEvaluation = 'PASS' THEN 3 " +
            "       WHEN a.admissionStatus.firstEvaluation = 'FALL' THEN 2 " +
            "       ELSE 1 " +
            "   END DESC")
    Page<Application> findAllByIsFinalSubmittedAndApplicantNameContaining(@Param("keyword") String keyword, @Param("isFinalSubmitted") Boolean isFinalSubmitted, Pageable pageable);

    @Query("SELECT a FROM Application a " +
            "WHERE a.admissionInfo.schoolName LIKE %:keyword% " +
            "AND a.admissionStatus.isFinalSubmitted = :isFinalSubmitted " +
            "ORDER BY " +
            "    CASE " +
            "        WHEN a.admissionStatus.secondEvaluation = 'PASS' THEN 30 " +
            "        WHEN a.admissionStatus.secondEvaluation = 'FALL' THEN 20 " +
            "        ELSE 10 " +
            "    END DESC, " +
            "    CASE " +
            "        WHEN a.admissionStatus.firstEvaluation = 'PASS' THEN 3 " +
            "        WHEN a.admissionStatus.firstEvaluation = 'FALL' THEN 2 " +
            "        ELSE 1 " +
            "    END DESC")
    Page<Application> findAllByIsFinalSubmittedAndSchoolNameContaining(@Param("keyword") String keyword, @Param("isFinalSubmitted") Boolean isFinalSubmitted, Pageable pageable);

    @Query("SELECT a FROM Application a " +
            "WHERE " +
            "(a.admissionInfo.applicantPhoneNumber LIKE %:keyword% OR " +
            "a.admissionInfo.guardianPhoneNumber LIKE %:keyword% OR " +
            "a.admissionInfo.teacherPhoneNumber LIKE %:keyword%) " +
            "AND a.admissionStatus.isFinalSubmitted = TRUE " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN a.admissionStatus.secondEvaluation = 'PASS' THEN 30 " +
            "       WHEN a.admissionStatus.secondEvaluation = 'FALL' THEN 20 " +
            "       ELSE 10 " +
            "   END DESC, " +
            "       CASE " +
            "       WHEN a.admissionStatus.firstEvaluation = 'PASS' THEN 3 " +
            "       WHEN a.admissionStatus.firstEvaluation = 'FALL' THEN 2 " +
            "       ELSE 1 " +
            "   END DESC")
    Page<Application> findAllByIsFinalSubmittedAndPhoneNumberContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT a FROM Application a " +
            "WHERE " +
            "a.admissionStatus.isFinalSubmitted = :isFinalSubmitted " +
            "ORDER BY " +
            "    CASE " +
            "        WHEN a.admissionStatus.secondEvaluation = 'PASS' THEN 30 " +
            "        WHEN a.admissionStatus.secondEvaluation = 'FALL' THEN 20 " +
            "        ELSE 10 " +
            "    END DESC, " +
            "    CASE " +
            "        WHEN a.admissionStatus.firstEvaluation = 'PASS' THEN 3 " +
            "        WHEN a.admissionStatus.firstEvaluation = 'FALL' THEN 2 " +
            "        ELSE 1 " +
            "    END DESC")
    Page<Application> findAllByIsFinalSubmitted(@Param("isFinalSubmitted") Boolean isFinalSubmitted, Pageable pageable);

    void deleteApplicationByUserId(Long userId);

    List<Application> findAllByAdmissionStatus_FirstEvaluation(EvaluationStatus evaluationStatus);

    List<Application> findAllByAdmissionStatusFirstEvaluationOrAdmissionStatusSecondEvaluationAndAdmissionStatusIsFinalSubmitted(EvaluationStatus firstEvaluationStatus, EvaluationStatus secondEvaluationStatus, Boolean isFinalSubmitted);

    Boolean existsByAdmissionStatusFirstEvaluation(EvaluationStatus evaluationStatus);

    Boolean existsByAdmissionStatusSecondEvaluation(EvaluationStatus evaluationStatus);

    List<Application> findAllByAdmissionInfoScreeningAndAdmissionStatusIsFinalSubmitted(Screening screening, Boolean isFinalSubmitted);

    List<Application> findAllByAdmissionStatusScreeningFirstEvaluationAtAndAdmissionStatusIsFinalSubmitted(Screening screening, Boolean isFinalSubmitted);

    List<Application> findAllByAdmissionStatusScreeningSecondEvaluationAtAndAdmissionStatusSecondEvaluationNotAndAdmissionStatusIsFinalSubmitted(Screening screening, EvaluationStatus evaluationStatus, Boolean isFinalSubmitted);

    List<Application> findAllByAdmissionStatusFirstEvaluationAndAdmissionStatusIsFinalSubmitted(EvaluationStatus evaluationStatus, Boolean isFinalSubmitted);
}
