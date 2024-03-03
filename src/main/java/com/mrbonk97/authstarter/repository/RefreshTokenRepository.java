package com.mrbonk97.authstarter.repository;

import com.mrbonk97.authstarter.model.Account;
import com.mrbonk97.authstarter.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByAccount(Account account);
    void deleteByAccount_Email(String email);
    Optional<RefreshToken> findByAccount(Account account);
    Optional<RefreshToken> findByToken(String token);
}
