package team.themoment.hellogsm.web.domain.user.service.impl;

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

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateUserServiceImpl implements CreateUserService {
    private final UserRepository userRepository;

    @Override
    public UserDto execute(CreateUserReqDto createUserReqDto) {
        checkExistUser(createUserReqDto);
        User savedTemp = userRepository.save(UserMapper.INSTANCE.createUserReqDtoToUser(createUserReqDto));
        return UserMapper.INSTANCE.userToUserDto(savedTemp);
    }

    private void checkExistUser(CreateUserReqDto createUserReqDto) {
        Boolean isExist = userRepository.existsByProviderAndProviderId(
                createUserReqDto.provider(),
                createUserReqDto.providerId()
        );
        if (isExist)
            throw new ExpectedException("이미 존재하는 User 입니다", HttpStatus.BAD_REQUEST);
    }
}
