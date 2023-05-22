package com.indekos.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.model.MasterRole;
import com.indekos.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static List<String> OWNER = new ArrayList<>(Arrays.asList("Owner", "Pemilik Indekos"));
    private static List<String> ADMIN = new ArrayList<>(Arrays.asList("Admin", "Pengelola/ Penjaga Indekos"));
    private static List<String> TENANT = new ArrayList<>(Arrays.asList("Tenant", "Penyewa Indekos"));
	
	public List<MasterRole> getAll() {
		return roleRepository.findAll();
	}
	
	public MasterRole getById(String roleId) {
		MasterRole targetMasterRole = roleRepository.findById(roleId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Role ID"));
		
		return targetMasterRole;
	}
	
	public MasterRole getByName(String roleName) {
		MasterRole targetMasterRole = roleRepository.findByName(roleName)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Role Name : " + roleName));

		return targetMasterRole;
	}
}