package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;

/**
 * 사용자의 원서 정보를 가져오는 service interface 입니다.
 */
public interface QuerySingleApplicationService {
    SingleApplicationRes execute(Long userId);
}
