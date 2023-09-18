package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;

/**
 * AuthenticationCode를 인증하는 인터페이스입니다.
 * @see AuthenticationCode 참고해주세요.
 */
public interface AuthenticateCodeService {
    /**
     * AuthenticationCode를 인증합니다.
     *
     * @param userId code를 인증할 user의 userId
     * @param reqDto code를 인증하기 위한 정보가 담긴 DTO
     */
    void execute(Long userId, AuthenticateCodeReqDto reqDto);
}
