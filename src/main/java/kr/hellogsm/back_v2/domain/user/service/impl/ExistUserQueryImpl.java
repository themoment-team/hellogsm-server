package kr.hellogsm.back_v2.domain.user.service.impl;

import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.domain.user.service.ExistUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExistUserQueryImpl implements ExistUserQuery {

    private final UserRepository userRepository;

    @Override
    public Boolean execute(String provider, String providerId) {
        return userRepository.existsByProviderAndProviderId(provider, providerId);
    }
}
