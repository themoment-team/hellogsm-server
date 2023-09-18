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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

}
