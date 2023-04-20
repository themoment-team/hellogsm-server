package kr.hellogsm.back_v2.domain.user.service.impl;

import kr.hellogsm.back_v2.domain.user.dto.request.CreateUserReqDto;
import kr.hellogsm.back_v2.domain.user.dto.response.UserResDto;
import kr.hellogsm.back_v2.domain.user.entity.User;
import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.domain.user.service.CreateUserService;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateUserServiceImpl implements CreateUserService {

    private final UserRepository userRepository;

    @Override
    public UserResDto execute(CreateUserReqDto createUserReqDto) {
        checkExistUser(createUserReqDto);
        User savedTemp = userRepository.save(createUserReqDto.toEntity());
        return UserResDto.from(savedTemp);
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
