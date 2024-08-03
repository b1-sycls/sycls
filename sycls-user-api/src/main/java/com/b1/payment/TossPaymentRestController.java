package com.b1.payment;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.payment.dto.ClientResponseDto;
import com.b1.payment.dto.PaymentSuccessRequestDto;
import com.b1.payment.dto.TossConfirmRequestDto;
import com.b1.payment.dto.TossPaymentRestResponse;
import com.b1.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TossPaymentRestController {

    private final TossPaymentService tossPaymentService;

    @PostMapping("/payment/client-key")
    public ResponseEntity<RestApiResponseDto<ClientResponseDto>> getClientKey(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ClientResponseDto responseDto = tossPaymentService.getClientKey(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(responseDto));
    }

    /**
     * 결제
     */
    @PostMapping("/payment/confirm")
    public ResponseEntity<RestApiResponseDto<ResponseEntity<TossPaymentRestResponse>>> confirmPayment(
            @Valid @RequestBody final TossConfirmRequestDto requestDto
    ) {
        ResponseEntity<TossPaymentRestResponse> response = tossPaymentService.confirm(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(response));
    }

    /**
     * 인증성공처리
     */
    @PostMapping("/payment/success")
    public ResponseEntity<RestApiResponseDto<Object>> paymentRequest(
            @Valid @RequestBody final PaymentSuccessRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        tossPaymentService.successReservation(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("결제 성공"));
    }

    /**
     * 인증실패처리 TODO
     */
    @GetMapping("/payment/fail")
    public String failPayment(HttpServletRequest request, Model model) {
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "/fail";
    }
}
