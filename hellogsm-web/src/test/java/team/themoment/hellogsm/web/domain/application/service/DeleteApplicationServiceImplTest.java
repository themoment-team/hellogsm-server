package team.themoment.hellogsm.web.domain.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.impl.DeleteApplicationServiceImpl;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DeleteApplicationServiceImplTest {

    @InjectMocks
    private DeleteApplicationServiceImpl deleteApplicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    private final Application application = mock(Application.class);
    private final AdmissionStatus admissionStatus = mock(AdmissionStatus.class);

    @Test
    public void 성공() {
        // given
        given(applicationRepository.findByUserId(any(Long.class))).willReturn(Optional.ofNullable(application));
        given(application.getAdmissionStatus()).willReturn(admissionStatus);
        given(application.getAdmissionStatus().isFinalSubmitted()).willReturn(false);

        // when & then
        assertDoesNotThrow(() -> deleteApplicationService.execute(1L));
    }

    @Test
    public void 존재하지_않는_Application() {
        // given
        given(applicationRepository.findByUserId(any(Long.class))).willReturn(Optional.empty());

        //when & then
        assertExpectedExceptionWithMessage("원서가 존재하지 않습니다");
    }

    @Test
    public void 최종제출_완료된_Application() {
        // given
        given(applicationRepository.findByUserId(any(Long.class))).willReturn(Optional.ofNullable(application));
        given(application.getAdmissionStatus()).willReturn(admissionStatus);
        given(application.getAdmissionStatus().isFinalSubmitted()).willReturn(true);

        //when & then
        assertExpectedExceptionWithMessage("최종제출이 완료된 원서입니다");
    }

    private void assertExpectedExceptionWithMessage(String expectedMessage) {
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                deleteApplicationService.execute(1L));

        assertEquals(expectedMessage, exception.getMessage());
    }

}
