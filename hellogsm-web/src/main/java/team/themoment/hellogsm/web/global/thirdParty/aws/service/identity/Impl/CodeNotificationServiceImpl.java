package team.themoment.hellogsm.web.global.thirdParty.aws.service.identity.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import team.themoment.hellogsm.web.domain.identity.service.CodeNotificationService;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.SendSmsService;

@Component
@RequiredArgsConstructor
@Slf4j
public class CodeNotificationServiceImpl implements CodeNotificationService {
    private final SendSmsService sendSmsService;

    @Override
    public void execute(String phoneNumber, String code) {
        sendSmsService.execute(phoneNumber, createMessage(code));
    }

    private static String createMessage(String code) {
        return "[Hello, GSM] 본인인증번호 [" + code + "]를 입력해주세요.";
    }
}
