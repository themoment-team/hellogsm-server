package team.themoment.hellogsm.web.global.security;

import team.themoment.hellogsm.entity.domain.user.enums.Role;
import team.themoment.hellogsm.web.global.data.profile.ServerProfile;
import team.themoment.hellogsm.web.global.security.auth.AuthEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * SecurityConfig <br>
 * Spring Security를 위한 설정 클래스입니다. <br>
 * inner class로 프로필별 설정을 관리합니다. - 아직 예정임
 *
 * @author 양시준
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthEnvironment authEnv;
    private static final String logoutUri = "/auth/v1/logout";
    private static final String oauth2LoginEndpointBaseUri = "/auth/v1/oauth2/authorization";
    private static final String oauth2LoginProcessingUri = "/auth/v1/oauth2/code/*";


    // TODO
    //  1. 본인인증 필터 추가

    @Configuration
    @EnableWebSecurity
    @Profile(ServerProfile.LOCAL)
    public class LocalSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .formLogin().disable()
                    .httpBasic().disable()
                    .headers().frameOptions().sameOrigin().and()
                    .cors().disable()
                    .csrf().disable();
            logout(http);
            oauth2Login(http);
            http.authorizeHttpRequests(
                    httpRequests -> httpRequests
                            .requestMatchers(toH2Console()).permitAll()
            );
            authorizeHttpRequests(http);
            return http.build();
        }
    }

    @Configuration
    @EnableWebSecurity
    @Profile(ServerProfile.PROD)
    public class ProdSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .formLogin().disable()
                    .httpBasic().disable()
                    .cors().configurationSource(corsConfigurationSource()).and()
                    .csrf().disable();
            logout(http);
            oauth2Login(http);
            authorizeHttpRequests(http);
            return http.build();
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(authEnv.allowedOrigins());
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void oauth2Login(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2Login ->
                oauth2Login
                        .authorizationEndpoint().baseUri(oauth2LoginEndpointBaseUri).and()
                        .loginProcessingUrl(oauth2LoginProcessingUri)
                        .defaultSuccessUrl(authEnv.redirectBaseUri())

        );
    }

    private void logout(HttpSecurity http) throws Exception {
        http.logout(logout -> logout
                .logoutUrl(logoutUri)
                .logoutSuccessUrl(authEnv.redirectBaseUri())
        );
    }

    private void authorizeHttpRequests(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(httpRequests -> httpRequests
                .requestMatchers("/csrf").permitAll()
                .requestMatchers("/auth/v1/**").permitAll()
                .requestMatchers("/user/v1/**").hasAnyRole(
                        Role.ROLE_UNAUTHENTICATED.getRole(),
                        Role.ROLE_USER.getRole(),
                        Role.ROLE_ADMIN.getRole()
                )
                .requestMatchers(HttpMethod.POST, "/identity/v1/**").hasAnyRole(
                        Role.ROLE_UNAUTHENTICATED.getRole(),
                        Role.ROLE_USER.getRole(),
                        Role.ROLE_ADMIN.getRole()
                )
                .requestMatchers("/identity/v1/**").hasAnyRole(
                        Role.ROLE_USER.getRole(),
                        Role.ROLE_ADMIN.getRole()
                )
                .requestMatchers("/application/v1/**").hasAnyRole(
                        Role.ROLE_USER.getRole(),
                        Role.ROLE_ADMIN.getRole()
                )
                .anyRequest().permitAll()
        );
    }

}
