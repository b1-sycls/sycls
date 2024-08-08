package com.b1.user;

import com.b1.globalresponse.RestApiResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final KakaoService kakaoService;

    /**
     * 카카오 회원가입 콜백
     */
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<RestApiResponseDto<String>> kakaoLogin(@RequestParam String code,
            HttpServletResponse response)
            throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        String mesaage = kakaoService.kakaoLogin(code, response);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestApiResponseDto.of("로그인에 성공하였습니다!", mesaage));
    }
}
