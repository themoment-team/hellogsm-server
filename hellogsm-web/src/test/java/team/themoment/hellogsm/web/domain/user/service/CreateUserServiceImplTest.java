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

    @Test
    public void 성공(){
        //given
        given(userRepository.existsByProviderAndProviderId(any(String.class), any(String.class))).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(user);

        //when
        UserDto result = createUserService.execute(reqDto);

        //then
        Assertions.assertNotNull(result);
    }
}
