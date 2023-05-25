package team.themoment.hellogsm.web.global.security.oauth;

import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.dto.request.CreateUserReqDto;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        String providerId = getProviderIdByProvider(provider, oAuth2User);

        User user = getUser(provider, providerId);

        return new UserInfo(UserDto.from(user), LocalDateTime.now());
    }

    private User getUser(String provider, String providerId) {
        User savedUser = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElse(null);
        if (savedUser == null) {
            User user = new CreateUserReqDto(provider, providerId).toEntity();
            return userRepository.save(user);
        }
        return savedUser;
    }

    private String getProviderIdByProvider(String provider, OAuth2User oAuth2User) {
        final String nameAttributesKey = getNameAttributesKeyByProvider(provider);
        final Object providerIdObject = oAuth2User.getAttribute(nameAttributesKey);
        if (providerIdObject == null) {
            throw new IllegalArgumentException("provider는 null일 수 없습니다");
        }
        return providerIdObject.toString();
    }


    private static String getNameAttributesKeyByProvider(String provider) {
        final String GOOGLE_PROVIDER = "google";
        final String KAKAO_PROVIDER = "kakao";
        final String GITHUB_PROVIDER = "github";
        return switch (provider) {
            case GOOGLE_PROVIDER -> "sub";
            case KAKAO_PROVIDER, GITHUB_PROVIDER -> "id";
            default -> throw new IllegalArgumentException("유효하지 않은 provider입니다");
        };
    }
}
