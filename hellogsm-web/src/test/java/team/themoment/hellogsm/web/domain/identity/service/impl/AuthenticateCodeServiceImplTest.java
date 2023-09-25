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
    CodeRepository codeRepository;

    private final List<AuthenticationCode> code = List.of(
            new AuthenticationCode("123456", 1L, true, "010-1234-5678", LocalDateTime.now()),
            new AuthenticationCode("654321", 2L, false, "010-8765-4321", LocalDateTime.now()));

    private AuthenticateCodeReqDto createAuthenticateCodeReqDto(Integer codeIndex){
        return new AuthenticateCodeReqDto(code.get(codeIndex).getCode());
    }

    private void assertThrowsExpectedExceptionWithMessage(String expectedMessage, Integer codeIndex){
        AuthenticateCodeReqDto reqDto = createAuthenticateCodeReqDto(codeIndex);

        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> authenticateCodeService.execute(code.get(codeIndex).getUserId(), reqDto));;

        assertEquals(exception.getMessage(), expectedMessage);
    }

    private void givenValidCode(){
        given(codeRepository.findByUserId(any(Long.class))).willReturn(code);
    }

    @Test
    public void 성공(){
        //given
        givenValidCode();
        given(codeRepository.save(any(AuthenticationCode.class))).willReturn(code.get(1));

        //when & then
        AuthenticateCodeReqDto reqDto = createAuthenticateCodeReqDto(1);
        assertDoesNotThrow(() -> authenticateCodeService.execute(code.get(1).getUserId(), reqDto));
    }

    @Test
    public void 존재하지_않는_AuthenticateCode(){
        //given
        given(codeRepository.findByUserId(any(Long.class))).willReturn(Collections.emptyList());

        //when & then
        assertThrowsExpectedExceptionWithMessage("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + code.get(1).getUserId(), 1);
    }

    @Test
    public void 유효하지_않거나_최신이_아닌_AuthenticateCode(){
        //given
        givenValidCode();

        //when & then
        assertThrowsExpectedExceptionWithMessage("유효하지 않은 code 입니다. 이전 혹은 잘못된 code입니다.", 0);
    }
}
