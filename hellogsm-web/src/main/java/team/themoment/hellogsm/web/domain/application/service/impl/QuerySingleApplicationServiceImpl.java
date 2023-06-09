package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.QuerySingleApplicationService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QuerySingleApplicationServiceImpl implements QuerySingleApplicationService {
    final private ApplicationRepository applicationRepository;

    @Override
    public SingleApplicationRes execute(Long applicationId) {
        Application application = applicationRepository.findByUserIdEgerFetch(applicationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND));

        return ApplicationMapper.INSTANCE.CreateSingleApplicationDto(application);
    }
}
