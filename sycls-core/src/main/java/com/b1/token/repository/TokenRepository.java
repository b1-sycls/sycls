package com.b1.token.repository;

import com.b1.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {

}