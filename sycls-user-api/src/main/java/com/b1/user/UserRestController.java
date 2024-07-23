package com.b1.user;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserResetPasswordRequestDto;
import com.b1.user.dto.UserResignRequestDto;
import com.b1.user.dto.UserSignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<RestApiResponseDto<String>> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {

        userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestApiResponseDto.of("회원가입에 성공하였습니다!"));
    }

    @DeleteMapping("/users/resign")
    public ResponseEntity<RestApiResponseDto<String>> signup(@Valid @RequestBody UserResignRequestDto requestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.resign(requestDto, userDetails);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("유저가 삭제되었습니다. User Email : " + userDetails.getUser().getEmail()));
    }

    @GetMapping("/email/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkEmail(@RequestParam String email) {

        boolean isDuplicated = userService.checkDuplicateEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<RestApiResponseDto<Boolean>> checkNickname(@RequestParam String nickname) {

        boolean isDuplicated = userService.checkDuplicateNickname(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(isDuplicated));
    }

    @PutMapping("/users/reset-password")
    public ResponseEntity<RestApiResponseDto<String>> resetPassword(@Valid @RequestBody UserResetPasswordRequestDto requestDto,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.resetPassword(requestDto, userDetails);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("비밀번호가 성공적으로 변경되었습니다."));
    }
}
