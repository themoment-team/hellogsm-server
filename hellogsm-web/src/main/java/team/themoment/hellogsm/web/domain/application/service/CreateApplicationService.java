package team.themoment.hellogsm.web.domain.application.service;


import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;

/**
 * 원서를 생성하는 service interface 입니다.
 */
public interface CreateApplicationService {
    void execute(ApplicationReqDto body, Long userId);
}
