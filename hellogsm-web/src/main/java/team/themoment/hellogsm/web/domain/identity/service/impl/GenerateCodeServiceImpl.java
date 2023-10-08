package team.themoment.hellogsm.web.domain.identity.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.service.CodeNotificationService;
import team.themoment.hellogsm.web.domain.identity.service.GenerateCodeService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * GenerateCodeService의 구현체 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class GenerateCodeServiceImpl implements GenerateCodeService {

    private final static Random RANDOM = new Random();

    public final static int DIGIT_NUMBER = 6;
    public final static int LIMIT_COUNT_CODE_REQUEST = 5;  // code의 만료 시간은 AuthenticationCode에서 찾을 수 있음
    public final static int MAX = (int) Math.pow(10, DIGIT_NUMBER) - 1;

    private final CodeRepository codeRepository;
    private final CodeNotificationService codeNotificationService;

    /**
     * code를 생성하고 전송합니다.
     *
     * @param userId 코드를 생성하기 위한 user의 userId
     * @param reqDto 코드를 생성하기 위한 정보가 담긴 DTO
     * @return 생성된 code가 반환됩니다.
     * @throws ExpectedException 코드 생성 요청 횟수가 제한 횟수를 초과했을 경우에 발생
     */
    @Override
    public String execute(Long userId, GenerateCodeReqDto reqDto) {
        if (isLimitedRequest(userId))
            throw new ExpectedException(String.format(
                    "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요. 특정 시간 내 제한 횟수인 %d회를 초과하였습니다.",
                    LIMIT_COUNT_CODE_REQUEST), HttpStatus.FORBIDDEN);

        final String code = generateUniqueCode();
        codeRepository.save(new AuthenticationCode(code, userId, false, reqDto.phoneNumber(), LocalDateTime.now()));
        sendSMS(reqDto.phoneNumber(), code);
        return code;
    }

    /**
     * 요청 횟수를 판별합니다.
     *
     * @param userId 요청 횟수를 확인하기 위한 user의 userId
     * @return 요청 횟수가 초과하였는지 판별한 Boolean 값
     */
    private Boolean isLimitedRequest(Long userId) {
        long count = codeRepository.findByUserId(userId).stream().count();
        return count >= LIMIT_COUNT_CODE_REQUEST;
    }

    /**
     * SMS를 전송합니다.
     *
     * @param phoneNumber 전송하는 핸드폰 번호
     * @param code        전송할 코드
     */
    private void sendSMS(String phoneNumber, String code) {
        codeNotificationService.execute(phoneNumber, code);
    }

    /**
     * code를 생성합니다.
     *
     * @return 생성된 unique한 code가 반환됩니다.
     */
    private String generateUniqueCode() {
        String code;
        do {
            code = getRandomCode();
        } while (isDuplicate(code));
        return code;
    }

    /**
     * code가 중복되는지 판별합니다.
     *
     * @param code 전송할 코드
     * @return code의 중복을 판별한 Boolean 값
     */
    private Boolean isDuplicate(String code) {
        return codeRepository.findById(code).isPresent();
    }

    /**
     * code를 가져옵니다.
     *
     * @return random한 code를 반환합니다.
     */
    public static String getRandomCode() {
        return String.format("%0" + DIGIT_NUMBER + "d", RANDOM.nextInt(0, MAX + 1));
    }
}
