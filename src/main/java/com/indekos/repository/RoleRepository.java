package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indekos.model.MasterRole;

@Repository
public interface RoleRepository extends JpaRepository<MasterRole, String> {
	
	@Query(value = "SELECT * FROM role WHERE is_deleted IS FALSE ORDER BY name ASC", nativeQuery = true)
	List<MasterRole> findAllActiveByOrderByNameAsc();
	
	MasterRole findByName(String roleName);
	
	@Query(value = "SELECT * FROM role WHERE name LIKE :role_name AND id NOT LIKE :role_id", nativeQuery = true)
	MasterRole findByNameAndIdNot(@Param("role_name") String roleName, @Param("role_id") String roleId);
}
