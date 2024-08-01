package com.b1.auth.repository;

import com.b1.auth.entity.BlacklistToken;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistTokenRepository extends CrudRepository<BlacklistToken, String> {
}