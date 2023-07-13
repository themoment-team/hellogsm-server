package team.themoment.hellogsm.web.global.thirdParty.aws.service.aws;

public interface SendSmsService {
    void execute(String phoneNumber, String message);
}
