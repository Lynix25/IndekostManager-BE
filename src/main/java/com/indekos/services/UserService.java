package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.MasterServiceDTO;
import com.indekos.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.model.User;
import com.indekos.repository.UserRepository;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    
    public User register(UserRegisterRequest userRegisterRequest){
        User user = modelMapper.map(userRegisterRequest, User.class);

        // System input define
        user.setDeleted(false);
        user.setJoinedOn(Instant.now());

        // Reqeust input define
//        user.setName(userRegisterRequest.getName());
//        user.setEmail(userRegisterRequest.getEmail());
//        user.setPhone(userRegisterRequest.getPhone());
//        user.setJob(userRegisterRequest.getJob());
//        user.setGender(userRegisterRequest.getGender());
//        user.setDescription(userRegisterRequest.getDescription());
//        user.setRoleId(userRegisterRequest.getRoleId());
//        user.setCreatedBy(userRegisterRequest.getCreatedBy());
//        user.setLastModifiedBy(userRegisterRequest.getLastModifiedBy());

        save(user);
        return user;
    }

    public User getByID(String id){
        User user = null;
        try {
            user = userRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid User ID");
        }

        return user;
    }

    private void save(User user){
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(">>> " + e.getMessage());
        }
        catch (Exception e){
            System.out.println(">>> " + e);
        }
    }
    
    public List<User> getAll() {
    	return userRepository.findAllActiveUserOrderByName();
    }
    
    public User update(String userId, UserRegisterRequest request) {
    	User data = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    	
    	data.setName(request.getName());
    	data.setAlias("Alias");
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
