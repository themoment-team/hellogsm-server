package kr.hellogsm.back_v2.domain.temporary.repository;

import kr.hellogsm.back_v2.domain.temporary.entity.Temporary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryRepository extends JpaRepository<Temporary, Long> {
    Boolean existsByProviderAndProviderId(String provider, String providerId);
}
