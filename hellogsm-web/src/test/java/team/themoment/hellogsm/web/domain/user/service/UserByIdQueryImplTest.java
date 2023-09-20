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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        UserDto result = userByIdQuery.execute(user.getId());

        System.out.println(result);

        assertNotNull(result);
    }
}
