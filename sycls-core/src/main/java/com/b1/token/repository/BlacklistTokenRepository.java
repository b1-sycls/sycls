package com.b1.token.repository;

import com.b1.token.entity.BlacklistToken;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistTokenRepository extends CrudRepository<BlacklistToken, String> {

}