package team.themoment.hellogsm.web.domain.user.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.web.domain.user.dto.mapper.UserMapper;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.UserByProviderQuery;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * UserByProviderQuery의 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class UserByProviderQueryImpl implements UserByProviderQuery {
    private final UserRepository userRepository;

    /**
     * provider와 providerId를 인자로 받아서 user를 찾아오고,
     * 찾아온 user 정보가 담긴 DTO를 반환합니다.
     *
     * @param provider 찾아올 user에 대한 provider(oauth 공급자)
     * @param providerId 찾아올 user에 대한 providerId(oauth 공급자를 식별하는 고유 식별자)
     * @return 찾아온 user 정보가 담긴 DTO
     * @throws ExpectedException user가 존재하지 않을 경우 발생
     */
    @Override
    public UserDto execute(String provider, String providerId) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
}
