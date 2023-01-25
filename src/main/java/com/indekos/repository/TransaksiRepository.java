package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.Transaksi;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
}
