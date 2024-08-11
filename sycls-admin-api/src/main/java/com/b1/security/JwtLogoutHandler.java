package com.b1.security;

import static com.b1.constant.TokenConstants.AUTHORIZATION_HEADER;
import static com.b1.constant.TokenConstants.REFRESHTOKEN_HEADER;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "Jwt Logout Handler")
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        log.info("로그아웃 시도");
        String requestRefreshToken = request.getHeader(REFRESHTOKEN_HEADER);
        String requestAccessToken = request.getHeader(AUTHORIZATION_HEADER);

        log.info("남은 유효시간 {}", jwtProvider.getRemainingValidityMillis(
                jwtProvider.substringToken(requestRefreshToken)));
        long blacklistTokenTtl = jwtProvider.getRemainingValidityMillis(
                jwtProvider.substringToken(requestRefreshToken));

        jwtProvider.addBlacklistToken(requestAccessToken, blacklistTokenTtl);
        jwtProvider.addBlacklistToken(requestRefreshToken, blacklistTokenTtl);
        jwtProvider.deleteToken(requestRefreshToken);

        SecurityContextHolder.clearContext();
    }
}
