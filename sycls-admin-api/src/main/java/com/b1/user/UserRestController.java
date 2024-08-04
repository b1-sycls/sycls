package com.b1.user;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserProfileResponseDto;
import com.b1.user.dto.UserResignRequestDto;
import com.b1.user.dto.UserSignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserRestController {

    private final UserService userService;

    /**
     * 회원가입 기능
     *
     * @param requestDto : email, username, nickname, phone_number, password
     */
    @PostMapping("/users/signup")
    public ResponseEntity<RestApiResponseDto<String>> signup(
            @Valid @RequestBody final UserSignupRequestDto requestDto
    ) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestApiResponseDto.of("회원가입에 성공하였습니다!"));
    }

    /**
     * 회원탈퇴 기능
     *
     * @param requestDto : password
     */
    @PatchMapping("/users/resign")
    public ResponseEntity<RestApiResponseDto<String>> resign(
            @Valid @RequestBody final UserResignRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        userService.resign(requestDto, userDetails);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(
                        "유저가 삭제되었습니다. User Email : " + userDetails.getUser().getEmail()));
    }

    /**
     * 유저 프로필 조회 기능
     */
    @GetMapping("/users/me")
    public ResponseEntity<RestApiResponseDto<UserProfileResponseDto>> getProfile(
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        UserProfileResponseDto responseDto = userService.getProfile(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("성공", responseDto));
    }

}
