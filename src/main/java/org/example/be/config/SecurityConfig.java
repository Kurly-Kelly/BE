package org.example.be.config;

import java.util.Arrays;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.example.be.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS 설정
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // CSRF, HTTP 기본 인증, 폼 로그인 비활성화
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        // URL별 접근 권한 설정
        http.authorizeHttpRequests(auth -> auth
            // 회원가입 및 로그인 API는 모두 접근 가능
            .requestMatchers("/users/signup", "/users/login", "/users/logout").permitAll()
            .requestMatchers("/users/email/**", "/users/id/**", "/users/find-id/**", "/users/phone/**", "/users/reset-pw/**").permitAll()

            // 상품 관련 API는 모두 접근 가능
            .requestMatchers("/product/**").permitAll()
            .requestMatchers("/category/**").permitAll()
            .requestMatchers("/admin/saveProduct/**").permitAll()
//            .requestMatchers("/product/save/**").permitAll()
//            .requestMatchers("/product/ChildrenCategory/**").permitAll()
//            .requestMatchers("/product/ParentCategory/**").permitAll()
//            .requestMatchers("/product/getProduct/**").permitAll()
            .requestMatchers("/search/**").permitAll()
            .requestMatchers("/payments/**").permitAll()

            // 찜한 상품 API 접근 가능
                .requestMatchers("/like/**").permitAll()

            // Swagger 문서 접근 가능
            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml",
                "/v3/api-docs/swagger-config"
            ).permitAll()

            // 인증이 필요한 URL 설정
            .requestMatchers("/cart/**", "/users/me").authenticated()

            // 그 외 모든 요청은 인증 필요
            .anyRequest().authenticated()
        );

        // 세션 상태를 STATELESS로 설정 (JWT 사용 시 필수)
        http.sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT 필터 추가 (UsernamePasswordAuthenticationFilter 이전)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 허용할 Origin 설정 (여러 개의 Origin 허용)
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"));

        // 허용할 HTTP 메서드
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 모든 헤더 허용
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));

        // 인증 정보 포함 (Credentials)
        corsConfiguration.setAllowCredentials(true);

        // 노출할 헤더
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Refresh-Token"));

        // 캐싱 시간
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해 CORS 적용

        return source;
    }
}
