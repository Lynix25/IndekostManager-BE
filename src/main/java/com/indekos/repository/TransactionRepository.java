package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
