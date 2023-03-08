package com.indekos.repository;

import com.indekos.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {

    @Query(value = "SELECT * FROM service WHERE created_by LIKE :user_id", nativeQuery = true)
    List<Service> findUnpaidById(@Param("user_id") String id);

}
