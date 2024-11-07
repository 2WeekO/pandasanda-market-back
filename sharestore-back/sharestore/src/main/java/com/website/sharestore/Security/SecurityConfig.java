package com.website.sharestore.Security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.website.sharestore.jwt.JwtAccessDeniedHandler;
import com.website.sharestore.jwt.JwtAuthenticationEntryPoint;
import com.website.sharestore.jwt.JwtFilter;
import com.website.sharestore.jwt.TokenProvider;




@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider,
                        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                        JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> corsConfigurationSource()) // CORS 설정
            .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable()) // httpBasic 비활성화
            .csrf(csrfCustomizer -> csrfCustomizer.disable()) // CSRF 비활성화
            .sessionManagement(sessionManagementCustomizer ->
                sessionManagementCustomizer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 관리 정책을 STATELESS로 설정
            .exceptionHandling(exceptionHandlingCustomizer ->
                exceptionHandlingCustomizer
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패 시 처리
                    .accessDeniedHandler(jwtAccessDeniedHandler)) // 권한 부족 시 처리
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                        "/auth/**",
                        "/error",
                        "/api/product/all",
                        "/images/**",
                        "/api/product/**"
                    ).permitAll() // 위 경로들은 인증 없이 접근 가능
                    .anyRequest().authenticated()) // 나머지 요청은 인증 필요
            .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT 보안 설정 적용

        return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://172.30.1.66:3000"); // 로컬 개발 환경
        config.addAllowedOrigin("https://172.30.1.66:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 CORS 설정 적용

        return source;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
