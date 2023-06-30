package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.DeleteApplicationService;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class DeleteApplicationServiceImpl implements DeleteApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    public void execute(Long userId) {
        applicationRepository.deleteApplicationByUserId(userId);
    }
}
