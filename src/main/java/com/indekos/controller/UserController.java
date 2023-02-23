package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.AuditableRequest;
import com.indekos.dto.request.ImageUploadRequest;
import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.dto.request.UserUpdateRequest;
import com.indekos.dto.response.Response;
import com.indekos.model.User;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
	private UserService userService;
    
    @GetMapping
    public ResponseEntity getAllUser() {
        return GlobalAcceptions.listData(userService.getAll(), "All User Data");
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
    public ResponseEntity updateUser(@PathVariable String userId, @Valid @RequestBody UserUpdateRequest userRegisterRequest, Errors errors){
        Validated.request(errors);

    	return GlobalAcceptions.data(userService.update(userId, userRegisterRequest),"Success");
    }
    @PutMapping("/image/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody MultipartFile image){
        System.out.println(image);

//        return new ResponseEntity<>(HttpStatus.OK);
        return GlobalAcceptions.data(userService.uploadIdentityCard(userId, image),"Upload Identity Card Success");
    }
    @DeleteMapping("/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable String userId, @Valid @RequestBody AuditableRequest responseBody) {
    	Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", userService.delete(userId, responseBody));
		return response;
    }
}
