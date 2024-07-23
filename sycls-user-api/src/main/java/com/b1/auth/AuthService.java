package com.b1.auth;

import com.b1.auth.entity.EmailVerificationCode;
import com.b1.auth.repository.EmailVerificationCodeRepository;
import com.b1.user.UserAdapter;
import com.b1.user.dto.UserResetPasswordRequestDto;
import com.b1.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "Auth Service")
public class AuthService {

    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public boolean checkDuplicateEmail(String email) {
        return userAdapter.checkDuplicateEmail(email);
    }

    public boolean checkDuplicateNickname(String nickname) {
        return userAdapter.checkDuplicateNickname(nickname);
    }

    /**
     * TODO 유저 패스워드 변경 Service 로직에서 Email 인증번호 + Redis 인증번호 관리 구현 필요
     */
    public void resetPassword(UserResetPasswordRequestDto requestDto) {
        User user = userAdapter.findByEmail(requestDto.email());

        user.changePassword(passwordEncoder.encode(requestDto.password()));
    }

    public void saveVerificationCode(String email, String code) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email, code, Duration.ofMinutes(5)); // 5분간 유효
    }

    public boolean verifyCode(String email, String code) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String storedCode = (String) valueOperations.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(899999) + 100000; // 100000 ~ 999999 사이의 숫자 생성
        return String.valueOf(code);
    }
}
