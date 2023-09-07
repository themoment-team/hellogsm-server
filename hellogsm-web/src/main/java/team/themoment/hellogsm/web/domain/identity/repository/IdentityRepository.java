package team.themoment.hellogsm.web.domain.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

import java.util.Optional;

/**
 * Identity Entity의 JpaRepository입니다.
 */
public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);
}
