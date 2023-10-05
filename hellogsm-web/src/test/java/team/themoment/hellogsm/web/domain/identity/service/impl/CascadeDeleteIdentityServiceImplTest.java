package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
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

    private final Identity identityDummy = new Identity(1L, "유재석", "01012345678", LocalDate.EPOCH, Gender.MALE, 1L);

    private void verifyIdentityDeletionForUser(Boolean identityExists, Integer wantedNumberOfInvocations){
        //given
        given(identityRepository.findByUserId(any(Long.class)))
                .willReturn(identityExists ? Optional.of(identityDummy) : Optional.empty());

        //when
        cascadeDeleteIdentityService.execute(identityDummy.getUserId());

        //then
        verify(identityRepository, times(wantedNumberOfInvocations)).deleteById(any(Long.class));
    }

    @Test
    public void Identity를_삭제합니다(){
        verifyIdentityDeletionForUser(true, 1);
    }

    @Test
    public void 존재하지_않는_Identity_삭제_요청(){
        verifyIdentityDeletionForUser(false, 0);
    }
}
