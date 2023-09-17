package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;

/**
 * userId와 AuthenticateCodeReqDto를 받아 AuthenticateCode를 인증하는 인터페이스입니다.
 */
public interface AuthenticateCodeService {
    void execute(Long userId, AuthenticateCodeReqDto reqDto);
}
