package com.b1.auth.repository;

import com.b1.auth.entity.EmailVerificationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailVerificationCodeRepository extends CrudRepository<EmailVerificationCode, String> {
    Optional<EmailVerificationCode> findByEmail(String email);
}