package com.indekos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.model.User;
import com.indekos.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User register(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(userRegisterRequest.getName());
        user.setPhone(userRegisterRequest.getNoTelp());
        userRepository.save(user);

        return user;
    }

    public User getByID(String id){
        User user = userRepository.findById(id).get();
        return user;
    }

    public List<User> getAll(){
        return  userRepository.findAll();
    }
}
