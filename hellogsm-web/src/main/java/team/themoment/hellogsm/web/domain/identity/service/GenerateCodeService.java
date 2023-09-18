package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;

/**
 * code를 생성하는 인터페이스입니다.
 */
public interface GenerateCodeService {
    String execute(Long userId, GenerateCodeReqDto reqDto);
}
