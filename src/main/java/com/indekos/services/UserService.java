package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AuditableRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.UserRequest;
import com.indekos.model.User;
import com.indekos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAllActiveUserOrderByName();
    }

    public User getById(String id){
        try {
            return userRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid User ID");
        }
    }

    public User getByAccountId(String accountId){
        try {
            User user = userRepository.findByAccountId(accountId);
            if(user == null) throw new InvalidUserCredentialException("Invalid Account ID");
            return user;
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid Account ID");
        }
    }

    public User register(UserRequest userRequest){
        User user = modelMapper.map(userRequest, User.class);
        user.create(userRequest.getRequesterIdUser());

        user.setDeleted(false);
        user.setJoinedOn(System.currentTimeMillis());
        user.setInactiveSince(System.currentTimeMillis());

        save(user);
        return user;
    }

    private void save(User user){
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public User update(String userId, UserRequest request) {
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        modelMapper.map(request, user);
        user.update(request.getRequesterIdUser());

        save(user);
        return user;
    }
    
    public boolean delete(String userId, AuditableRequest request) {
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    	
    	user.setDeleted(true);
        user.update(request.getRequesterIdUser());
    	userRepository.save(user);
    	
    	return true;
    }
}
