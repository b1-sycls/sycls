package com.b1.user;

import static com.b1.constant.TokenConstants.AUTHORIZATION_HEADER;
import static com.b1.constant.TokenConstants.REFRESHTOKEN_HEADER;

import com.b1.user.dto.KakaoUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class KakaoRestController {

    private final KakaoService kakaoService;

    /**
     * 카카오 회원가입 콜백
     */
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Void> kakaoLogin(@RequestParam String code, HttpServletResponse response)
            throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        KakaoUserInfoDto kakaoUserInfo = kakaoService.kakaoLogin(code, response);

        // 클라이언트로 리다이렉트 (쿼리 파라미터로 토큰 전달)
        String accessToken = response.getHeader(AUTHORIZATION_HEADER);
        String refreshToken = response.getHeader(REFRESHTOKEN_HEADER);

        //TODO 실제 서비스에서는 localhost 수정

        String redirectUrl;

        if (kakaoUserInfo.isNewUser()) {
            // 신규 사용자일 경우 추가 정보 입력 페이지로 리다이렉트
            redirectUrl =
                    "http://localhost:9090/kakao-signup?accessToken=" + accessToken
                            + "&refreshToken="
                            + refreshToken;
        } else {
            // 기존 사용자일 경우 메인 페이지로 리다이렉트
            redirectUrl = "http://localhost:9090/kakao-callback?accessToken=" + accessToken
                    + "&refreshToken=" + refreshToken;
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    @PostMapping("/user/kakao/signup")
    public ResponseEntity<Void> completeSignup(
            @RequestParam String accessToken,
            @RequestParam String nickname,
            @RequestParam String phoneNumber) {

        kakaoService.profileUpdate(accessToken, nickname, phoneNumber);

        return ResponseEntity.ok().build();
    }
}
