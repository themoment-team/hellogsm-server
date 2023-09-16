package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationStatusReqDto;

/**
 * 특정 사용자의 원서 상태를 변경하는 service interface 입니다.
 */
public interface ModifyApplicationStatusService {
    void execute(Long userId, ApplicationStatusReqDto applicationStatusReqDto);
}
