package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;

public interface CascadeDeleteApplicationService {
    void execute(Long userId);
}
