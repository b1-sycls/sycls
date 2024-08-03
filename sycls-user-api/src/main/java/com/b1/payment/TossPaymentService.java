package com.b1.payment;

import com.b1.config.TossConfig;
import com.b1.constant.TossConstant;
import com.b1.payment.dto.ClientResponseDto;
import com.b1.payment.dto.TossConfirmRequestDto;
import com.b1.payment.dto.TossPaymentRestResponse;
import com.b1.security.UserDetailsImpl;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import static com.b1.constant.TossConstant.*;
import static org.springframework.http.MediaType.*;

@Slf4j(topic = "Toss Payment Service")
@Service
@RequiredArgsConstructor
@Transactional
public class TossPaymentService {

    private final TossConfig tossConfig;

    /**
     * 토스페이먼츠 ClientKey 및 User정보 전송
     */
    public ClientResponseDto getClientKey(
            final User user
    ) {
        return ClientResponseDto.of(tossConfig.getPaymentClientKey(), user);
    }

    public ResponseEntity<TossPaymentRestResponse> confirm(
            final TossConfirmRequestDto requestDto
    ) throws Exception {

        String authorization = Base64.getEncoder().encodeToString((tossConfig.getPaymentSecretKey() + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, BASIC + authorization);


        URL url = new URL(TOSS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(AUTHORIZATION, authorization);
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<TossConfirmRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        return new RestTemplate()
                .postForEntity(TOSS_URL, requestEntity, TossPaymentRestResponse.class);
    }

}
