package com.b1.auth;

import static com.b1.constant.AuthConstants.EMAIL_SUBJECT;
import static com.b1.constant.AuthConstants.EMAIL_TEXT;
import static com.b1.constant.EmailConstants.AUTH_FAILURE_MESSAGE;
import static com.b1.constant.EmailConstants.AUTH_SUCCESS_MESSAGE;

import com.b1.auth.dto.EmailVerificationRequestDto;
import com.b1.auth.dto.UserVerificationCodeRequestDto;
import com.b1.email.EmailService;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.user.dto.UserResetPasswordRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j(topic = "Auth Rest Controller")
public class AuthRestController {

    private final AuthService authService;
    private final EmailService emailService;

    /**
     * 이메일 중복 체크
     */
    @GetMapping("/email/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkEmail(
            @RequestParam final String email
    ) {
        boolean isDuplicated = authService.checkEmailExists(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    /**
     * 닉네임 중복 체크
     */
    @GetMapping("/nickname/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkNickname(
            @RequestParam final String nickname
    ) {
        boolean isDuplicated = authService.checkNicknameExists(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    /**
     * 인증 번호 메일 전송
     *
     * @param requestDto : email
     * @apiNote : 인증 번호를 생성해서 Redis 에 저장 후 입력 받은 이메일로 인증번호 발송. 해당 API 는 회원가입, 비밀번호 찾기에 사용되는 API
     */
    @PostMapping("/auth/send-verification-code")
    public ResponseEntity<RestApiResponseDto<String>> sendVerificationCode(
            @Valid @RequestBody final EmailVerificationRequestDto requestDto
    ) {
        String email = requestDto.email();
        String code = authService.generateVerificationCode();
        String subject = EMAIL_SUBJECT;
        String text = EMAIL_TEXT + code;

        authService.saveVerificationCode(email, code);
        emailService.sendVerificationCode(email, subject, text);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("인증번호가 이메일로 전송되었습니다."));
    }

    /**
     * 인증 번호 체크
     *
     * @return : 인증 번호가 일치하면 -> "본인 인증이 완료됐습니다.", true | 인증 번호가 불일치하면 -> "인증 코드가 일치하지 않습니다.", false
     */
    @PostMapping("/auth/check-verification-code")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkVerificationCode(
            @Valid @RequestBody final UserVerificationCodeRequestDto requestDto
    ) {
        String message = AUTH_SUCCESS_MESSAGE;
        boolean isChecked = true;
        if (!authService.verifyCode(requestDto.email(), requestDto.code())) {
            message = AUTH_FAILURE_MESSAGE;
            isChecked = false;
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(message, isChecked));
    }

    /**
     * 비밀번호 변경
     *
     * @param requestDto : email(비밀번호를 바꿀 이메일), newPassword(새로 바꿀 비밀번호), code(메일 인증코드)
     */
    @PatchMapping("/auth/forget-password")
    public ResponseEntity<RestApiResponseDto<String>> resetPassword(
            @Valid @RequestBody final UserResetPasswordRequestDto requestDto
    ) {
        authService.resetPassword(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("비밀번호가 성공적으로 변경되었습니다."));
    }

    /**
     * 이메일 찾기
     *
     * @param username    : 회원가입 한 유저 이름
     * @param phoneNumber : 회원가입 한 전화번호
     * @apiNote : 본인 이메일로 회원가입 할 때 입력했던 username 과 phoneNumber 로 이메일 조회
     */
    @GetMapping("/auth/forget-email")
    public ResponseEntity<RestApiResponseDto<String>> findEmail(
            @RequestParam("username") @NotEmpty final String username,
            @RequestParam("phoneNumber") @NotEmpty final String phoneNumber) {
        String findEmail = authService.findEmail(username, phoneNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("이메일을 찾았습니다.", findEmail));
    }

    /**
     * 토큰 재발급 API
     *
     * @apiNote : 새 엑세스, 리프레쉬 토크을 발급하고 기존의 엑세스, 리프레쉬 토큰을 Blacklist 에 적재함. 그 후 기존의 엑세스토큰을 삭제함.
     */
    @PostMapping("/auth/token")
    public ResponseEntity<RestApiResponseDto<String>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.refreshToken(request, response);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("토큰이 성공적으로 재발급 됐습니다."));
    }
}
