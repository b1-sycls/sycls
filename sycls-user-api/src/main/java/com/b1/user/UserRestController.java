package com.b1.user;

import com.b1.user.dto.UserSignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {

        userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입에 성공하였습니다!");
    }
}
