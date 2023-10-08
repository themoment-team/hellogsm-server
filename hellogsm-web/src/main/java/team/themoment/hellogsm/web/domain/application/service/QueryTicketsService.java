package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.TicketResDto;

import java.util.List;

/**
 * 모든 사용자의 수험표 정보를 조회하는 service interface 입니다.
 */
public interface QueryTicketsService {
    List<TicketResDto> execute();
}
