package kr.hellogsm.back_v2.domain.application.service;

import kr.hellogsm.back_v2.domain.application.dto.request.CreateApplicationReqDto;

/**
 * 원서 생성을 위한 service 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
public interface CreateApplicationService {
    void execute(CreateApplicationReqDto body, Long userId);
}
