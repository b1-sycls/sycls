package com.b1.payment;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.payment.dto.ClientResponseDto;
import com.b1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
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
        ClientResponseDto responseDto = tossPaymentService.getClientKey();
        return ResponseEntity.status(200)
                .body(RestApiResponseDto.of(responseDto));
    }

}
