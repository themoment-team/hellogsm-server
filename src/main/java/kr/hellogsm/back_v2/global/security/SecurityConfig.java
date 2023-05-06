package kr.hellogsm.back_v2.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig <br>
 * Spring Security를 위한 설정 클래스입니다. <br>
 * inner class로 프로필별 설정을 관리합니다. - 아직 예정임
 *
 * @author 양시준
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${oauth2.redirect-base-uri}")
    String redirectBaseUri;
    private final String logoutUri = "/auth/v1/auth/logout";
    // /auth/v1/auth/oauth2/authorization/google
    private final String oauth2LoginEndpointBaseUri = "/auth/v1/auth/oauth2/authorization";

    // TODO
    //  1. OAuth 인증 관련 필터 추가 - ok
    //  2. Logout~Handler 구현 - REST - ok
    //  3. 필터 예외처리 추가 - ok
    //  4. inner class로 프로필 별 셜졍 관리 - hirecruit 참고
    //  5. 본인인증 필터 추가

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        csrf(http);
        http
                .headers(header -> header
                        .frameOptions()
                        .sameOrigin()
                )
                .authorizeHttpRequests(httpRequests -> httpRequests
                        .requestMatchers("/h2").permitAll()
                        .requestMatchers("/auth/v1/**").permitAll()
                        .requestMatchers("/user/v1/**").authenticated()
                        .requestMatchers("/identity/v1/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .authorizationEndpoint().baseUri(oauth2LoginEndpointBaseUri).and()
                                .defaultSuccessUrl(redirectBaseUri)

                )
                .logout(logout -> logout
                        .logoutUrl(logoutUri)
                        .logoutSuccessUrl(redirectBaseUri))
                .formLogin().disable()
                .httpBasic().disable();
        return http.build();
    }

    private HttpSecurity csrf(HttpSecurity http) throws Exception {
        return http.csrf().disable();
    }

}