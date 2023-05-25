package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

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
