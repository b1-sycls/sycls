package com.b1.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class TossConfig {

    @Value("${toss.clientKey}")
    private String paymentClientKey;

    @Value("${toss.secretKey}")
    private String paymentSecretKey;
}
