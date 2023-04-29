package com.indekos.repository;

import com.indekos.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, String> {
    @Query(value = "SELECT * FROM rent WHERE created_by LIKE :user_id", nativeQuery = true)
    List<Rent> findUnpaidById(@Param("user_id") String id);
}
