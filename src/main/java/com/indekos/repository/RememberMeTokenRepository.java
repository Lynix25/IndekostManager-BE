package com.indekos.repository;

import com.indekos.model.Account;
import com.indekos.model.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, String> {
    Optional<RememberMeToken> findByAccount(Account account);
}
