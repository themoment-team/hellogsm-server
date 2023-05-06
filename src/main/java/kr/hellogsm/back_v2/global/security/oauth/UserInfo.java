package kr.hellogsm.back_v2.global.security.oauth;

import kr.hellogsm.back_v2.domain.user.dto.domain.UserDto;
import kr.hellogsm.back_v2.domain.user.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserInfo implements OAuth2User, Serializable {
    private final UserDto userDto;
    private final LocalDateTime lastLoginTime;

    public UserInfo(UserDto userDto, LocalDateTime lastLoginTime) {
        this.userDto = userDto;
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userDto.role().name()));
    }

    public Role getUserRole() {
        return userDto.role();
    }

    public Long getUserId() {
        return userDto.id();
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    @Override
    public String getName() {
        return userDto.provider() + "_" + userDto.providerId();
    }

    /**
     * @deprecated 해당 메서드는 사용하지 않습니다.
     */
    @Override
    @Deprecated(forRemoval = true)
    public Map<String, Object> getAttributes() {
        throw new IllegalStateException("해당 메서드는 사용하지 않습니다.");
    }
}
