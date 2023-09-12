package team.themoment.hellogsm.web.domain.user.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsm.entity.domain.user.entity.User;

import java.util.Optional;

@XRayEnabled
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByProviderAndProviderId(String provider, String providerId);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
