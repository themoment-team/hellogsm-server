package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.service.AuthenticateCodeService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * CreateIdentityService의 기본 구현체입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class AuthenticateCodeServiceImpl implements AuthenticateCodeService {

    private final CodeRepository codeRepository;

    @Override
    public void execute(Long userId, AuthenticateCodeReqDto reqDto) {
        List<AuthenticationCode> codes = codeRepository.findByUserId(userId);
        AuthenticationCode recentCode = codes.stream()
                .max(Comparator.comparing(AuthenticationCode::getCreatedAt))
                .orElseThrow(() -> new ExpectedException("사용자의 code가 존재하지 않습니다. 사용자의 ID : "+userId, HttpStatus.BAD_REQUEST));
        if (!recentCode.getCode().equals(reqDto.code()))
            throw new ExpectedException("유효하지 않은 code 입니다. 이전 혹은 잘못된 code입니다.", HttpStatus.BAD_REQUEST);
        codeRepository.save(new AuthenticationCode(recentCode.getCode(), recentCode.getUserId(), true, recentCode.getCreatedAt()));
    }
}
