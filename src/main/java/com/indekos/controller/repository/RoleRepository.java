package com.indekos.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.MasterRole;

@Repository
public interface RoleRepository extends JpaRepository<MasterRole, String> {
	
	MasterRole findByName(String roleName);
}
