package team.themoment.hellogsm.web.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.domain.user.service.impl.ExistUserQueryImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ExistUserQueryImplTest {

    @InjectMocks
    ExistUserQueryImpl existUserQuery;

    @Mock
    UserRepository userRepository;

    private final User user = new User(1L, "google", "12345678", Role.ROLE_UNAUTHENTICATED);

    private void givenExistingUser(Boolean value){
        given(userRepository.existsByProviderAndProviderId(any(String.class), any(String.class))).willReturn(value);
    }

    private Boolean executeResult(){
        return existUserQuery.execute(user.getProvider(), user.getProviderId());
    }

    @Test
    public void 존재하지_않는_User(){
        //given
        givenExistingUser(false);

        //when & then
        assertFalse(executeResult());
    }

    @Test
    public void 존재하는_User(){
        //given
        givenExistingUser(true);

        //when & then
        assertTrue(executeResult());
    }
}
