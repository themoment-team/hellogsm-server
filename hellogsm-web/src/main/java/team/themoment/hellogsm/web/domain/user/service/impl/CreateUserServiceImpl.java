package team.themoment.hellogsm.web.domain.user.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.web.domain.user.dto.mapper.UserMapper;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.CreateUserService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * CreateUserService의 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateUserServiceImpl implements CreateUserService {
    private final UserRepository userRepository;

    /**
     * user를 생성합니다.
     *
     * @param createUserReqDto 생성할 user 정보가 담긴 DTO
     * @return 생성된 user 정보를 담은 DTO
     */
    @Override
    public UserDto execute(CreateUserReqDto createUserReqDto) {
        checkExistUser(createUserReqDto);
        User savedTemp = userRepository.save(UserMapper.INSTANCE.createUserReqDtoToUser(createUserReqDto));
        return UserMapper.INSTANCE.userToUserDto(savedTemp);
    }

    /**
     * user의 유무를 판별합니다.
     *
     * @param createUserReqDto 생성할 user 정보가 담긴 DTO
     * @throws ExpectedException 이미 존재하는 user 일때 발생
     */
    private void checkExistUser(CreateUserReqDto createUserReqDto) {
        Boolean isExist = userRepository.existsByProviderAndProviderId(
                createUserReqDto.provider(),
                createUserReqDto.providerId()
        );
        if (isExist)
            throw new ExpectedException("이미 존재하는 User 입니다", HttpStatus.BAD_REQUEST);
    }
}
