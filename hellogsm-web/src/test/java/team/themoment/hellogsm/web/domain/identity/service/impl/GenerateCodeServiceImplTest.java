package team.themoment.hellogsm.web.domain.identity.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.service.CodeNotificationService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateCodeServiceImplTest {

    @Mock
    private CodeRepository codeRepository;

    @Mock
    private CodeNotificationService codeNotificationService;

    @InjectMocks
    private GenerateCodeServiceImpl generateCodeService;

    @Test
    void 성공() {
        // given
        Long userId = 1L;
        GenerateCodeReqDto reqDto = new GenerateCodeReqDto("01012345678");
        given(codeRepository.findByUserId(anyLong())).willReturn(new ArrayList<>());

        AuthenticationCode authenticationCode = new AuthenticationCode(getCode(), userId, false, reqDto.phoneNumber(), LocalDateTime.now());
        given(codeRepository.save(any(AuthenticationCode.class))).willReturn(authenticationCode);

        doNothing().when(codeNotificationService).execute(anyString(), anyString());

        // when
        String generatedCode = generateCodeService.execute(userId, reqDto);

        // then
        assertNotNull(generatedCode);
    }

    @Test
    void 너무_많은_요청() {
        // given
        Long userId = 1L;
        GenerateCodeReqDto reqDto = new GenerateCodeReqDto("01012345678");
        List<AuthenticationCode> Limit_넘은_Codes = IntStream.range(0, GenerateCodeServiceImpl.LIMIT_COUNT_CODE_REQUEST)
                .mapToObj(i -> new AuthenticationCode(getCode(), userId, false, reqDto.phoneNumber(), LocalDateTime.now()))
                .collect(Collectors.toList());
        given(codeRepository.findByUserId(anyLong())).willReturn(Limit_넘은_Codes);

        // when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                generateCodeService.execute(userId, reqDto));

        String expectedMessage = String.format(
                "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요. 특정 시간 내 제한 횟수인 %d회를 초과하였습니다.",
                GenerateCodeServiceImpl.LIMIT_COUNT_CODE_REQUEST);

        assertEquals(expectedMessage, exception.getMessage());
    }

    String getCode() {
        return ReflectionTestUtils.invokeMethod(generateCodeService, "getRandomCode");
    }
}