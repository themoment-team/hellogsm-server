package team.themoment.hellogsm.web.global.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.global.security.oauth.UserInfo;

import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public Role setRole(HttpServletRequest req, Role role) {
        OAuth2AuthenticationToken oAuth2AuthenticationToken =
                (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        Map<String, Object> newAttributes = new HashMap<>(oAuth2User.getAttributes());
        newAttributes.put("role", role.name());

        Collection<GrantedAuthority> newAuthorities = new ArrayList<>(oAuth2User.getAuthorities());

        newAuthorities.remove(new SimpleGrantedAuthority(Role.ROLE_UNAUTHENTICATED.name()));
        newAuthorities.add(new SimpleGrantedAuthority(role.name()));

        OAuth2User newOAuth2User =
                new UserInfo(newAuthorities, newAttributes, "id");

        OAuth2AuthenticationToken newAuth = new OAuth2AuthenticationToken(
                newOAuth2User, newAuthorities, oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(newAuth);
        HttpSession session = req.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
        return role;
    }
}
