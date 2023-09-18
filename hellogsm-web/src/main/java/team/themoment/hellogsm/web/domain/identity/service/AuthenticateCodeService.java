package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;

/**
 * AuthenticateCode를 인증하는 인터페이스입니다.
 */
public interface AuthenticateCodeService {

    /**
     * AuthenticateCode를 인증합니다.
     *
     * @param userId code를 인증할 user의 userId
     * @param reqDto code를 인증하기 위한 정보가 담긴 DTO
     */
    void execute(Long userId, AuthenticateCodeReqDto reqDto);
}
