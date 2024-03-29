package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.response.CreateIdentityResDto;
import team.themoment.hellogsm.web.domain.identity.repository.CodeRepository;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class CreateIdentityServiceImplTest {

    @InjectMocks
    private CreateIdentityServiceImpl service;

    @Mock
    private IdentityRepository identityRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CodeRepository codeRepository;

    private final IdentityReqDto identityReqDto = new IdentityReqDto("012345", "홍길동", "01012345678", "MALE", LocalDate.EPOCH);

    private final Identity identity = new Identity(1L, "홍길동", "01012345678", LocalDate.EPOCH, Gender.MALE, 1L);

    private final User user = new User(1L, "google", "123456789", Role.ROLE_USER);

    private void givenValidUserAndIdentity() {
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(identityRepository.existsByUserId(any(Long.class))).willReturn(false);
    }

    private void givenValidCodes() {
        final List<AuthenticationCode> validCodes = List.of(
                new AuthenticationCode("012345", 1L, true, "01012345678", LocalDateTime.MAX),
                new AuthenticationCode("654321", 1L, true, "01012345678", LocalDateTime.MIN)
        );
        given(codeRepository.findByUserId(any(Long.class))).willReturn(validCodes);
    }


    @Test
    public void 성공() {
        //given
        givenValidUserAndIdentity();
        givenValidCodes();
        given(identityRepository.save(any(Identity.class))).willReturn(identity);
        doNothing().when(codeRepository).deleteById(any(String.class));

        //when
        CreateIdentityResDto resDto = service.execute(identityReqDto, 1L);

        //then
        Assertions.assertNotNull(resDto);
    }

    @Test
    public void 존재하지_않는_User() {
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                service.execute(identityReqDto, 1L));

        String expectedMessage = String.format("존재하지 않는 User 입니다");

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void 이미_존재하는_Identity() {
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(identityRepository.existsByUserId(any(Long.class))).willReturn(true);

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                service.execute(identityReqDto, 1L));

        String expectedMessage = String.format("이미 존재하는 Identity 입니다");

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void 인증받지_않은_code() {
        //given
        final List<AuthenticationCode> 최신_Code가_인증받지_않은_상태 = List.of(
                new AuthenticationCode("012345", 1L, false, "01012345678", LocalDateTime.MAX),
                new AuthenticationCode("654321", 1L, true, "01012345678", LocalDateTime.MIN)
        );
        givenValidUserAndIdentity();
        given(codeRepository.findByUserId(any(Long.class))).willReturn(최신_Code가_인증받지_않은_상태);

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                service.execute(identityReqDto, 1L));

        String expectedMessage = String.format("유효하지 않은 요청입니다. 인증받지 않은 code입니다.");

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void 유효하지_않은_code() {
        //given
        final IdentityReqDto 인증코드가_다른_Identity
                = new IdentityReqDto("000000", "홍길동", "01012345678", "MALE", LocalDate.EPOCH);
        givenValidUserAndIdentity();
        givenValidCodes();

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                service.execute(인증코드가_다른_Identity, 1L));

        String expectedMessage = String.format("유효하지 않은 요청입니다. 이전 혹은 잘못된 형식의 code입니다.");

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void 존재하지않는_code() {
        //given
        final Long userId = 1L;
        givenValidUserAndIdentity();
        given(codeRepository.findByUserId(any(Long.class))).willReturn(Collections.emptyList());

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                service.execute(identityReqDto, userId));

        String expectedMessage = String.format("사용자의 code가 존재하지 않습니다. 사용자의 ID : %d", userId);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void 전화번호가_인증된_code와_다름() {
        //given
        final IdentityReqDto 전화번호가_다른_Identity
                = new IdentityReqDto("012345", "홍길동", "01099999999", "MALE", LocalDate.EPOCH);
        givenValidUserAndIdentity();
        givenValidCodes();

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                service.execute(전화번호가_다른_Identity, 1L));

        String expectedMessage = String.format("유효하지 않은 요청입니다. code인증에 사용되었던 전화번호와 요청에 사용한 전화번호가 일치하지 않습니다.");

        assertEquals(expectedMessage, exception.getMessage());
        assertThrows(ExpectedException.class, () -> service.execute(전화번호가_다른_Identity, 1L));
    }
}