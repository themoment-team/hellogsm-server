package team.themoment.hellogsm.web.domain.user.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.ExistUserQuery;

/**
 * ExistUserQuery의 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class ExistUserQueryImpl implements ExistUserQuery {
    private final UserRepository userRepository;

    /**
     * provider와 providerId를 인자로 받아서 유저 유무를 판별합니다.
     *
     * @param provider user의 유무를 판별할 provider(oauth 공급자)
     * @param providerId user의 유무를 판별할 providerId(oauth 공급자를 식별하는 고유 식별자)
     * @return 유저 유무를 판별한 Boolean
     */
    @Override
    public Boolean execute(String provider, String providerId) {
        return userRepository.existsByProviderAndProviderId(provider, providerId);
    }
}
