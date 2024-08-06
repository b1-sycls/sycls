package com.b1.email;

import static com.b1.constant.EmailConstants.CANCEL_FROM_NAME;
import static com.b1.constant.EmailConstants.CONTENT_CANCEL_SUBJECT;
import static com.b1.constant.EmailConstants.FROM_NAME;

import com.b1.config.EmailConfig;
import com.b1.exception.customexception.EmailSendFailException;
import com.b1.exception.customexception.EncodingNotSupportedException;
import com.b1.exception.errorcode.EmailErrorCode;
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
    public void sendVerificationCode(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(
                    new InternetAddress(emailConfig.getUsername(), FROM_NAME)); // 발신자 이메일과 이름 설정
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

    /**
     * 공연취소로인한 이메일 발송
     */
    public void sendContentCancelEmail(final String userEmail, final String contentTitle,
            final Integer roundSequence) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(new InternetAddress(emailConfig.getUsername(), CANCEL_FROM_NAME));
            helper.setTo(userEmail);
            helper.setSubject(CONTENT_CANCEL_SUBJECT);
            helper.setText(contentTitle + "의 " + roundSequence + "회차 공연이 취소 되었음을 알려드립니다.", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("이메일 전송 실패", e);
            throw new EmailSendFailException(EmailErrorCode.EMAIL_SEND_FAIL);
        } catch (UnsupportedEncodingException e) {
            log.error("지원되지 않는 인코딩 타입입니다.", e);
            throw new EncodingNotSupportedException(EmailErrorCode.ENCODING_NOT_SUPPORTED);
        }
    }
}
