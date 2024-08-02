package com.b1.security;

import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.user.UserHelper;
import com.b1.user.dto.UserLoginRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserRole;
import com.b1.user.entity.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final UserHelper userHelper;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserHelper userHelper) {
        this.jwtProvider = jwtProvider;
        this.userHelper = userHelper;
        setFilterProcessesUrl("/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            UserLoginRequestDto loginRequestDto = new ObjectMapper().readValue(
                    request.getInputStream(), UserLoginRequestDto.class);

            User user = userHelper.findByEmail(loginRequestDto.email());
            if (!UserRole.isAdmin(user.getRole())) {
                log.error("관리자 권한이 아닙니다. | request : {}", user.getId());
                throw new UserNotFoundException(UserErrorCode.USER_NOT_ADMIN);
            }
            if (UserStatus.isDeleted(user.getStatus())) {
                log.error("이미 삭제된 유저 | request : {}", user.getId());
                throw new UserAlreadyDeletedException(UserErrorCode.USER_ALREADY_DELETED);
            }

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.email(),
                            loginRequestDto.password(),
                            null

                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getEmail();

        // 토큰 생성
        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken(email);

        // 헤더에 토큰 저장
        response.setHeader("Authorization", accessToken);
        response.setHeader("RefreshToken", refreshToken);
        jwtProvider.addToken(accessToken, refreshToken,
                jwtProvider.extractExpirationMillis(jwtProvider.substringToken(refreshToken)));

        // 로그인 성공 메세지 반환
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString("로그인 성공!"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
