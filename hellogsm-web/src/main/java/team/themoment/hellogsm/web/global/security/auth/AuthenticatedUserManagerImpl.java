package team.themoment.hellogsm.web.global.security.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.time.LocalDateTime;

@Component
public class AuthenticatedUserManagerImpl implements AuthenticatedUserManager {
    @Override
    public Long getId() {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(oAuth2User.getName());
    }

    @Override
    public Role getRole() {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return oAuth2User.getAttribute("role");
    }

    @Override
    public LocalDateTime getLastLoginTime() {
        OAuth2User oAuth2User =
                (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return oAuth2User.getAttribute("last_login_time");
    }
}
