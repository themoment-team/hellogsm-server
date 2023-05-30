package team.themoment.hellogsm.web.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.web.domain.user.dto.mapper.UserMapper;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.UserByProviderQuery;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class UserByProviderQueryImpl implements UserByProviderQuery {
    private final UserRepository userRepository;

    @Override
    public UserDto execute(String provider, String providerId) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
}
