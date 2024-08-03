package com.b1.auth;

import static com.b1.constant.TokenConstants.REFRESHTOKEN_HEADER;

import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.security.JwtProvider;
import com.b1.token.entity.Code;
import com.b1.user.UserHelper;
import com.b1.user.dto.UserResetPasswordRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "Auth Service")
public class AuthService {

    private final UserHelper userHelper;
    private final JwtProvider jwtProvider;
    private final CodeHelper codeHelper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public boolean checkEmailExists(final String email) {
        return userHelper.checkEmailExists(email);
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameExists(final String nickname) {
        return userHelper.checkNicknameExists(nickname);
    }

    public void resetPassword(final UserResetPasswordRequestDto requestDto) {
        User user = userHelper.findByEmail(requestDto.email());
        if (UserStatus.isDeleted(user.getStatus())) {
            throw new UserAlreadyDeletedException(UserErrorCode.USER_ALREADY_DELETED);
        }
        user.changePassword(passwordEncoder.encode(requestDto.newPassword()));
    }

    public void saveVerificationCode(final String email, final String code) {
        if (userHelper.checkEmailExists(email)) {
            User user = userHelper.findByEmail(email);
            if (UserStatus.isDeleted(user.getStatus())) {
                throw new UserAlreadyDeletedException(UserErrorCode.USER_ALREADY_DELETED);
            }
        }
        Code emailVerificationCode = Code.builder()
                .email(email)
                .code(code)
                .ttl(300)
                .build();
        codeHelper.addCode(emailVerificationCode);
    }

    public boolean verifyCode(final String email, final String code) {
        String storedCode = codeHelper.findCodeByEmail(email);
        return storedCode != null && storedCode.equals(code);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(899999) + 100000; // 100000 ~ 999999 사이의 숫자 생성
        return String.valueOf(code);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String requestRefreshToken = request.getHeader(REFRESHTOKEN_HEADER);

        String getAccessToken = jwtProvider.getToken(requestRefreshToken).getAccess();

        log.info("남은 유효시간 {}", jwtProvider.getRemainingValidityMillis(
                jwtProvider.substringToken(requestRefreshToken)));
        long blacklistTokenTtl = jwtProvider.getRemainingValidityMillis(
                jwtProvider.substringToken(requestRefreshToken));

        jwtProvider.addBlacklistToken(getAccessToken, blacklistTokenTtl);
        jwtProvider.addBlacklistToken(requestRefreshToken, blacklistTokenTtl);
        jwtProvider.deleteToken(requestRefreshToken);

        String userEmail = jwtProvider.extractEmail(
                jwtProvider.substringToken(requestRefreshToken));
        // 토큰 생성
        String newAccessToken = jwtProvider.createAccessToken(userEmail);
        String newRefreshToken = jwtProvider.createRefreshToken(userEmail);

        // 헤더에 토큰 저장
        response.setHeader("Authorization", newAccessToken);
        response.setHeader("RefreshToken", newRefreshToken);
        jwtProvider.addToken(newAccessToken, newRefreshToken,
                jwtProvider.extractExpirationMillis(jwtProvider.substringToken(newRefreshToken)));
    }

    @Transactional(readOnly = true)
    public String findEmail(final String username, final String phoneNumber) {
        List<User> userList = userHelper.findAllByUsername(username);
        for (User user : userList) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user.getEmail();
            }
        }
        log.error("유저를 찾지 못함 : {}", username);
        throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
    }
}
