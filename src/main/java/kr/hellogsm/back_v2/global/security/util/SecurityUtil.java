package kr.hellogsm.back_v2.global.security.util;

import kr.hellogsm.back_v2.domain.user.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Optional;

/**
 * OAuth2 인증을 통해 인증된 사용자의 정보를 가져오기 위한 유틸리티 클래스입니다.
 *
 * @author 양시준
 * @since 1.0.0
 * @deprecated 해당 클래스는 더 이상 사용하지 않습니다. <br>
 *     인증된 사용자의 정보를 가져오는 기능은 @AuthenticationPrincipal를 사용하여 대체합니다.
 */
@Slf4j
@Deprecated
public class SecurityUtil {

    // 여기서 던지는 모든 에러는 정상적인(의도한) 상황이라면 발생하지 않을 에러이기 때문에 예외처리 하지 않고, 발생하면 서버에서 알 수 있도록 해야 함

    /**
     인증된 사용자의 ID를 반환합니다.
     @return Long 타입의 사용자 ID
     */
    public static Long getUserId() {
        return (Long) getOAuth2User().getAttributes().get("user_id");
    }

    /**
     인증된 사용자의 Role를 반환합니다.
     @return Role enum의 사용자 역할
     @throws IllegalStateException 사용자 역할이 존재하지 않는 경우 발생합니다.
     */
    public static Role getUserRole() {
        Collection<? extends GrantedAuthority> grantedAuthorities = getOAuth2User().getAuthorities();
        GrantedAuthority role = grantedAuthorities.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User의 Role이 존재하지 않습니다."));
        return Role.valueOf(role.getAuthority());
    }

    /**
     OAuth2User에서 지정한 키를 가진 사용자 속성의 값을 반환합니다.
     @param key 가져올 속성의 키
     @return Optional<Object> 형태의 속성 값. 값이 없는 경우 Optional.empty()를 반환합니다.
     */
    private static Optional<Object> getAttribute(String key) {
        return getOAuth2User().getAttribute(key);
    }

    /**
     인증된 OAuth2 사용자 객체를 반환합니다.
     @return OAuth2User 객체
     */
    private static OAuth2User getOAuth2User() {
        return (OAuth2User) getAuthentication().getPrincipal();
    }

    /**
     인증된 사용자의 인증 객체(Authentication)를 반환합니다.
     @return Authentication 객체
     */
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     생성자를 private로 설정하여 클래스의 인스턴스화를 방지합니다.
     @throws IllegalStateException 생성자 호출 시 예외 발생
     */
    private SecurityUtil() {
        throw new IllegalStateException("Utility class");
    }

}
