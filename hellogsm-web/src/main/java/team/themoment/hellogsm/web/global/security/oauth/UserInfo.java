package team.themoment.hellogsm.web.global.security.oauth;

import team.themoment.hellogsm.entity.domain.user.entity.User;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserInfo implements OAuth2User, Serializable {
    private final Long userId;
    private final Role userRole;
    private final String userName;
    private final LocalDateTime lastLoginTime;

    public UserInfo(User user, LocalDateTime lastLoginTime) {
        this.userId = user.getId();
        this.userRole = user.getRole();
        this.userName = user.getProvider() + "_" + user.getProviderId();
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    public Role getUserRole() {
        return userRole;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    @Override
    public String getName() {
        return userName;
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
