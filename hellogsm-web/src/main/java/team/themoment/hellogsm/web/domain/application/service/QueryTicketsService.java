package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.TicketResDto;

import java.util.List;

public interface QueryTicketsService {
    List<TicketResDto> execute();
}
