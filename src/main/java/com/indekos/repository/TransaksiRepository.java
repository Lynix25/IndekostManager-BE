package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.Transaksi;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
}
