package com.b1.auth;

import com.b1.auth.dto.UserEmailVerificationRequestDto;
import com.b1.email.EmailService;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserResetPasswordRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthRestController {

    private final AuthService authService;
    private final EmailService emailService;

    @GetMapping("/email/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkEmail(@RequestParam String email) {

        boolean isDuplicated = authService.checkDuplicateEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkNickname(@RequestParam String nickname) {

        boolean isDuplicated = authService.checkDuplicateNickname(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    @PutMapping("/auth/forget-password")
    public ResponseEntity<RestApiResponseDto<String>> resetPassword(@Valid @RequestBody UserResetPasswordRequestDto requestDto,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        authService.resetPassword(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("비밀번호가 성공적으로 변경되었습니다."));
    }

    @PostMapping("/users/send-verification-code")
    public ResponseEntity<RestApiResponseDto<String>> sendVerificationCode(@Valid @RequestBody UserEmailVerificationRequestDto requestDto) {
        String email = requestDto.email();
        String code = authService.generateVerificationCode();
        authService.saveVerificationCode(email, code);

        String subject = "비밀번호 변경을 위한 인증번호";
        String text = "인증번호는 다음과 같습니다: " + code;
        emailService.sendVerificationCode(email, subject, text);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("인증번호가 이메일로 전송되었습니다."));
    }


}
