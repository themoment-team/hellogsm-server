package kr.hellogsm.back_v2.domain.application.repository;

import kr.hellogsm.back_v2.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
