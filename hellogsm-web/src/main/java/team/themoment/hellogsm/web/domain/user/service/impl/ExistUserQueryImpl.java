package team.themoment.hellogsm.web.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.ExistUserQuery;

@Service
@RequiredArgsConstructor
public class ExistUserQueryImpl implements ExistUserQuery {

    private final UserRepository userRepository;

    @Override
    public Boolean execute(String provider, String providerId) {
        return userRepository.existsByProviderAndProviderId(provider, providerId);
    }
}
