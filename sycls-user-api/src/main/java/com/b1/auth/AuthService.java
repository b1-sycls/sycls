package com.b1.auth;

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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "Auth Service")
public class AuthService {

    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public boolean checkEmailExists(String email) {
        return userAdapter.checkEmailExists(email);
    }

    public boolean checkNicknameExists(String nickname) {
        return userAdapter.checkNicknameExists(nickname);
    }

    /**
     * TODO 유저 패스워드 변경 Service 로직에서 Email 인증번호 + Redis 인증번호 관리 구현 필요
     */
    public void resetPassword(UserResetPasswordRequestDto requestDto) {
        User user = userAdapter.findByEmail(requestDto.email());

        user.changePassword(passwordEncoder.encode(requestDto.password()));
    }

    public void saveVerificationCode(String email, String code) {
        log.info("인증 코드를 Redis에 저장합니다: email={}, code={}", email, code); // 추가된 로깅
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email, code, Duration.ofMinutes(5)); // 5분간 유효
        log.info("인증 코드가 저장되었습니다."); // 추가된 로깅
    }

    public boolean verifyCode(String email, String code) {
        log.info("인증 코드를 확인합니다: email={}, code={}", email, code); // 추가된 로깅
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String storedCode = (String) valueOperations.get(email);
        log.info("Redis에서 가져온 인증 코드: {}", storedCode); // 추가된 로깅
        return storedCode != null && storedCode.equals(code);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(899999) + 100000; // 100000 ~ 999999 사이의 숫자 생성
        return String.valueOf(code);
    }
}
