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
    private IdentityQueryImpl identityQuery;

    @Mock
    private IdentityRepository identityRepository;

    private final Identity identityDummy = new Identity(1L, "아이유", "01012345678", LocalDate.EPOCH, Gender.FEMALE, 1L);

    private void givenValidIdentity(Boolean identityExists){
        given(identityRepository.findByUserId(any(Long.class)))
                .willReturn(identityExists ? Optional.of(identityDummy) : Optional.empty());
    }

    @Test
    public void Identity를_찾고_IdentityDto로_변환하여_반환합니다(){
        //given
        givenValidIdentity(true);

        //when
        IdentityDto result = identityQuery.execute(identityDummy.getUserId());

        //then
        IdentityDto expectedResult = new IdentityDto(
                identityDummy.getId(), identityDummy.getName(), identityDummy.getPhoneNumber(), identityDummy.getBirth(), identityDummy.getGender(), identityDummy.getUserId());

        assertEquals(result, expectedResult);
    }

    @Test
    public void 존재하지_않는_Identity일때_적절한_ExpectedException을_던진다(){
        //given
        givenValidIdentity(false);

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> identityQuery.execute(identityDummy.getUserId()));

        String expectedMessage = "존재하지 않는 Identity 입니다";

        assertEquals(exception.getMessage(), expectedMessage);
    }
}
