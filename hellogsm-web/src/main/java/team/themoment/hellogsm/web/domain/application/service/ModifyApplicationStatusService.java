package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationStatusReqDto;

public interface ModifyApplicationStatusService {
    void execute(Long userId, ApplicationStatusReqDto applicationStatusReqDto);
}
