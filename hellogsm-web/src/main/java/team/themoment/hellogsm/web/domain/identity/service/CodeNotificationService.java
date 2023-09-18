package team.themoment.hellogsm.web.domain.identity.service;

/**
 * code를 전송하는 인터페이스입니다.
 */
public interface CodeNotificationService {
    /**
     *
     * @param phoneNumber 전송하는 핸드폰 번호
     * @param code 전송할 코드
     */
    void execute(String phoneNumber, String code);
}
