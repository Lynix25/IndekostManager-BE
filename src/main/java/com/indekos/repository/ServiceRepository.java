package com.indekos.repository;

import com.indekos.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {

//    @Query(value = "SELECT * FROM room WHERE name LIKE :room_name AND id NOT LIKE :room_id AND is_deleted IS FALSE", nativeQuery = true)
//    List<Service> findUnpaidByid(@Param("user_id") String id);

}
