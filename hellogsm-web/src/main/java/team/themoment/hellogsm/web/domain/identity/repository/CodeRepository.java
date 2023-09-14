package team.themoment.hellogsm.web.domain.identity.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;

@XRayEnabled
public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {
    List<AuthenticationCode> findByUserId(Long userId);
}
