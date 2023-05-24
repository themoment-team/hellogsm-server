package kr.hellogsm.back_v2.domain.application.service;
import kr.hellogsm.back_v2.domain.application.dto.request.ApplicationReqDto;

public interface ModifyApplicationService {
    void execute(ApplicationReqDto body, Long userId);
}
