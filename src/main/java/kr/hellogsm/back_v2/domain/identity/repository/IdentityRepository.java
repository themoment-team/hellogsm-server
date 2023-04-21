package kr.hellogsm.back_v2.domain.identity.repository;

import kr.hellogsm.back_v2.domain.identity.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Identity Entity의 JpaRepository입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);
}
