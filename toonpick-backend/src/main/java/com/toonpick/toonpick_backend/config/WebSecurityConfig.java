package com.toonpick.toonpick_backend.config;

import com.toonpick.toonpick_backend.filter.JwtAuthenticationFilter;
import com.toonpick.toonpick_backend.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 비활성화 (JWT 사용 시)
            .csrf(csrf -> csrf.disable())
            // 세션을 사용하지 않음 (Stateless)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 요청 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()                          // 인증 없이 접근
                .requestMatchers(HttpMethod.GET, "/api/toonpick/**").permitAll()     // GET 방식 웹툰 조회
                .requestMatchers("/file/**").permitAll()                             // 파일 접근
                .requestMatchers("/swagger-ui/**", "/v1/api-docs/**").permitAll()   // Swagger 문서
                .anyRequest().authenticated()                                           // 그 외 모든 요청은 인증 필요
            )
            // 기본 폼 로그인 및 HTTP Basic 비활성화
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            // JWT 인증 필터 등록
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}