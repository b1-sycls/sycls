package com.b1.security;

import static com.b1.constant.TokenConstants.AUTHORIZATION_HEADER;

import com.b1.exception.customexception.TokenException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = req.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(tokenValue)) {
            // JWT 토큰 substring
            tokenValue = jwtProvider.substringToken(tokenValue);
            log.info(tokenValue);

            try {
                if (!jwtProvider.validateToken(tokenValue)) {
                    log.error("Token validation failed.");
                    res.setContentType("application/json; charset=UTF-8"); // 콘텐츠 타입과 캐릭터 인코딩 설정
                    res.setCharacterEncoding("UTF-8"); // 캐릭터 인코딩 설정
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    res.getWriter().write("Invalid Token");
                    return;
                }

                Claims info = jwtProvider.getUserInfoFromToken(tokenValue);
                setAuthentication(info.getSubject());
            } catch (TokenException e) {
                log.error("Token error: {}", e.getErrorCode().getHttpStatusCode());
                res.setContentType("application/json; charset=UTF-8"); // 콘텐츠 타입과 캐릭터 인코딩 설정
                res.setCharacterEncoding("UTF-8"); // 캐릭터 인코딩 설정
                res.setStatus(e.getErrorCode().getHttpStatusCode());
                res.getWriter().write(e.getErrorCode().getDescription());
                return;
            } catch (Exception e) {
                log.error("An unexpected error occurred: {}", e.getMessage());
                res.setContentType("application/json; charset=UTF-8"); // 콘텐츠 타입과 캐릭터 인코딩 설정
                res.setCharacterEncoding("UTF-8"); // 캐릭터 인코딩 설정
                res.setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
                res.getWriter().write("An unexpected error occurred.");
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
    }
}
