package com.indekos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.services.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<?> getAllRoles(){
		return GlobalAcceptions.listData(roleService.getAll(), "All Master Role Data");
	}
	
	@GetMapping("/{roleId}")
	public ResponseEntity<?> getRoleById(@PathVariable String roleId){
		return GlobalAcceptions.data(roleService.getByRoleId(roleId), "Master Role Data");
	}
}
