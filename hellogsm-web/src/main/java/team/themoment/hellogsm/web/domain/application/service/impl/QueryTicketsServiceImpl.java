package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.web.domain.application.dto.response.TicketResDto;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.QueryTicketsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryTicketsServiceImpl implements QueryTicketsService {
    final private ApplicationRepository applicationRepository;

    @Override
    public List<TicketResDto> execute(int page, int size) {
        Pageable pageable =  PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Application> applicationList =
                applicationRepository.findAllByAdmissionStatus_FirstEvaluation(EvaluationStatus.PASS, pageable);

        return ApplicationMapper.INSTANCE.ApplicationListToTicketResDtoList(applicationList.getContent());
    }
}
