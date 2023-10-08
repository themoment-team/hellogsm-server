package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.ApplicationListDto;

/**
 * 모든 원서를 paging하여 조회하는 service interface 입니다.
 */
public interface ApplicationListQuery {
    ApplicationListDto execute(Integer page, Integer size);
}
