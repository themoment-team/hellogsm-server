package team.themoment.hellogsm.web.domain.identity.service;

import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;

/**
 * code를 전송하는 인터페이스입니다.
 * @see GenerateCodeService
 */
public interface CodeNotificationService {
    /**
     * code를 전송합니다.
     *
     * @param phoneNumber 전송하는 핸드폰 번호
     * @param code 전송할 코드
     */
    void execute(String phoneNumber, String code);
}
