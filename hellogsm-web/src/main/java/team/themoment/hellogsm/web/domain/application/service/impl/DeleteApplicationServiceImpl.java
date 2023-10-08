package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.DeleteApplicationService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * 현재 사용자의 원서를 삭제하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class DeleteApplicationServiceImpl implements DeleteApplicationService {
    private final ApplicationRepository applicationRepository;

    /**
     * 원서를 찾고 해당 원서가 최종제출되지 않았다면 삭제합니다.
     *
     * @param userId 요청한 유저의 pk값
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 원서가 존재하지 않을 경우 <br>다
     *      2. 최종제출이 완료된 원서일 경우 <br>
     */
    @Override
    public void execute(Long userId) {
        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("원서가 존재하지 않습니다", HttpStatus.BAD_REQUEST));
        if (application.getAdmissionStatus().isFinalSubmitted())
            throw new ExpectedException("최종제출이 완료된 원서입니다", HttpStatus.BAD_REQUEST);
        applicationRepository.deleteApplicationByUserId(userId);
    }
}
