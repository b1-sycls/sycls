package com.b1.security;

import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.user.UserHelper;
import com.b1.user.dto.UserLoginRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final UserHelper userHelper;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserHelper userHelper,
            ObjectMapper objectMapper) {
        this.jwtProvider = jwtProvider;
        this.userHelper = userHelper;
        this.objectMapper = objectMapper;
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
        RestApiResponseDto<String> responseDto = RestApiResponseDto.of("로그인 성공");

        String body = objectMapper.writeValueAsString(responseDto);
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        log.info("로그인 실패");

        RestApiResponseDto<String> responseDto = RestApiResponseDto.of("로그인 실패");

        String body = objectMapper.writeValueAsString(responseDto);
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);
    }
}
