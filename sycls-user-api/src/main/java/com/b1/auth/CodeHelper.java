package com.b1.auth;

import com.b1.auth.repository.CodeRepository;
import com.b1.exception.customexception.TokenException;
import com.b1.exception.errorcode.TokenErrorCode;
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
            return new TokenException(TokenErrorCode.USER_TOKEN_NOT_FOUND);
        }).getCode();
    }
}
