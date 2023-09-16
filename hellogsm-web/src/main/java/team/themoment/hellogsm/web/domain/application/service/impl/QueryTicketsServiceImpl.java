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

/**
 * 모든 사용자의 수험표 정보를 조회하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class QueryTicketsServiceImpl implements QueryTicketsService {
    final private ApplicationRepository applicationRepository;

    /**
     * 1차 평가를 통과한 사용자의 원서를 찾습니다. <br>
     * 찾은 원서를 바탕으로 필요한 정보만 추려 수험표 정보를 반환합니다.
     *
     * @return {@link TicketResDto} (List)모든 사용자의 수험표 정보
     */
    @Override
    public List<TicketResDto> execute() {
        List<Application> applicationList =
                applicationRepository.findAllByAdmissionStatus_FirstEvaluation(EvaluationStatus.PASS);

        return ApplicationMapper.INSTANCE.applicationListToTicketResDtos(applicationList);
    }
}
