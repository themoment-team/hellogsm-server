package team.themoment.hellogsm.web.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TimeBasedFilter extends OncePerRequestFilter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, TimeRange> urlTimeConstraints = new HashMap<>();

    private static class TimeRange {
        private final LocalDateTime startTime;
        private final LocalDateTime endTime;

        public TimeRange(LocalDateTime startTime, LocalDateTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public boolean isWithinRange(LocalDateTime currentTime) {
            return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
        }
    }

    public TimeBasedFilter addTimeConstraint(HttpMethod httpMethod, String url, LocalDateTime startTime, LocalDateTime endTime) {
        urlTimeConstraints.put(url + ":" + httpMethod, new TimeRange(startTime, endTime));
        return this;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isOAuthAuth()) {
            filterChain.doFilter(request, response);
            return;
        }
        if (isSkipAble()) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestUrl = request.getRequestURI();
        HttpMethod requestMethod;
        try {
            requestMethod = HttpMethod.valueOf(request.getMethod());
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 HTTP 메소드 : " + request.getMethod());
            return;
        }
        LocalDateTime currentTime = LocalDateTime.now();

        String constraintKey = requestUrl + ":" + requestMethod;

        if (urlTimeConstraints.containsKey(constraintKey)) {
            TimeRange timeRange = urlTimeConstraints.get(constraintKey);
            if (timeRange.isWithinRange(currentTime)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                String message = String.format("%s 요청이 거부되었습니다. " +
                        "현재 시간: %s, 해당 요청은 %s ~ %s 이내에만 처리 가능합니다.", constraintKey, currentTime, timeRange.startTime, timeRange.endTime);
                log.warn(message);
                sendErrorResponse(response, message);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isSkipAble() {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authRole = Objects.requireNonNull(oAuth2User.getAttribute("role")).toString();
        boolean isTester = Role.ROLE_TESTER.name().equals(authRole);
        boolean isAdmin = Role.ROLE_ADMIN.name().equals(authRole);
        return isTester || isAdmin;
    }

    private boolean isOAuthAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof OAuth2AuthenticationToken;
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
