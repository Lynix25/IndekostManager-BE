package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;

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
    UserRepository userRepository;
    public User register(UserRegisterRequest userRegisterRequest){
        User user = new User();
        // System input define
        user.setDeleted(false);
        user.setJoinedOn(Instant.now());

        // Reqeust input define
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPhone(userRegisterRequest.getPhone());
        user.setJob(userRegisterRequest.getJob());
        user.setGender(userRegisterRequest.getGender());
        user.setDescription(userRegisterRequest.getDescription());
        user.setRoleId(userRegisterRequest.getRoleId());
        user.setCreatedBy(userRegisterRequest.getCreatedBy());
        user.setLastModifiedBy(userRegisterRequest.getLastModifiedBy());

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
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public List<User> getAll(){
        return  userRepository.findAll();
    }
}
