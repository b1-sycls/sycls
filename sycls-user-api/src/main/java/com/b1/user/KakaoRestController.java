package com.b1.user;

import static com.b1.constant.TokenConstants.AUTHORIZATION_HEADER;
import static com.b1.constant.TokenConstants.REFRESHTOKEN_HEADER;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.user.dto.KakaoUserInfoResponseDto;
import com.b1.user.dto.UserKakaoProfileUpdateRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class KakaoRestController {

    private final KakaoService kakaoService;

    @Value("${client.server.address}")
    private String clientServerAddress;

    /**
     * 카카오 회원가입 콜백
     */
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<RestApiResponseDto<String>> kakaoLogin(@RequestParam String code,
            HttpServletResponse response)
            throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        KakaoUserInfoResponseDto kakaoUserInfo = kakaoService.kakaoLogin(code, response);

        // 클라이언트로 리다이렉트 (쿼리 파라미터로 토큰 전달)
        String accessToken = response.getHeader(AUTHORIZATION_HEADER);
        String refreshToken = response.getHeader(REFRESHTOKEN_HEADER);

        String redirectUrl = "http://";
        if (kakaoUserInfo.isNewUser()) {
            // 신규 사용자일 경우 추가 정보 입력 페이지로 리다이렉트
            redirectUrl +=
                    clientServerAddress + "/kakao-signup?accessToken=" + accessToken
                            + "&refreshToken=" + refreshToken;
        } else {
            // 기존 사용자일 경우 메인 페이지로 리다이렉트
            redirectUrl +=
                    clientServerAddress + "/kakao-callback?accessToken=" + accessToken
                            + "&refreshToken=" + refreshToken;
        }

        // RestApiResponseDto를 통해 메시지와 리다이렉션 URL을 본문에 포함
        RestApiResponseDto<String> responseBody = RestApiResponseDto.of("리다이렉션을 수행합니다.",
                redirectUrl);

        // 리다이렉션 URL을 Location 헤더에 설정하고, 본문에 추가 정보를 포함한 응답 반환
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .body(responseBody);
    }

    @PatchMapping("/user/kakao/signup")
    public ResponseEntity<RestApiResponseDto<String>> completeSignup(
            @Valid @RequestBody final UserKakaoProfileUpdateRequestDto requestDto
    ) {

        kakaoService.profileUpdate(requestDto.accessToken(), requestDto.nickname(),
                requestDto.phoneNumber());

        // RestApiResponseDto를 이용해 반환
        return ResponseEntity.ok(RestApiResponseDto.of("카카오 회원가입이 완료되었습니다."));
    }
}
