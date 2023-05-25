package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;

public interface ModifyApplicationService {
    void execute(ApplicationReqDto body, Long userId);
}
