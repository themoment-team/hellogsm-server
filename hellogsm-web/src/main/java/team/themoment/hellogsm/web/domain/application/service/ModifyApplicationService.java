package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;

/**
 * 현재 사용자의 원서를 수정하는 service interface 입니다.
 */
public interface ModifyApplicationService {
    void execute(ApplicationReqDto body, Long userId, boolean isAdmin);
}
