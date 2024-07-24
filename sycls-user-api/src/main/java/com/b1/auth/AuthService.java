package com.b1.auth;

import com.b1.auth.entity.EmailVerificationCode;
import com.b1.auth.repository.EmailVerificationCodeRepository;
import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.user.UserHelper;
import com.b1.user.dto.UserResetPasswordRequestDto;
import com.b1.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "Auth Service")
public class AuthService {

    private final UserHelper userHelper;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;

    public boolean checkEmailExists(String email) {
        return userHelper.checkEmailExists(email);
    }

    public boolean checkNicknameExists(String nickname) {
        return userHelper.checkNicknameExists(nickname);
    }

    public void resetPassword (UserResetPasswordRequestDto requestDto) {
        User user = userHelper.findByEmail(requestDto.email());
        user.changePassword(passwordEncoder.encode(requestDto.newPassword()));
    }

    public void saveVerificationCode (String email, String code) {
        EmailVerificationCode emailVerificationCode = EmailVerificationCode.builder()
                .email(email)
                .code(code)
                .ttl(300)
                .build();
        emailVerificationCodeRepository.save(emailVerificationCode);
    }

    public boolean verifyCode  (String email, String code) {
        String storedCode = emailVerificationCodeRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND)
        ).getCode();
        return storedCode != null && storedCode.equals(code);
    }

    public String generateVerificationCode () {
        Random random = new Random();
        int code = random.nextInt(899999) + 100000; // 100000 ~ 999999 사이의 숫자 생성
        return String.valueOf(code);
    }
}
