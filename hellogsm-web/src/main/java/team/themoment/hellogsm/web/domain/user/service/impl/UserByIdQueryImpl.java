package team.themoment.hellogsm.web.domain.user.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.web.domain.user.dto.mapper.UserMapper;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.UserByIdQuery;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * UserByIdQuery의 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class UserByIdQueryImpl implements UserByIdQuery {
    private final UserRepository userRepository;

    /**
     * user를 찾아서 가져옵니다.
     *
     * @param userId 찾아올 user에 대한 userId
     * @return 찾아온 user 정보가 담긴 DTO
     * @throws ExpectedException user가 존재하지 않을 경우 발생
     */
    @Override
    public UserDto execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
}
