package com.b1.auth.repository;

import com.b1.auth.entity.Code;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CodeRepository extends CrudRepository<Code, String> {
    Optional<Code> findByEmail(String email);
}