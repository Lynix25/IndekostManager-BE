package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.UserRegisterRequest;
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
            return userRepository.findByAccountId(accountId);
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid Account ID");
        }
    }

    public User register(UserRegisterRequest userRegisterRequest){
//                modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
//                    mapper.map(src -> false,
//                            User::setDeleted);
//                });
        User user = modelMapper.map(userRegisterRequest, User.class);

        user.setCreatedBy(userRegisterRequest.getRequesterIdUser());
        user.setLastModifiedBy(userRegisterRequest.getRequesterIdUser());

        user.setDeleted(false);
        user.setJoinedOn(Instant.now());
        user.setInactiveSince(Instant.now());

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
    
    public User update(String userId, UserRegisterRequest request) {
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user = modelMapper.map(request, user.getClass());
        user.update(request.getRequesterIdUser());
//        modelMapper.typeMap(UserRegisterRequest.class, user.getClass()).addMappings(mapper -> {
//                    mapper.map(src -> src.getBillingAddress().getStreet(),
//                            Destination::setBillingStreet);
//                    mapper.map(src -> src.getBillingAddress().getCity(),
//                            Destination::setBillingCity);
//                });
        save(user);
        return user;
    }
    
    public boolean delete(String userId) {
    	User data = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    	
    	data.setDeleted(true);
    	userRepository.save(data);
    	
    	return true;
    }
}
