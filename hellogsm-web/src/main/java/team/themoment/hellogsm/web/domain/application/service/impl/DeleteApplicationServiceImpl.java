package team.themoment.hellogsm.web.domain.application.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.DeleteApplicationService;

@Service
@RequiredArgsConstructor
public class DeleteApplicationServiceImpl implements DeleteApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public void execute(Long userId) {
        applicationRepository.deleteApplicationByUserId(userId);
    }
}
