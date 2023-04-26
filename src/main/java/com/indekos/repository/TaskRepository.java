package com.indekos.repository;

import com.indekos.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
	
	@Query(value = "SELECT * FROM task WHERE status LIKE %:status% ORDER BY created_date DESC", nativeQuery = true)
    List<Task> findAllTask(@Param("status") String status);
}
