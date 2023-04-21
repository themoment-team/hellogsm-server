package kr.hellogsm.back_v2.domain.user.service.impl;

import kr.hellogsm.back_v2.domain.user.dto.response.UserResDto;
import kr.hellogsm.back_v2.domain.user.entity.User;
import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.domain.user.service.UserByProviderQuery;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserByProviderQueryImpl implements UserByProviderQuery {
    private final UserRepository userRepository;

    @Override
    public UserResDto execute(String provider, String providerId) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new ExpectedException("이미 존재하는 Identity 입니다", HttpStatus.BAD_REQUEST));
        return UserResDto.from(user);
    }
}