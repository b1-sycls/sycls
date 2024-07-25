package com.b1.auth;

import static com.b1.security.JwtProvider.AUTHORIZATION_HEADER;

import com.b1.auth.entity.Code;
import com.b1.auth.repository.CodeRepository;
import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.security.JwtProvider;
import com.b1.user.UserHelper;
import com.b1.user.dto.UserResetPasswordRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
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
    private final PasswordEncoder passwordEncoder;
    private final CodeRepository codeRepository;

    @Transactional(readOnly = true)
    public boolean checkEmailExists(String email) {
        return userHelper.checkEmailExists(email);
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameExists(String nickname) {
        return userHelper.checkNicknameExists(nickname);
    }

    public void resetPassword(UserResetPasswordRequestDto requestDto) {
        User user = userHelper.findByEmail(requestDto.email());
        if (UserStatus.isDeleted(user.getStatus())) {
            throw new UserAlreadyDeletedException(UserErrorCode.USER_ALREADY_DELETED);
        }
        user.changePassword(passwordEncoder.encode(requestDto.newPassword()));
    }

    public void saveVerificationCode(String email, String code) {
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
        codeRepository.save(emailVerificationCode);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = codeRepository.findById(email).orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND)
        ).getCode();
        return storedCode != null && storedCode.equals(code);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(899999) + 100000; // 100000 ~ 999999 사이의 숫자 생성
        return String.valueOf(code);
    }

    public void refreshToken(String email, HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        String refreshToken = jwtProvider.getToken(accessToken).getRefresh();

        log.info("{}",
                jwtProvider.getRemainingValidityMillis(jwtProvider.substringToken(refreshToken)));
        long blacklistTokenTtl = jwtProvider.getRemainingValidityMillis(
                jwtProvider.substringToken(refreshToken));

        jwtProvider.addBlacklistToken(accessToken, blacklistTokenTtl);
        jwtProvider.addBlacklistToken(refreshToken, blacklistTokenTtl);
        jwtProvider.deleteToken(accessToken);
    }
}
