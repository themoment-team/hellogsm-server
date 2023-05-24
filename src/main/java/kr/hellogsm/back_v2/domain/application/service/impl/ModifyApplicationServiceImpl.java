package kr.hellogsm.back_v2.domain.application.service.impl;

import kr.hellogsm.back_v2.domain.application.dto.request.ApplicationReqDto;
import kr.hellogsm.back_v2.domain.application.entity.Application;
import kr.hellogsm.back_v2.domain.application.repository.ApplicationRepository;
import kr.hellogsm.back_v2.domain.application.service.ModifyApplicationService;
import kr.hellogsm.back_v2.domain.user.repository.UserRepository;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModifyApplicationServiceImpl implements ModifyApplicationService {
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public void execute(ApplicationReqDto body, Long userId) {
        if (!userRepository.existsById(userId))
            throw new ExpectedException("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST);
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("원서가 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        applicationRepository.save(body.toEntity(application.getId(), userId));
    }
}
