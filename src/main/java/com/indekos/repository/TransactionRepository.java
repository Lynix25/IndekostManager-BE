package com.indekos.repository;

import com.indekos.model.Transaction;
import com.indekos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUser(User user);

    Optional<Transaction> findByPaymentId(String paymentId);
}
