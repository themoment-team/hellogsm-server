package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ModifyIdentityServiceImplTest {

    @InjectMocks
    private ModifyIdentityServiceImpl modifyIdentityService;

    @Mock
    private IdentityRepository identityRepository;

    @Mock
    private CodeRepository codeRepository;

    @Mock
    private UserRepository userRepository;

    private final List<AuthenticationCode> codes = List.of(
            new AuthenticationCode("123456", 1L, true, "010-1234-5678", LocalDateTime.MAX),
            new AuthenticationCode("654321", 1L, true, "010-1234-5678", LocalDateTime.MIN));

    private final Identity identity = new Identity(1L, "이정우", "01012345678", LocalDate.EPOCH, Gender.MALE, 1L);

    private final IdentityReqDto reqDto = new IdentityReqDto("654321", "이정우", "010-1234-5678", "MALE", LocalDate.EPOCH);

    private void givenExistingUser(Boolean value){
        given(userRepository.existsById(any(Long.class))).willReturn(value);
    }

    private void givenValidIdentity(Boolean identityExists){
        given(identityRepository.findByUserId(any(Long.class))).willReturn(identityExists ? Optional.of(identity) : Optional.empty());
    }

    private void givenValidCode(List<AuthenticationCode> codes){
        given(codeRepository.findByUserId(any(Long.class))).willReturn(codes);
    }

    private void assertThrowsExpectedExceptionWithMessageAndUserId(String expectedMessage, Long userId){

        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> modifyIdentityService.execute(reqDto, userId));;

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    public void 성공(){
        //given
        givenExistingUser(true);
        givenValidIdentity(true);
        givenValidCode(codes);
        given(identityRepository.save(any(Identity.class))).willReturn(identity);
        doNothing().when(codeRepository).deleteById(any(String.class));

        //when
        IdentityDto result = modifyIdentityService.execute(reqDto, identity.getUserId());

        //then
        IdentityDto expectedResult = new IdentityDto(identity.getId(), identity.getName(), identity.getPhoneNumber(), identity.getBirth(), identity.getGender(), identity.getUserId());

        assertEquals(result, expectedResult);
    }

    @Test
    public void 존재하지_않는_User(){
        //given
        givenExistingUser(false);

        //when & then
        assertThrowsExpectedExceptionWithMessageAndUserId("존재하지 않는 User 입니다", identity.getUserId());
    }

    @Test
    public void 존재하지_않는_Identity(){
        //given
        givenExistingUser(true);
        givenValidIdentity(false);

        //when & then
        assertThrowsExpectedExceptionWithMessageAndUserId("존재하지 않는 Identity 입니다", identity.getUserId());
    }

    @Test
    public void 인증받지_않은_Code(){
        final List<AuthenticationCode> unauthenticatedCodes = List.of(
                new AuthenticationCode("123456", 1L, false, "010-1234-5678", LocalDateTime.MAX),
                new AuthenticationCode("654321", 1L, false, "010-1234-5678", LocalDateTime.MIN));

        //given
        givenExistingUser(true);
        givenValidIdentity(true);
        givenValidCode(unauthenticatedCodes);

        assertThrowsExpectedExceptionWithMessageAndUserId("유효하지 않은 요청입니다. 인증받지 않은 code입니다.", identity.getUserId());
    }
}
