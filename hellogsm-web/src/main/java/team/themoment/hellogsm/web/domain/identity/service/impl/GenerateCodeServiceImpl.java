package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.service.GenerateCodeService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

@Component
@RequiredArgsConstructor
public class GenerateCodeServiceImpl implements GenerateCodeService {

    private final static Random RANDOM = new Random();

    public final static int DIGIT_NUMBER = 6;
    public final static int MAX = (int) Math.pow(10, DIGIT_NUMBER) - 1;

    private final CodeRepository codeRepository;

    @Override
    public String execute(Long userId) {
        String code;
        do {
            code = getRandomCode();
        } while (isDuplicate(code));
        long count = codeRepository.findByUserId(userId).stream().count();
        if (count >= 5) throw new ExpectedException("너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.FORBIDDEN);
        codeRepository.save(new AuthenticationCode(code, userId, false, LocalDateTime.now()));
        // TODO send SMS
        return code;
    }

    public static String getRandomCode() {
        return String.format("%0"+DIGIT_NUMBER+"d", RANDOM.nextInt(0, MAX + 1));
    }

    private Boolean isDuplicate(String code) {
        return codeRepository.findById(code).isPresent();
    }
}
