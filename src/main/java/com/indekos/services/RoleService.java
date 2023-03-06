package com.indekos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.DataNotFoundException;
import com.indekos.common.helper.exception.InternalServerErrorException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.RoleRequest;
import com.indekos.model.MasterRole;
import com.indekos.repository.RoleRepository;
import com.indekos.repository.UserRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	public List<MasterRole> getAll() {
		return roleRepository.findAllActiveByOrderByNameAsc();
	}
	
	public MasterRole getByName(String roleName) {
		MasterRole targetMasterRole = roleRepository.findByName(roleName);
		if(targetMasterRole == null)
			throw new DataNotFoundException("Role not found for this name :: " + roleName);
		
		return targetMasterRole;
	}
	
	public MasterRole create(RoleRequest request) {
		MasterRole targetMasterRole = roleRepository.findByName(request.getName());
		if(targetMasterRole != null) {
			if(targetMasterRole.isDeleted()) {
				targetMasterRole.setDeleted(false);
				targetMasterRole.setDescription(request.getDescription());
				targetMasterRole.update(request.getRequesterIdUser());
				
				final MasterRole createdData = roleRepository.save(targetMasterRole);
				return createdData;
			} else throw new DataAlreadyExistException();
		}
		else {
			MasterRole newData = new MasterRole();
			newData.setName(request.getName());
			newData.setDescription(request.getDescription());
			newData.setDeleted(false);
			newData.update(request.getRequesterIdUser());
			
			final MasterRole createdData = roleRepository.save(newData);
			return createdData;
		}
	}

	public MasterRole update(String roleId, RoleRequest request) {
		MasterRole data = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + roleId));
	
		if(roleRepository.findByNameAndIdNot(request.getName(), roleId) != null) throw new DataAlreadyExistException();
		else {
			data.setName(request.getName());
			data.setDescription(request.getDescription());
			data.update(request.getRequesterIdUser());
			
			final MasterRole updatedData = roleRepository.save(data);
			return updatedData;
		}
	}
	
	public boolean delete(String roleId) {
		MasterRole data = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + roleId));
	
		if(userRepository.findByRoleId(data.getId()).size() == 0) {
			data.setDeleted(true);
			roleRepository.save(data);
			
			return true;
		} else throw new InternalServerErrorException("This role still has user!");
	} 
}