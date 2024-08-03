package com.b1.security;

import static com.b1.constant.TokenConstants.AUTHORIZATION_HEADER;
import static com.b1.constant.TokenConstants.BEARER_PREFIX;
import static com.b1.constant.TokenConstants.REFRESH_TOKEN_TIME;
import static com.b1.constant.TokenConstants.TOKEN_TIME;

import com.b1.exception.customexception.TokenException;
import com.b1.exception.errorcode.TokenErrorCode;
import com.b1.token.entity.BlacklistToken;
import com.b1.token.entity.Token;
import com.b1.token.repository.BlacklistTokenRepository;
import com.b1.token.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "Jwt Provider")
public class JwtProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final TokenRepository tokenRepository;
    private final BlacklistTokenRepository blacklistTokenRepository;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("토큰을 찾을 수 없음 : {}", tokenValue);
        throw new TokenException(TokenErrorCode.TOKEN_NOT_FOUND);
    }

    public boolean validateToken(String token) {
        try {
            if (isExistBlacklistToken(token)) {
                log.error("블랙리스트 토큰입니다.");
                throw new TokenException(TokenErrorCode.IS_BLACKLIST_TOKEN);
            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new TokenException(TokenErrorCode.INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new TokenException(TokenErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new TokenException(TokenErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new TokenException(TokenErrorCode.ILLEGAL_ARGUMENT);
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        String email = extractEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        String accessToken = req.getHeader(AUTHORIZATION_HEADER);
        return substringToken(accessToken);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public long extractExpirationMillis(String token) {
        return extractAllClaims(token).getExpiration().getTime();
    }

    public long getRemainingValidityMillis(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public void addToken(String accessToken, String refreshToken, long ttl) {
        Token newToken = Token.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .ttl(ttl)
                .build();
        tokenRepository.save(newToken);
    }

    public Token getToken(String token) {
        return tokenRepository.findById(token).orElseThrow(
                () -> new TokenException(TokenErrorCode.TOKEN_NOT_FOUND)
        );
    }

    public void deleteToken(String token) {
        tokenRepository.deleteById(token);
    }

    public void addBlacklistToken(String token, long ttl) {
        BlacklistToken blacklistToken = BlacklistToken.builder()
                .token(token)
                .ttl(ttl)
                .build();
        blacklistTokenRepository.save(blacklistToken);
    }

    public boolean isExistBlacklistToken(String token) {
        return blacklistTokenRepository.existsById(BEARER_PREFIX + token);
    }

}