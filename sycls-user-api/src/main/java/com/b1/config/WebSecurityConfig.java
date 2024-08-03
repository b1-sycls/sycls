package com.b1.config;

import com.b1.security.JwtAuthenticationFilter;
import com.b1.security.JwtAuthorizationFilter;
import com.b1.security.JwtLogoutHandler;
import com.b1.security.JwtLogoutSuccessHandler;
import com.b1.security.JwtProvider;
import com.b1.security.UserDetailsServiceImpl;
import com.b1.user.UserHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserHelper userHelper;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    private final JwtLogoutHandler jwtLogoutHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userHelper);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, userDetailsService);
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
                                .permitAll()
                                // 정적 파일
//                        .requestMatchers("/favicon.ico").permitAll()
//                        .requestMatchers("/static/**").permitAll()
//                        .requestMatchers("/templates/**").permitAll()
//                        .requestMatchers("/static/css/**").permitAll()
                                .requestMatchers("/v1/users/signup").permitAll()
                                .requestMatchers("/v1/auth/login").permitAll()
                                .requestMatchers("/v1/auth/token").permitAll()
                                .requestMatchers("/v1/auth/send-verification-code").permitAll()
                                .requestMatchers("/v1/auth/check-verification-code").permitAll()
                                .requestMatchers("/v1/auth/forget-email").permitAll()
                                .requestMatchers("/v1/auth/forget-password").permitAll()

                                .requestMatchers("/v1/email/check").permitAll()
                                .requestMatchers("/v1/nickname/check").permitAll()
                                .requestMatchers("/error").permitAll()

                                // Content
                                .requestMatchers(HttpMethod.GET, "/v1/contents/**").permitAll()

                                // Category
                                .requestMatchers(HttpMethod.GET, "/v1/categories").permitAll()

                                // Cast
                                .requestMatchers(HttpMethod.GET, "/v1/casts").permitAll()

                                // Round
                                .requestMatchers(HttpMethod.GET, "/v1/rounds/**").permitAll()

                                // ETC .. 필요한거 추가해서 사용하세요
                                .anyRequest().authenticated()
        );

        http.logout(logout ->
                logout.logoutUrl("/v1/auth/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessHandler(jwtLogoutSuccessHandler)
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}