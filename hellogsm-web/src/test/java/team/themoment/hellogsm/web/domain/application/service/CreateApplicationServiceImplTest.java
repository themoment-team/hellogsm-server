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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

    private final Identity identity = new Identity(
            1L,
            "차무식",
            "01012345678",
            LocalDate.EPOCH,
            Gender.MALE,
            1L
    );

    private void givenIdentity() {
        given(identityRepository.findByUserId(any(Long.class))).willReturn(Optional.of(identity));
    }

    private void verifyExistence() {
        given(applicationRepository.existsByUserId(any(Long.class))).willReturn(false);
        given(userRepository.existsById(any(Long.class))).willReturn(true);
    }

    @Test
    public void 성공() {
        // given
        givenIdentity();
        verifyExistence();

        // when & then
        assertDoesNotThrow(() -> createApplicationService.execute(applicationReqDto, 1L));
    }
}
