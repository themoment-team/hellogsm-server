package team.themoment.hellogsm.web.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.impl.UserByIdQueryImpl;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserByIdQueryImplTest {

    @InjectMocks
    private UserByIdQueryImpl userByIdQuery;

    @Mock
    private UserRepository userRepository;

    private final User user = new User(1L, "google", "12345678", Role.ROLE_UNAUTHENTICATED);

    @Test
    public void 성공(){
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        //when
        UserDto result = userByIdQuery.execute(user.getId());

        //then
        UserDto expectedResult = new UserDto(user.getId(), user.getProvider(), user.getProviderId(), user.getRole());

        assertEquals(result, expectedResult);
    }

    @Test
    public void 존재하지_않는_User(){
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when & then
        ExpectedException exception = assertThrows(ExpectedException.class,
                () -> userByIdQuery.execute(user.getId()));

        String expectedMessage = "존재하지 않는 User 입니다";

        assertEquals(exception.getMessage(), expectedMessage);
    }
}
