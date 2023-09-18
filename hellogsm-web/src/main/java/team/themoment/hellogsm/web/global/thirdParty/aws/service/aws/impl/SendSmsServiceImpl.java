package team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import io.awspring.cloud.sns.sms.SmsMessageAttributes;
import io.awspring.cloud.sns.sms.SmsType;
import io.awspring.cloud.sns.sms.SnsSmsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.SendSmsService;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.template.AwsTemplate;

/**
 * SendSmsService의 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
@Slf4j
public class SendSmsServiceImpl implements SendSmsService {
    private final static String MAX_PRICE = "1.00"; // 서버에서 이게 필요한가? 어차피 AWS 설정하면 되는데
    private final static String SENDER_ID = "hello-gsm";
    private final static String KR_CODE = "+82";
    private final SnsSmsTemplate smsTemplate;
    private final AwsTemplate<Void> executeWithExHandle;

    /**
     * 메세지를 전송합니다.
     *
     * @param phoneNumber 전송할 핸드폰 번호
     * @param message     전송할 메세지
     */
    @Override
    public void execute(String phoneNumber, String message) {
        executeWithExHandle.execute(() -> {
            smsTemplate.send(
                    createPhoneNumber(phoneNumber),
                    message,
                    SmsMessageAttributes.builder()
                            .smsType(SmsType.TRANSACTIONAL)
                            .maxPrice(MAX_PRICE)
                            .senderID(SENDER_ID)
                            .build()
            );
            return null;
        });
    }

    /**
     * 국가를 식별할 수 있는 phoneNumber를 생성합니다.
     *
     * @param phoneNumber 전송할 핸드폰 번호
     * @return 대한민국의 국제 전화 코드와 phoneNumber를 조합한 문자열을 반환합니다.
     */
    private static String createPhoneNumber(String phoneNumber) {
        return KR_CODE + phoneNumber;
    }
}
