package com.b1.payment;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.payment.dto.TossConfirmRequestDto;
import com.b1.payment.dto.TossPaymentRestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final TossPaymentService tossPaymentService;

    /**
     * 결제
     */
    @PostMapping("/payment/confirm")
    public ResponseEntity<RestApiResponseDto<ResponseEntity<TossPaymentRestResponse>>> confirmPayment(
            @Valid @RequestBody final TossConfirmRequestDto requestDto
    ) throws Exception {
        ResponseEntity<TossPaymentRestResponse> response = tossPaymentService.confirm(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(response));
    }

    /**
     * 인증성공처리
     */
    @GetMapping("/payment/success")
    public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
        //seat_grade_status DISABLE
        log.info("success");
        return "/success";
    }

    /**
     * 인증실패처리
     */
    @GetMapping("/payment/fail")
    public String failPayment(HttpServletRequest request, Model model) throws Exception {
        log.info("fail");
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "/fail";
    }

}
