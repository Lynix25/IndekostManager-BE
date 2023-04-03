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
	
	@PostConstruct
	void initializeMasterRole() {
		
		String targetField = "(id, created_by, created_date, last_modified_by, last_modified_date, name, description)";
		String auditDataValue = "UUID(), 'system', UNIX_TIMESTAMP(), 'system', UNIX_TIMESTAMP()";
		jdbcTemplate.update("INSERT IGNORE INTO master_role " + targetField + " VALUES (" + auditDataValue + ", '" + OWNER.get(0) + "', '" + OWNER.get(1) + "')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_role " + targetField + " VALUES (" + auditDataValue + ", '" + ADMIN.get(0) + "', '" + ADMIN.get(1) + "')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_role " + targetField + " VALUES (" + auditDataValue + ", '" + TENANT.get(0) + "', '" + TENANT.get(1) + "')");
	}
	
	public List<MasterRole> getAll() {
		return roleRepository.findAll();
	}
	
	public MasterRole getByRoleId(String roleId) {
		MasterRole targetMasterRole = roleRepository.findById(roleId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Role ID"));
		
		return targetMasterRole;
	}
}