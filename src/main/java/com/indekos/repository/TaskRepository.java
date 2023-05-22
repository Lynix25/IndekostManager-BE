package com.indekos.repository;

import com.indekos.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
	
	
	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% ORDER BY created_date DESC", nativeQuery = true)
	List<Task> findAllByRequestor(@Param("requestor") String requestor);
	
	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% AND status LIKE 'Menunggu Konfirmasi' "
					+ "ORDER BY created_date DESC", nativeQuery = true)
	List<Task> findWaitingConfirmationTaskByRequestor(@Param("requestor") String requestor);
	
	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% AND "
			+ "(status LIKE 'Menunggu Konfirmasi' OR status LIKE 'Diterima' OR status LIKE 'Dalam Pengerjaan') "
			+ "ORDER BY created_date DESC", nativeQuery = true)
	List<Task> findActiveTaskByRequestor(@Param("requestor") String requestor);

	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% AND "
			+ "transaction_id IS NULL AND (charge > 0 AND charge IS NOT NULL)"
			+ "ORDER BY created_date DESC", nativeQuery = true)
	List<Task> findUnpaidTaskByRequestor(@Param("requestor") String requestor);

	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% ORDER BY task_date ASC", nativeQuery = true)
	List<Task> findAllOrderByTarget(@Param("requestor") String requestor);

	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% AND status LIKE 'Menunggu Konfirmasi' "
			+ "ORDER BY created_date DESC", nativeQuery = true)
	List<Task> findAllChargedTask(@Param("requestor") String requestor);
	
//	@Query(value = "SELECT * FROM task WHERE created_by LIKE %:requestor% AND price_adjustment "
//			+ "ORDER BY created_date DESC", nativeQuery = true)
//	List<Task> findChargedTaskByRequestor(@Param("requestor") String requestor);
}