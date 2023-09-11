package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.web.domain.application.dto.response.TicketResDto;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.QueryTicketsService;

import java.util.List;

@Service
@XRayEnabled
@RequiredArgsConstructor
public class QueryTicketsServiceImpl implements QueryTicketsService {
    final private ApplicationRepository applicationRepository;

    @Override
    public List<TicketResDto> execute() {
        List<Application> applicationList =
                applicationRepository.findAllByAdmissionStatus_FirstEvaluation(EvaluationStatus.PASS);

        return ApplicationMapper.INSTANCE.applicationListToTicketResDtos(applicationList);
    }
}
