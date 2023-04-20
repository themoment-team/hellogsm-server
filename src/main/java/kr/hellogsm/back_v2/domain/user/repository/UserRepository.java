package kr.hellogsm.back_v2.domain.user.repository;

import kr.hellogsm.back_v2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByProviderAndProviderId(String provider, String providerId);
}
