package team.themoment.hellogsm.web.domain.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class IdentityQueryImplTest {

    @InjectMocks
    IdentityQueryImpl identityQuery;

    @Mock
    IdentityRepository identityRepository;

    private final Identity identity = new Identity(1L, "아이유", "01012345678", LocalDate.EPOCH, Gender.FEMALE, 1L);


    @Test
    public void 성공(){
        //given
        given(identityRepository.findByUserId(any(Long.class))).willReturn(Optional.of(identity));

        //when
        IdentityDto result = identityQuery.execute(identity.getUserId());

        //then
        IdentityDto expectedResult = new IdentityDto(identity.getId(), identity.getName(), identity.getPhoneNumber(), identity.getBirth(), identity.getGender(), identity.getUserId());

        assertEquals(result, expectedResult);
    }
}
