package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.SearchApplicationsResDto;
import team.themoment.hellogsm.web.domain.application.enums.SearchTag;

/**
 * 최종제출이 완료된 사용자를 검색하는 service interface 입니다.
 */
public interface SearchApplicationsService {
    SearchApplicationsResDto execute(Integer page, Integer size, SearchTag tag, String keyword);
}
