package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.MasterRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<MasterRole, String> {
	
	Optional<MasterRole> findByName(String roleName);
}
