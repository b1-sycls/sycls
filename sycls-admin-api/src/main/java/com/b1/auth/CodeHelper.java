package com.b1.auth;

import com.b1.exception.customexception.EmailCodeException;
import com.b1.exception.errorcode.EmailAuthErrorCode;
import com.b1.token.entity.Code;
import com.b1.token.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Code Helper")
@Component
@RequiredArgsConstructor
public class CodeHelper {

    private final CodeRepository codeRepository;

    public String findCodeByEmail(final String email) {
        return codeRepository.findById(email).orElseThrow(() -> {
            log.error("Code가 없는 email입니다: {}", email);
            return new EmailCodeException(EmailAuthErrorCode.USER_CODE_NOT_FOUND);
        }).getCode();
    }

    public void addCode(final Code emailVerificationCode) {
        codeRepository.save(emailVerificationCode);
    }
}
