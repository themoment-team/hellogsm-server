package kr.hellogsm.back_v2.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig <br>
 *
 * Spring Security를 위한 설정 클래스입니다. <br>
 *
 * inner class로 프로필별 설정을 관리합니다. - 아직 예정임
 *
 * @author 양시준
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // TODO
    //  1. 인증 관련 필터 추가
    //  2. Logout~Handler 구현 - REST
    //  3. 필터 예외처리 추가
    //  4. inner class로 프로필 별 셜졍 관리 - hirecruit 참고

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
                        .anyRequest().permitAll()
                )
                .oauth2Login()
                .and()
                .formLogin().disable()
                .logout();  // TODO Handler 추가
        return http.build();
    }

    private HttpSecurity csrf(HttpSecurity http) throws Exception {
        return http.csrf().disable();
    }

}