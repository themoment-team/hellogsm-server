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

    private final List<AuthenticationCode> codesDummy = List.of(
            new AuthenticationCode("123456", 1L, true, "01012345678", LocalDateTime.MIN),
            new AuthenticationCode("654321", 1L, true, "01012345678", LocalDateTime.MAX));

    private final Identity identityDummy = new Identity(1L, "이정우", "01012345678", LocalDate.EPOCH, Gender.MALE, 1L);

    private final IdentityReqDto reqDtoDummy = new IdentityReqDto("654321", "이정우", "01012345678", "MALE", LocalDate.EPOCH);

    private void givenExistingUser(Boolean value){
        given(userRepository.existsById(any(Long.class))).willReturn(value);
    }

    private void givenValidIdentity(Boolean identityExists){
        given(identityRepository.findByUserId(any(Long.class)))
                .willReturn(identityExists ? Optional.of(identityDummy) : Optional.empty());
    }

    private void givenValidCode(List<AuthenticationCode> codes){
        given(codeRepository.findByUserId(any(Long.class))).willReturn(codes);
    }

    private void assertThrowsExpectedExceptionWithMessageAndReqDtoAndUserId(String expectedMessage, IdentityReqDto reqDto, Long userId){
        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> modifyIdentityService.execute(reqDto, userId));;

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    public void Identity를_찾고_Identity를_IdentityDto로_변환하여_반환합니다(){
        //given
        givenExistingUser(true);
        givenValidIdentity(true);
        givenValidCode(codesDummy);
        given(identityRepository.save(any(Identity.class))).willReturn(identityDummy);
        doNothing().when(codeRepository).deleteById(any(String.class));

        //when
        IdentityDto result = modifyIdentityService.execute(reqDtoDummy, identityDummy.getUserId());

        //then
        IdentityDto expectedResult = new IdentityDto(
                identityDummy.getId(), identityDummy.getName(), identityDummy.getPhoneNumber(), identityDummy.getBirth(), identityDummy.getGender(), identityDummy.getUserId());

        assertEquals(result, expectedResult);
    }

    @Test
    public void 존재하지_않는_User일때_적절한_ExpectedException을_던진다(){
        //given
        givenExistingUser(false);

        //when & then
        assertThrowsExpectedExceptionWithMessageAndReqDtoAndUserId("존재하지 않는 User 입니다", reqDtoDummy, identityDummy.getUserId());
    }

    @Test
    public void 존재하지_않는_Identity일때_적절한_ExpectedException을_던진다(){
        //given
        givenExistingUser(true);
        givenValidIdentity(false);

        //when & then
        assertThrowsExpectedExceptionWithMessageAndReqDtoAndUserId("존재하지 않는 Identity 입니다", reqDtoDummy, identityDummy.getUserId());
    }

    @Test
    public void 인증받지_않은_Code일때_적절한_ExpectedException을_던진다(){
        //given
        final List<AuthenticationCode> unauthenticatedCodes = List.of(
                new AuthenticationCode("123456", 1L, false, "01012345678", LocalDateTime.MIN),
                new AuthenticationCode("654321", 1L, false, "01012345678", LocalDateTime.MAX));

        givenExistingUser(true);
        givenValidIdentity(true);
        givenValidCode(unauthenticatedCodes);

        //when & then
        assertThrowsExpectedExceptionWithMessageAndReqDtoAndUserId("유효하지 않은 요청입니다. 인증받지 않은 code입니다.", reqDtoDummy, identityDummy.getUserId());
    }

    @Test
    public void 유효하지_않거나_최신이_아닌_Code일때_적절한_ExpectedException을_던진다(){
        //given
        final IdentityReqDto 인증코드가_최신이_아닌_IdentityReqDto = new IdentityReqDto(
                codesDummy.get(0).getCode(), reqDtoDummy.name(), reqDtoDummy.phoneNumber(), reqDtoDummy.gender(), reqDtoDummy.birth());

        givenExistingUser(true);
        givenValidIdentity(true);
        givenValidCode(codesDummy);

        assertThrowsExpectedExceptionWithMessageAndReqDtoAndUserId(
                "유효하지 않은 요청입니다. 이전 혹은 잘못된 형식의 code입니다.", 인증코드가_최신이_아닌_IdentityReqDto, identityDummy.getUserId());
    }

    @Test
    public void 전화번호가_인증된_code와_일치하지_않을때_적절한_ExpectedException을_던진다(){
        //given
        final IdentityReqDto 전화번호가_일치하지_않는_IdentityReqDto = new IdentityReqDto(
                codesDummy.get(1).getCode(), reqDtoDummy.name(), "01099999999", reqDtoDummy.gender(), reqDtoDummy.birth());

        givenExistingUser(true);
        givenValidIdentity(true);
        givenValidCode(codesDummy);

        //when
        assertThrowsExpectedExceptionWithMessageAndReqDtoAndUserId(
                "유효하지 않은 요청입니다. code인증에 사용되었던 전화번호와 요청에 사용한 전화번호가 일치하지 않습니다.", 전화번호가_일치하지_않는_IdentityReqDto, identityDummy.getUserId());
    }
}
