package kr.hellogsm.back_v2.domain.user.repository;

import kr.hellogsm.back_v2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByProviderAndProviderId(String provider, String providerId);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
