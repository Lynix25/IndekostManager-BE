package com.indekos.controller;

import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.dto.response.Response;
import com.indekos.model.User;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
	private UserService userService;
    
    @GetMapping
    public List<User> getAllUser() {
    	return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable String id){
        User user = userService.getById(id);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody UserRegisterRequest userRegisterRequest, Errors errors){
        Validated.request(errors);
        userService.register(userRegisterRequest);
        return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @Valid @RequestBody UserRegisterRequest userRegisterRequest){
    	final User updatedUser = userService.update(userId, userRegisterRequest);
    	return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable String userId) {
    	Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", userService.delete(userId));
		return response;
    }
}
