package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;

public interface QuerySingleApplicationService {
    SingleApplicationRes execute(Long userId);
}
