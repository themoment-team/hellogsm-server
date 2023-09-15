package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.CascadeDeleteApplicationService;

import java.util.Optional;

@Slf4j
@Service
@XRayEnabled
@RequiredArgsConstructor
public class CascadeDeleteApplicationServiceImpl implements CascadeDeleteApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void execute(Long userId) {
        final Optional<Application> optionalApplication = applicationRepository.findByUserId(userId);

        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            if (application.getAdmissionStatus().getIsFinalSubmitted()) {
                log.warn("최종제출 된 Application 삭제 요청 발생, 최종제출 된 Application은 삭제되지 않습니다. - User Id: {}", userId);
            } else {
                applicationRepository.deleteById(application.getId());
            }
        } else {
            log.warn("존재하지 않는 Application 삭제 요청 발생 - User Id: {}", userId);
        }
    }
}
