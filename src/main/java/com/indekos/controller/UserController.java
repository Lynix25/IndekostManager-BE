package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.UserDTO;
import com.indekos.dto.UserSettingsDTO;
import com.indekos.dto.request.*;
import com.indekos.dto.response.Response;
import com.indekos.model.Room;
import com.indekos.model.User;
import com.indekos.services.RoomService;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
	private UserService userService;
    @Autowired
    private RoomService roomService;
    
    @GetMapping
    public ResponseEntity getAllUser() {
        return GlobalAcceptions.listData(userService.getAll(), "All User Data");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable String id){
        User user = userService.getById(id);
        Room room = roomService.getByUser(user);

        return ResponseEntity.ok().body(new UserDTO(user, room));
    }
    @GetMapping("/{id}/settings")
    public ResponseEntity getUserSettings(@PathVariable String id){
        UserSettingsDTO userSettings = userService.getSettings(id);
        return new ResponseEntity<>(userSettings,HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity register(@Valid @RequestBody UserRegisterRequest userRegisterRequest, Errors errors){
//        Validated.request(errors);
//
//        userService.register(userRegisterRequest);
//
//        return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK);
//    }
//            @RequestHeader(value = "Requester-ID") String requesterId, @RequestHeader HttpHeaders header,
    @PostMapping
    public ResponseEntity registerV2(
//            @RequestHeader(value = "Requester-ID") String requesterId, @RequestHeader HttpHeaders header,
            @ModelAttribute MultipartFile identityCardImage , @Valid @ModelAttribute UserRegisterRequest requestBody, Errors errors){
        Validated.request(errors);
        requestBody.setIdentityCardImage(identityCardImage);

        User user = userService.register(requestBody);

//        return new ResponseEntity(user, HttpStatus.OK);
        return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK);
    }
    @PostMapping("/{id}/contact-able-person")
    public ResponseEntity addContactAblePerson(@PathVariable String id,@Valid @RequestBody ContactAblePersonCreateRequest requestBody, Errors errors){
        Validated.request(errors);

        userService.addContactAblePesson(id,requestBody);

        return GlobalAcceptions.emptyData("Success add contactable person");
    }
    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserRegisterRequest requestBody){

    	return GlobalAcceptions.data(userService.update(userId, requestBody),"Success");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId, @Valid @RequestBody AuditableRequest requestBody) {
        userService.delete(userId, requestBody);
        return GlobalAcceptions.emptyData("Succes delete user : " + userId);
    }
}
