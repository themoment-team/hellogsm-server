package team.themoment.hellogsm.web.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.io.IOException;

public class CustomUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String defaultTargetUrl;

    public CustomUrlAuthenticationSuccessHandler(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        boolean isUnAuthentication = authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ROLE_UNAUTHENTICATED.name().equals(authority.getAuthority()));

        String redirectUrlWithParameter = UriComponentsBuilder
                .fromUriString(defaultTargetUrl)
                .queryParam("verification", !isUnAuthentication)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrlWithParameter);

        clearAuthenticationAttributes(request);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
