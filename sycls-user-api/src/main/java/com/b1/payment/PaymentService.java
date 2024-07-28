package com.b1.payment;

import com.b1.payment.dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Reservation Service")
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    @Value("${toss.clientKey}")
    private String paymentClientKey;

    public ClientResponseDto getClientKey() {
        return ClientResponseDto.of(paymentClientKey);
    }
}
