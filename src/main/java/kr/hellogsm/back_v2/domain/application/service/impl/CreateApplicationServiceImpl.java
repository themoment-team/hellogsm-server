package kr.hellogsm.back_v2.domain.application.service.impl;

import kr.hellogsm.back_v2.domain.application.dto.request.CreateApplicationReqDto;
import kr.hellogsm.back_v2.domain.application.repository.ApplicationRepository;
import kr.hellogsm.back_v2.domain.application.service.CreateApplicationService;
import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * 원서 생성을 위한 service interface 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CreateApplicationServiceImpl implements CreateApplicationService {
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public void execute(CreateApplicationReqDto body, Long userId) {
        if (!userRepository.existsById(userId))
            throw new ExpectedException("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST);
        if (applicationRepository.existsByUserId(userId))
            throw new ExpectedException("원서가 이미 존재합니다", HttpStatus.BAD_REQUEST);

        applicationRepository.save(body.toEntity(userId));
    }
}
