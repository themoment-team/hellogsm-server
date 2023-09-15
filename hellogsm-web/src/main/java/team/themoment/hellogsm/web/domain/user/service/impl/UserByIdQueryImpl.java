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

@Service
@XRayEnabled
@RequiredArgsConstructor
public class UserByIdQueryImpl implements UserByIdQuery {
    private final UserRepository userRepository;

    @Override
    public UserDto execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 User 입니다", HttpStatus.BAD_REQUEST));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
}
