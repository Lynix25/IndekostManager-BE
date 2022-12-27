package com.indekos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
	List<Role> findAllByOrderByNameAsc();
	Role findByName(String name);
	Role findByNameAndIdNot(String name, String id);
}
