package team.themoment.hellogsm.web.domain.identity.service;

/**
 * phoneNumber와 code를 받아서 code를 전송하는 인터페이스입니다.
 */
public interface CodeNotificationService {
    void execute(String phoneNumber, String code);
}
