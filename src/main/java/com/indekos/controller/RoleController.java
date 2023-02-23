package com.indekos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.services.RoleService;
import com.indekos.dto.request.RoleRequest;
import com.indekos.model.MasterRole;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/all")
	public List<MasterRole> getAllRoles(){
		return roleService.getAll();
	}
	
	@GetMapping
	public MasterRole getRoleByName(@RequestParam String roleName){
		return roleService.getByName(roleName);
	}

	@PostMapping
	public MasterRole createRole(@Validated @RequestBody RoleRequest request) {
		return roleService.create(request);
	}
	
	@PutMapping("/{roleId}")
	public ResponseEntity<MasterRole> updateRole(@PathVariable String roleId, @Validated @RequestBody RoleRequest request) throws ResourceNotFoundException {
		final MasterRole updatedMasterRole = roleService.update(roleId, request);
		return ResponseEntity.ok(updatedMasterRole);
	}
	
	@DeleteMapping("/{roleId}")
	public Map<String, Boolean> deleteRole(@PathVariable String roleId) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", roleService.delete(roleId));
		return response;
	}
}