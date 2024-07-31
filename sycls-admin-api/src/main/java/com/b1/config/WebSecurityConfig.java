package com.b1.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    // CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization"); // 클라이언트에서 접근할 수 있게 허용할 헤더 추가
        configuration.addExposedHeader("RefreshToken"); // 클라이언트에서 접근할 수 있게 허용할 헤더 추가

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll() // resources 접근 허용 설정
                        .requestMatchers("/favicon.ico").permitAll()

                        // 정적 파일
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/templates/**").permitAll()
                        .requestMatchers("/static/css/**").permitAll()

                        .requestMatchers("/v1/users/signup").permitAll()
                        .requestMatchers("/v1/auth/login").permitAll()
                        //임시 권한 수정
                        .requestMatchers("/v1/users/**").permitAll()
                        .requestMatchers("/v1/auth/**").permitAll()
                        // Payment
                        .requestMatchers("/v1/payment").permitAll()
                        .requestMatchers("/v1/payment/confirm").permitAll()
                        .requestMatchers("/v1/payment/success").permitAll()
                        .requestMatchers("/v1/payment/fail/**").permitAll()

                        // Place
                        .requestMatchers("/v1/places/**").permitAll()

                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
        );

        return http.build();
    }
}