package team.themoment.hellogsm.web.domain.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.impl.CreateUserServiceImpl;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CreateUserServiceImplTest {

    @InjectMocks
    private CreateUserServiceImpl createUserService;

    @Mock
    private UserRepository userRepository;

    private final User user = new User(1L,"google", "12345678", Role.ROLE_UNAUTHENTICATED);

    private final CreateUserReqDto reqDto = new CreateUserReqDto("google", "12345678");

    private void givenExistingUser(Boolean value){
        given(userRepository.existsByProviderAndProviderId(any(String.class), any(String.class))).willReturn(value);
    }

    @Test
    public void 성공(){
        //given
        givenExistingUser(false);
        given(userRepository.save(any(User.class))).willReturn(user);

        //when
        UserDto result = createUserService.execute(reqDto);

        //then
        Assertions.assertNotNull(result);
    }

    @Test
    public void 이미_존재하는_User(){
        //given
        givenExistingUser(true);

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> createUserService.execute(reqDto));

        String expectedMessage = "이미 존재하는 User 입니다";

        assertEquals(exception.getMessage(), expectedMessage);
    }
}
