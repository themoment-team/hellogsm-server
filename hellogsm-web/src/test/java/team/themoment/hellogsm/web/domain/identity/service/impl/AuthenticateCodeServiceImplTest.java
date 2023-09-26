package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthenticateCodeServiceImplTest {

    @InjectMocks
    private AuthenticateCodeServiceImpl authenticateCodeService;

    @Mock
    private CodeRepository codeRepository;

    private final List<AuthenticationCode> codesDummy = List.of(
            new AuthenticationCode("123456", 1L, false, "010-1234-5678", LocalDateTime.now()),
            new AuthenticationCode("654321", 2L, true, "010-8765-4321", LocalDateTime.now()));

    private final AuthenticationCode pastAndInvalidAuthenticationCode = codesDummy.get(0);

    private final AuthenticationCode recentAuthenticationCode = codesDummy.get(1);

    private AuthenticateCodeReqDto createAuthenticateCodeReqDto(AuthenticationCode authenticationCode){
        return new AuthenticateCodeReqDto(authenticationCode.getCode());
    }

    private void assertThrowsExpectedExceptionWithMessageAndAuthenticationCode(String expectedMessage, AuthenticationCode authenticationCode){
        AuthenticateCodeReqDto reqDto = createAuthenticateCodeReqDto(authenticationCode);

        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> authenticateCodeService.execute(authenticationCode.getUserId(), reqDto));;

        assertEquals(exception.getMessage(), expectedMessage);
    }

    private void givenValidCode(){
        given(codeRepository.findByUserId(any(Long.class))).willReturn(codesDummy);
    }

    @Test
    public void Code를_찾고_최신_Code_를_저장합니다(){
        //given
        givenValidCode();
        given(codeRepository.save(any(AuthenticationCode.class))).willReturn(codesDummy.get(1));

        //when & then
        AuthenticateCodeReqDto reqDto = createAuthenticateCodeReqDto(recentAuthenticationCode);

        assertDoesNotThrow(() -> authenticateCodeService.execute(recentAuthenticationCode.getUserId(), reqDto));
    }

    @Test
    public void 존재하지_않는_AuthenticateCode일때_적절한_ExpectedException을_던진다(){
        //given
        given(codeRepository.findByUserId(any(Long.class))).willReturn(Collections.emptyList());

        //when & then
        assertThrowsExpectedExceptionWithMessageAndAuthenticationCode("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + recentAuthenticationCode.getUserId(), recentAuthenticationCode);
    }

    @Test
    public void 유효하지_않거나_최신이_아닌_AuthenticateCode일때_적절한_ExpectedException을_던진다(){
        //given
        givenValidCode();

        //when & then
        assertThrowsExpectedExceptionWithMessageAndAuthenticationCode("유효하지 않은 code 입니다. 이전 혹은 잘못된 code입니다.", pastAndInvalidAuthenticationCode);
    }
}
