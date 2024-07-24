package com.b1.auth;

import com.b1.auth.dto.EmailVerificationRequestDto;
import com.b1.auth.dto.UserVerificationCodeRequestDto;
import com.b1.email.EmailService;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.user.dto.UserResetPasswordRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthRestController {

    private final AuthService authService;
    private final EmailService emailService;

    /**
     *  이메일 중복 체크
     * */
    @GetMapping("/email/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkEmail (
            @RequestParam String email
    ) {
        boolean isDuplicated = authService.checkEmailExists(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    /**
     * 닉네임 중복 체크
     * */
    @GetMapping("/nickname/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkNickname (
            @RequestParam String nickname
    ) {
        boolean isDuplicated = authService.checkNicknameExists(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    /**
     * 인증 번호 메일 전송
     * @param requestDto  : 인증 번호를 받을 User 이메일
     *                   인증 번호를 생성해서 Redis 에 저장 후 입력 받은 이메일로 인증번호 발송.
     *                   해당 API 는 회원가입, 비밀번호 찾기에 사용되는 API
     * */
    @PostMapping("/auth/send-verification-code")
    public ResponseEntity<RestApiResponseDto<String>> sendVerificationCode (
            @Valid @RequestBody EmailVerificationRequestDto requestDto
    ) {
        String email = requestDto.email();
        String code = authService.generateVerificationCode();
        String subject = "이메일 인증을 위한 인증번호";
        String text = "인증번호는 다음과 같습니다: " + code;

        authService.saveVerificationCode(email, code);
        emailService.sendVerificationCode(email, subject, text);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("인증번호가 이메일로 전송되었습니다."));
    }

    /**
     * 인증 번호 체크
     * @return : 인증 번호가 일치하면 -> "본인 인증이 완료됐습니다.", true | 인증 번호가 불일치하면 -> "인증 코드가 일치하지 않습니다.", false
     * */
    @PostMapping("/auth/check-verification-code")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkVerificationCode (
            @Valid @RequestBody UserVerificationCodeRequestDto requestDto
    ) {
        boolean isVerified = authService.verifyCode(requestDto.email(), requestDto.code());
        if (isVerified) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(RestApiResponseDto.of("본인 인증이 완료됐습니다.", true));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("인증 코드가 일치하지 않습니다.", false));
    }

    /**
     * 비밀번호 변경
     * @param requestDto : email(비밀번호를 바꿀 이메일), newPassword(새로 바꿀 비밀번호), code(메일 인증코드)
     * */
    @PutMapping("/auth/forget-password")
    public ResponseEntity<RestApiResponseDto<String>> resetPassword (
            @Valid @RequestBody UserResetPasswordRequestDto requestDto
    ) {
        authService.resetPassword(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("비밀번호가 성공적으로 변경되었습니다."));
    }
}
