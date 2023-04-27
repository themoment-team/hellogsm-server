package kr.hellogsm.back_v2.domain.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.hellogsm.back_v2.domain.application.dto.request.CreateApplicationReqDto;

/**
 * 원서 생성을 위한 service 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public interface CreateApplicationService {
    public void execute(CreateApplicationReqDto body) throws JsonProcessingException;
}
