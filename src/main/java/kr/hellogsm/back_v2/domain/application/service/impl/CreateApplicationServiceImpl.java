package kr.hellogsm.back_v2.domain.application.service.impl;

import kr.hellogsm.back_v2.domain.application.dto.request.CreateApplicationReqDto;
import kr.hellogsm.back_v2.domain.application.repository.ApplicationRepository;
import kr.hellogsm.back_v2.domain.application.service.CreateApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 원서 생성을 위한 service interface 입니다
 */
@Service
@RequiredArgsConstructor
public class CreateApplicationServiceImpl implements CreateApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    public void execute(CreateApplicationReqDto body) {
        applicationRepository.save(body.toEntity());
    }
}
