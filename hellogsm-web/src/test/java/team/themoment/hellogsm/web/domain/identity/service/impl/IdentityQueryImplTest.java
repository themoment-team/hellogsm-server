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
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class IdentityQueryImplTest {

    @InjectMocks
    IdentityQueryImpl identityQuery;

    @Mock
    IdentityRepository identityRepository;

    private final Identity identity = new Identity(1L, "아이유", "01012345678", LocalDate.EPOCH, Gender.FEMALE, 1L);

    private void givenValidIdentity(Boolean identityExists){
        given(identityRepository.findByUserId(any(Long.class)))
                .willReturn(identityExists ? Optional.of(identity) : Optional.empty());
    }

    @Test
    public void 성공(){
        //given
        givenValidIdentity(true);

        //when
        IdentityDto result = identityQuery.execute(identity.getUserId());

        //then
        IdentityDto expectedResult = new IdentityDto(identity.getId(), identity.getName(), identity.getPhoneNumber(), identity.getBirth(), identity.getGender(), identity.getUserId());

        assertEquals(result, expectedResult);
    }

    @Test
    public void 존재하지_않는_Identity(){
        //given
        givenValidIdentity(false);

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> identityQuery.execute(identity.getUserId()));

        String expectedMessage = "존재하지 않는 Identity 입니다";

        assertEquals(exception.getMessage(), expectedMessage);
    }
}
