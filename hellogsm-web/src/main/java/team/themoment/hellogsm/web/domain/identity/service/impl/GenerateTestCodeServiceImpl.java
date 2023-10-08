package team.themoment.hellogsm.web.domain.identity.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.service.GenerateCodeService;
import team.themoment.hellogsm.web.domain.identity.service.GenerateTestCodeService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@XRayEnabled
@RequiredArgsConstructor
public class GenerateTestCodeServiceImpl implements GenerateTestCodeService {
    private final static Random RANDOM = new Random();
    public final static int DIGIT_NUMBER = GenerateCodeServiceImpl.DIGIT_NUMBER;
    public final static int MAX = GenerateCodeServiceImpl.MAX;

    private final CodeRepository codeRepository;

    @Override
    public String execute(Long userId, GenerateCodeReqDto reqDto) {
        final String code = generateUniqueCode();
        codeRepository.save(new AuthenticationCode(code, userId, false, reqDto.phoneNumber(), LocalDateTime.now()));
        return code;
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = getRandomCode();
        } while (isDuplicate(code));
        return code;
    }

    private Boolean isDuplicate(String code) {
        return codeRepository.findById(code).isPresent();
    }

    public static String getRandomCode() {
        return String.format("%0" + DIGIT_NUMBER + "d", RANDOM.nextInt(0, MAX + 1));
    }
}
