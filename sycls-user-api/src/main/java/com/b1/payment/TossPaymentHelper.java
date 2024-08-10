package com.b1.payment;

import com.b1.config.TossConfig;
import com.b1.exception.customexception.TossPaymentException;
import com.b1.exception.errorcode.PaymentErrorCode;
import com.b1.payment.dto.TossConfirmRequestDto;
import com.b1.payment.dto.TossPaymentRestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Set;

import static com.b1.constant.TossConstant.AUTHORIZATION;
import static com.b1.constant.TossConstant.BASIC;
import static com.b1.constant.TossConstant.TOSS_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j(topic = "Toss Payment Helper")
@Component
@RequiredArgsConstructor
public class TossPaymentHelper {
    private final PaymentRepository paymentRepository;

    /**
     * 결제 시도
     */
    public TossPaymentRestResponse confirmPayment(
            final TossConfig tossConfig,
            final TossConfirmRequestDto requestDto,
            final Set<Long> reservationByUser,
            final Long userId
    ) {
        paymentRepository.seatLock(requestDto.roundId(), reservationByUser, userId);
        String authorization = Base64.getEncoder().encodeToString((tossConfig.getPaymentSecretKey() + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, BASIC + authorization);

        try {
            URL url = new URL(TOSS_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(AUTHORIZATION, authorization);
            headers.setContentType(APPLICATION_JSON);
        } catch (Exception e) {
            throw new TossPaymentException(PaymentErrorCode.TOSS_PAYMENT_EXCEPTION);
        }

        HttpEntity<TossConfirmRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<TossPaymentRestResponse> response = new RestTemplate()
                .postForEntity(TOSS_URL, requestEntity, TossPaymentRestResponse.class);
        return response.getBody();
    }

    public void clearReservation(
            final Long roundId,
            final Long userId
    ) {
        paymentRepository.releaseReservation(roundId, userId);
    }
}
