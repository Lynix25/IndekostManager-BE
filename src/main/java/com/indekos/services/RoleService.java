package com.indekos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.DataNotFoundException;
import com.indekos.common.helper.exception.InternalServerErrorException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.RoleRequest;
import com.indekos.model.Role;
import com.indekos.repository.RoleRepository;
import com.indekos.repository.UserRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Role> getAll() {
		return roleRepository.findAllByOrderByNameAsc();
	}
	
	public Role getByName(String roleName) {
		Role targetRole = roleRepository.findByName(roleName);
		if(targetRole == null)
			throw new DataNotFoundException("Role not found for this name :: " + roleName);
		
		return targetRole;
	}
	
	public Role create(RoleRequest request) {
		if(roleRepository.findByName(request.getName()) != null) throw new DataAlreadyExistException();
		else {
			Role newData = new Role();
			newData.setName(request.getName());
			newData.updateCreated(request.getUser());
			newData.updateLastModified(request.getUser());
			
			final Role createdData = roleRepository.save(newData);
			return createdData;
		}
	}

	public Role update(String roleId, RoleRequest request) {
		Role data = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + roleId));
	
		if(roleRepository.findByNameAndIdNot(request.getName(), roleId) != null) throw new DataAlreadyExistException();
		else {
			data.setName(request.getName());
			data.updateLastModified(request.getUser());
			
			final Role updatedData = roleRepository.save(data);
			return updatedData;
		}
	}
	
	public boolean delete(String roleId) {
		Role data = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + roleId));
	
		if(userRepository.findByRoleId(data.getId()).size() == 0) {
			roleRepository.delete(data);
			
			return true;
		} else throw new InternalServerErrorException("This role still has user!");
	} 
}