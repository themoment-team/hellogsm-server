package team.themoment.hellogsm.web.global.thirdParty.aws.service.aws;

/**
 * phoneNumber와 message를 받아서 message를 보내는 인터페이스입니다.
 */
public interface SendSmsService {
    void execute(String phoneNumber, String message);
}
