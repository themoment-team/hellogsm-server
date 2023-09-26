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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private final List<AuthenticationCode> codes = List.of(
            new AuthenticationCode("123456", 1L, false, "010-1234-5678", LocalDateTime.now()),
            new AuthenticationCode("654321", 2L, true, "010-8765-4321", LocalDateTime.now()));

    private final Identity identity = new Identity(1L, "이정우", "01012345678", LocalDate.EPOCH, Gender.MALE, 1L);

    private final IdentityReqDto reqDto = new IdentityReqDto("654321", "이정우", "010-8765-4321", "MALE", LocalDate.EPOCH);

    @Test
    public void 성공(){
        //given
        given(userRepository.existsById(any(Long.class))).willReturn(true);
        given(identityRepository.findByUserId(any(Long.class))).willReturn(Optional.of(identity));
        given(codeRepository.findByUserId(any(Long.class))).willReturn(codes);
        given(identityRepository.save(any(Identity.class))).willReturn(identity);
        doNothing().when(codeRepository).deleteById(any(String.class));

        //when
        IdentityDto result = modifyIdentityService.execute(reqDto, identity.getUserId());

        //then
        IdentityDto expectedResult = new IdentityDto(identity.getId(), identity.getName(), identity.getPhoneNumber(), identity.getBirth(), identity.getGender(), identity.getUserId());
        assertEquals(result, expectedResult);
    }
}
