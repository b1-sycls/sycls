package com.b1.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${NAVER_EMAIL}")
    private String from;

    private final String fromName = "이메일 인증 서비스";

    /**
     * TODO 이메일 전송 실패에 대한 예외클래스 추가
     * */
    public void sendVerificationCode(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(new InternetAddress(from, fromName)); // 발신자 이메일과 이름 설정
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("이메일 전송 실패", e);
            throw new IllegalStateException("이메일 전송 실패");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
