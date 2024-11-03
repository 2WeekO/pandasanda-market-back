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
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.website.sharestore.jwt.JwtAccessDeniedHandler;
import com.website.sharestore.jwt.JwtAuthenticationEntryPoint;
import com.website.sharestore.jwt.JwtFilter;
import com.website.sharestore.jwt.TokenProvider;








@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig {

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
        .cors(cors -> corsConfigurationSource()) //CORS는 CorsConfigrationSource로 대체
        .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable()) // httpBasic을 비활성화
        .csrf(csrfCustomizer -> csrfCustomizer.disable()) // CSRF 비활성화
        .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 관리 정책을 STATELESS로 설정

        .exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
            .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패 시 처리
            .accessDeniedHandler(jwtAccessDeniedHandler)) // 권한 부족 시 처리

        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
            .requestMatchers(
    "/auth/**",
                "/error",
                "/api/product/all",
                "/images/**",
                "/api/product/**"
                
                

            
            ).permitAll() // "/auth/**" 경로는 인증 없이 접근 가능
            .anyRequest().authenticated()) // 나머지 요청은 인증 필요

        .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ExceptionHandlerCustom -> ExceptionHandlerCustom
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler))
        
        ; // JWT 보안 설정 적용

    return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:3000");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
