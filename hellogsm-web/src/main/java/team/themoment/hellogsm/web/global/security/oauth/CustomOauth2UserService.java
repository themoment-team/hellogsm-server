package team.themoment.hellogsm.web.global.security.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.user.dto.mapper.UserMapper;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomOauth2UserService implements OAuth2UserService {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOauth2UserService;
    private final UserRepository userRepository;

    public CustomOauth2UserService(UserRepository userRepository) {
        this.delegateOauth2UserService = new DefaultOAuth2UserService();
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegateOauth2UserService.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getName();

        User user = getUser(provider, providerId);

        String nameAttribute = "id";
        Long id = user.getId();
        Role role = user.getRole();
        Map<String, Object> attributes = new HashMap<>(Map.of(
                nameAttribute, id,
                "role", role,
                "provider", provider,
                "provider_id", providerId,
                "last_login_time", LocalDateTime.now()
                ));
        Collection<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(role.name()));

        return new UserInfo(authorities, attributes, nameAttribute);
    }

    private User getUser(String provider, String providerId) {
        User savedUser = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElse(null);
        if (savedUser == null) {
            User user = UserMapper.INSTANCE.createUserReqDtoToUser(new CreateUserReqDto(provider, providerId));
            return userRepository.save(user);
        }
        return savedUser;
    }
}
