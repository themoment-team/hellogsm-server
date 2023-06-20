package team.themoment.hellogsm.web.domain.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsm.entity.domain.application.entity.Application;

import java.util.List;
import java.util.Optional;

/**
 * application을 위한 repository 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUserId(Long userId);
    Boolean existsByUserId(Long userId);

    @Query("select a from Application a join fetch a.admissionGrade join fetch a.admissionInfo join fetch a.admissionStatus join fetch a.middleSchoolGrade")
    Optional<Application> findByUserIdEagerFetch(Long userId);

    @Query("select a from Application a join fetch a.admissionInfo join fetch a.admissionStatus")
    List<Application> findAllEagerFetch();
}
