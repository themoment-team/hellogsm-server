package team.themoment.hellogsm.web.domain.application.service;


import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;

/**
 * 원서 생성을 위한 service 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public interface CreateApplicationService {
    void execute(ApplicationReqDto body, Long userId);
}
