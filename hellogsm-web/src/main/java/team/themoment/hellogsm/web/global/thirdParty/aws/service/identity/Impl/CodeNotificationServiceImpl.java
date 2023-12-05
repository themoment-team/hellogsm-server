package team.themoment.hellogsm.web.global.thirdParty.aws.service.identity.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import team.themoment.hellogsm.web.domain.identity.service.CodeNotificationService;
import team.themoment.hellogsm.web.domain.application.service.SendSmsService;

/**
 * CodeNotificationService의 구현체입니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CodeNotificationServiceImpl implements CodeNotificationService {
    private final SendSmsService sendSmsService;

    /**
     * 코드를 전송합니다.
     *
     * @param phoneNumber 전송하는 핸드폰 번호
     * @param code        전송할 코드
     * @see SendSmsService
     */
    @Override
    public void execute(String phoneNumber, String code) {
        sendSmsService.execute(phoneNumber, createMessage(code));
    }

    /**
     * 메세지를 생성합니다.
     *
     * @param code 전송할 코드
     * @return 인자로 받은 코드를 조합한 메세지 문자열을 반환합니다.
     */
    private static String createMessage(String code) {
        return "[Hello, GSM] 본인인증번호 [" + code + "]를 입력해주세요.";
    }
}
