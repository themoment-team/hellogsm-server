package kr.hellogsm.back_v2.domain.identity.repository;

import kr.hellogsm.back_v2.domain.identity.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);
}
