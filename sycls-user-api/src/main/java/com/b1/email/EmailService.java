package com.b1.email;

import static com.b1.constant.EmailConstants.FROM_NAME;

import com.b1.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Email Service")
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;

    /**
     * TODO 이메일 전송 실패에 대한 예외클래스 추가
     */
    public void sendVerificationCode(final String to, final String subject, final String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(new InternetAddress(emailConfig.getUsername(), FROM_NAME)); // 발신자 이메일과 이름 설정
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
