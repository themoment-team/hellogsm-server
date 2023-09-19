package team.themoment.hellogsm.web.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.entity.domain.user.event.DeleteUserEvent;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.impl.DeleteUserServiceImpl;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteUserServiceImplTest {

    @InjectMocks
    private DeleteUserServiceImpl deleteUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private final User user = new User(1L,"google", "12345678", Role.ROLE_UNAUTHENTICATED);

    @Test
    public void 성공(){
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        //when
        deleteUserService.execute(user.getId());

        //then
        verify(applicationEventPublisher).publishEvent(any(DeleteUserEvent.class));
    }

    @Test
    public void 존재하지_않는_User(){
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> deleteUserService.execute(user.getId()));

        String expectedMessage = "존재하지 않는 사용자입니다.";

        assertEquals(exception.getMessage(), expectedMessage);
    }
}
