package com.b1.auth;

import com.b1.auth.entity.Code;
import com.b1.auth.repository.CodeRepository;
import com.b1.exception.customexception.EmailCodeException;
import com.b1.exception.errorcode.EmailAuthErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Code Helper")
@Component
@RequiredArgsConstructor
public class CodeHelper {

    private CodeRepository codeRepository;

    public String findCodeByEmail(String email) {
        return codeRepository.findById(email).orElseThrow(() -> {
            log.error("Code가 없는 email입니다: {}", email);
            return new EmailCodeException(EmailAuthErrorCode.USER_CODE_NOT_FOUND);
        }).getCode();
    }

    public void addCode(Code emailVerificationCode) {
        codeRepository.save(emailVerificationCode);
    }
}
