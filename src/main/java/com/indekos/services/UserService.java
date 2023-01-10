package com.indekos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.model.User;
import com.indekos.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
	
    @Autowired
    UserRepository userRepository;
    
    public List<User> getAll() {
    	return userRepository.findAllActiveUserOrderByName();
    }
    
    public User register(UserRegisterRequest request){
        
    	User user = new User();
    	user.setDeleted(false);
    	user.setName(request.getName());
    	user.setAlias(request.getAlias());
    	user.setEmail(request.getEmail());
    	user.setPhone(request.getPhone());
    	user.setJob(request.getJob());
    	user.setGender(request.getGender());
    	user.setDescription(request.getDescription());
    	user.setJoinedOn(Instant.now());
    	user.setRoleId(request.getRoleId());
    	user.setRoomId(request.getRoomId());
    	user.setAccountId(request.getAccountId());
    	user.updateCreated(request.getUser());
    	user.updateLastModified(request.getUser());
    	
    	final User createdData = userRepository.save(user);
        return createdData;
    }
    
    public User update(String userId, UserRegisterRequest request) {
    	User data = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    	
    	data.setName(request.getName());
    	data.setAlias(request.getAlias());
    	data.setEmail(request.getEmail());
    	data.setPhone(request.getPhone());
    	data.setJob(request.getJob());
    	data.setGender(request.getGender());
    	data.setDescription(request.getDescription());
    	data.setRoleId(request.getRoleId());
    	data.setRoomId(request.getRoomId());
    	data.setAccountId(request.getAccountId());
    	data.updateLastModified(request.getUser());
    	
    	final User updatedData = userRepository.save(data);
        return updatedData;
    }
    
    public boolean delete(String userId) {
    	User data = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    	
    	data.setDeleted(true);
    	userRepository.save(data);
    	
    	return true;
    }
}
