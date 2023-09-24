package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthenticateCodeServiceImplTest {

    @InjectMocks
    private AuthenticateCodeServiceImpl authenticateCodeService;

    @Mock
    CodeRepository codeRepository;

    private final List<AuthenticationCode> code = List.of(
            new AuthenticationCode("123456", 1L, true, "010-1234-5678", LocalDateTime.now()),
            new AuthenticationCode("654321", 2L, false, "010-8765-4321", LocalDateTime.now()));

    private final AuthenticateCodeReqDto reqDto = new AuthenticateCodeReqDto(code.get(1).getCode());

    @Test
    public void 성공(){
        given(codeRepository.findByUserId(any(Long.class))).willReturn(code);
        given(codeRepository.save(any(AuthenticationCode.class))).willReturn(code.get(1));

        assertDoesNotThrow(() -> authenticateCodeService.execute(code.get(1).getUserId(), reqDto));
    }
}
