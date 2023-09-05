package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.SearchApplicationsResDto;
import team.themoment.hellogsm.web.domain.application.enums.SearchTag;

public interface SearchApplicationsService {
    SearchApplicationsResDto execute(Integer page, Integer size, SearchTag tag, String keyword);
}
