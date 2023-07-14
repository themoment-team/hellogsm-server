package team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.impl;

import io.awspring.cloud.sns.sms.SmsMessageAttributes;
import io.awspring.cloud.sns.sms.SmsType;
import io.awspring.cloud.sns.sms.SnsSmsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.SendSmsService;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.template.AwsTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendSmsServiceImpl implements SendSmsService {
    private final static String MAX_PRICE = "1.00"; // 서버에서 이게 필요한가? 어차피 AWS 설정하면 되는데
    private final static String SENDER_ID = "hello-gsm";
    private final static String KR_CODE = "+82";
    private final SnsSmsTemplate smsTemplate;
    private final AwsTemplate<Void> executeWithExHandle;

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

    private static String createPhoneNumber(String phoneNumber) {
        return KR_CODE + phoneNumber;
    }
}
