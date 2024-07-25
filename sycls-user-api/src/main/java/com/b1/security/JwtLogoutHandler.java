package com.b1.security;

import com.b1.auth.entity.Token;
import com.b1.exception.customexception.TokenException;
import com.b1.exception.errorcode.TokenErrorCode;
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
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("로그아웃 시도");
        String accessToken = request.getHeader("Authorization");
        String refreshToken = jwtProvider.getToken(accessToken).getRefresh();

        long blacklistTokenTtl = jwtProvider.getRemainingValidityMillis(jwtProvider.substringToken(refreshToken));

        jwtProvider.addBlacklistToken(accessToken, blacklistTokenTtl);
        jwtProvider.addBlacklistToken(refreshToken, blacklistTokenTtl);
        jwtProvider.deleteToken(accessToken);

        SecurityContextHolder.clearContext();
    }
}
