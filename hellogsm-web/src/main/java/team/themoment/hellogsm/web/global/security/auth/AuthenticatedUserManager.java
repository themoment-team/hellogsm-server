package team.themoment.hellogsm.web.global.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.time.LocalDateTime;

public interface AuthenticatedUserManager {
    Long getId();
    Role getRole();
    LocalDateTime getLastLoginTime();
    Role setRole(HttpServletRequest req, Role role);
}
