package org.edutecno.backend.auth.repository;

import java.util.List;
import java.util.Optional;

import org.edutecno.backend.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

  Optional<Token> findByToken(String token);

  List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id);
}

