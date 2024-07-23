package com.b1.auth;

import com.b1.auth.dto.EmailVerificationRequestDto;
import com.b1.auth.dto.UserVerificationCodeRequestDto;
import com.b1.email.EmailService;
import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserResetPasswordRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthRestController {

    private final AuthService authService;
    private final EmailService emailService;

    /**
     *  이메일 중복 체크
     * @param email : 중복 체크할 이메일
     * */
    @GetMapping("/email/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkEmail(@RequestParam String email) {

        boolean isDuplicated = authService.checkEmailExists(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    /**
     * 닉네임 중복 체크
     * @param nickname : 중복 체크할 닉네임
     * */
    @GetMapping("/nickname/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkNickname(@RequestParam String nickname) {

        boolean isDuplicated = authService.checkNicknameExists(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    /**
     * 인증 번호 메일 전송
     * @param requestDto : 인증 번호를 받을 User 이메일
     * 인증 번호를 생성해서 Redis 에 저장 후 입력 받은 이메일로 인증번호 발송.
     * */
    @PostMapping("/auth/send-verification-code")
    public ResponseEntity<RestApiResponseDto<String>> sendVerificationCode(@Valid @RequestBody EmailVerificationRequestDto requestDto) {
        if (!authService.checkEmailExists(requestDto.email())) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }

        String email = requestDto.email();
        String code = authService.generateVerificationCode();
        String subject = "비밀번호 변경을 위한 인증번호";
        String text = "인증번호는 다음과 같습니다: " + code;

        authService.saveVerificationCode(email, code);
        emailService.sendVerificationCode(email, subject, text);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("인증번호가 이메일로 전송되었습니다."));
    }

    @PostMapping("/auth/check-verification-code")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkVerificationCode(@Valid @RequestBody UserVerificationCodeRequestDto requestDto) {
        boolean isVerified = authService.verifyCode(requestDto.email(), requestDto.code());
        if (isVerified) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(RestApiResponseDto.of("본인 인증이 완료됐습니다.", true));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("인증 코드가 일치하지 않습니다.", false));
    }

    @PutMapping("/auth/forget-password")
    public ResponseEntity<RestApiResponseDto<String>> resetPassword(@Valid @RequestBody UserResetPasswordRequestDto requestDto,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        authService.resetPassword(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("비밀번호가 성공적으로 변경되었습니다."));
    }
}
