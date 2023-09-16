package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;

/**
 * 유저 탈퇴시 호출되는 원서 삭제 service interface 입니다.
 */
public interface CascadeDeleteApplicationService {
    void execute(Long userId);
}
