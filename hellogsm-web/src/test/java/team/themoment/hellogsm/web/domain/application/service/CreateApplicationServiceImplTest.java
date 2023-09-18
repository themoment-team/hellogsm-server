package team.themoment.hellogsm.web.domain.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.impl.CreateApplicationServiceImpl;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CreateApplicationServiceImplTest {

    @InjectMocks
    private CreateApplicationServiceImpl createApplicationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IdentityRepository identityRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    private final ApplicationReqDto applicationReqDto = new ApplicationReqDto(
            "https://naver.com",
            "지원자 집주소",
            "지원자 상세주소",
            "GED",
            "01012341234",
            "홍길동",
            "부",
            "01044447777",
            "김철수",
            "01077774444",
            "SW",
            "AI",
            "IOT",
            "{\"curriculumScoreSubtotal\":100,\"nonCurriculumScoreSubtotal\":100,\"rankPercentage\":0,\"scoreTotal\":261}",
            "지원자 학교 이름",
            "지원자 학교 위치",
           "GENERAL"
    );

    private final Identity identity = mock(Identity.class);

    private void verifyExistence() {
        given(applicationRepository.existsByUserId(any(Long.class))).willReturn(false);
        given(userRepository.existsById(any(Long.class))).willReturn(true);
    }

    @Test
    public void 성공() {
        // given
        verifyExistence();
        given(identityRepository.findByUserId(any(Long.class))).willReturn(Optional.ofNullable(identity));

        // when & then
        assertDoesNotThrow(() -> createApplicationService.execute(applicationReqDto, 1L));
    }

    @Test
    public void 존재하지_않는_User() {
        // given
        given(userRepository.existsById(any(Long.class))).willReturn(false);

        // when & then
        assertExpectedExceptionWithMessage("존재하지 않는 유저입니다");
    }

    @Test
    public void 이미_존재하는_Application() {
        // given
        given(userRepository.existsById(any(Long.class))).willReturn(true);
        given(applicationRepository.existsByUserId(any(Long.class))).willReturn(true);

        // when & then
        assertExpectedExceptionWithMessage("원서가 이미 존재합니다");
    }

    @Test
    public void 존재하지_않는_Identity() {
        //given
        verifyExistence();
        given(identityRepository.findByUserId(any(Long.class))).willReturn(Optional.empty());

        // when & thenㅍ
        assertExpectedExceptionWithMessage("Identity가 존재하지 않습니다");
    }

    private void assertExpectedExceptionWithMessage(String expectedMessage) {
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                createApplicationService.execute(applicationReqDto, 1L));
      
        assertEquals(expectedMessage, exception.getMessage());
    }
}
