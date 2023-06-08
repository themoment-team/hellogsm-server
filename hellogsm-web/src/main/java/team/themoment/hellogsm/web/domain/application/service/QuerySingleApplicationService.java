package team.themoment.hellogsm.web.domain.application.service;

import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationGrade;

public interface QuerySingleApplicationService {
    SingleApplicationGrade execute(Long applicantId);
}
