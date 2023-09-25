package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CascadeDeleteIdentityServiceImplTest {

    @InjectMocks
    private CascadeDeleteIdentityServiceImpl cascadeDeleteIdentityService;

    @Mock
    private IdentityRepository identityRepository;

    private final Identity identity = new Identity(1L, "유재석", "01012345678", LocalDate.EPOCH, Gender.MALE, 1L);

    @Test
    public void 성공(){
        //given
        given(identityRepository.findByUserId(any(Long.class))).willReturn(Optional.of(identity));

        //when
        cascadeDeleteIdentityService.execute(identity.getUserId());

        //then
        verify(identityRepository, times(1)).deleteById(any(Long.class));
    }
}
