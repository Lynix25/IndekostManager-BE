package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.InsertDataErrorException;
import com.indekos.dto.UserDTO;
import com.indekos.dto.request.*;
import com.indekos.model.User;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    /* ================================================ USER ACCOUNT ================================================ */
    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody AccountLoginRequest accountLoginRequest, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.loginAllowed(userService.login(accountLoginRequest), "Login berhasil");
    }
    
    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody AccountChangePasswordRequest requestBody, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(userService.changePassword(requestBody), "Berhasil mengubah password");
    }
    
    @PutMapping("/resetpassword")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody AccountForgotPasswordRequest requestBody, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(userService.forgotPassword(requestBody), "Berhasil mengubah password");
    }
    
    @PutMapping("/logout")	// user = userId
    public ResponseEntity<?> logout (@Valid @RequestParam String user){
       return GlobalAcceptions.data(userService.logout(user), "Logout berhasil");
    }
    
    /* ==================================================== USER ==================================================== */
    @GetMapping	// room = roomId
    public ResponseEntity<?> getAllUser(@RequestParam String room) {
    	if(room.length() == 0)	// Get All
    		return GlobalAcceptions.listData(userService.getAll(), "All User Data");
    	else	// Get All By Room
    		return GlobalAcceptions.listData(userService.getAllByRoomId(room), "All User Data");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        User user = userService.getById(userId);
        UserDTO userDTO = new UserDTO(user, user.getRoom());
    	return GlobalAcceptions.data(userDTO, "User Data");
    }
    
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestParam MultipartFile identityCard, @Valid @ModelAttribute UserRegisterRequest request, Errors errors) throws FileSizeLimitExceededException {
    	Validated.request(errors);
    	if(identityCard == null) throw new InsertDataErrorException("Mohon unggah foto KTP Anda");
    	request.setIdentityCard(identityCard);
        return GlobalAcceptions.data(userService.register(request), "Created User Data");
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @Valid @RequestParam MultipartFile identityCard, @Valid @ModelAttribute UserRegisterRequest request) throws FileSizeLimitExceededException {
    	if(identityCard == null) throw new InsertDataErrorException("Mohon unggah foto KTP Anda");
    	request.setIdentityCard(identityCard);
    	return GlobalAcceptions.data(userService.update(userId, request), "Updated User Data");
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId, @Valid @RequestBody AuditableRequest request) {
        return GlobalAcceptions.data(userService.delete(userId, request), "Deleted User Data");
    }
    
    /* ================================================ USER DOCUMENT =============================================== */
    @DeleteMapping("/{userId}/document")	// document = userDocumentId
    public ResponseEntity<?> removeUserDocument(@RequestParam String document, @PathVariable String userId) {
    	return GlobalAcceptions.data(userService.removeUserDocument(document, userId), "Deleted User Document");
    }
    
    /* ================================================ USER SETTING ================================================ */
    @GetMapping("/{userId}/settings")
    public ResponseEntity<?> getUserSettings(@PathVariable String userId){
        return GlobalAcceptions.data(userService.getSetting(userId), "User Setting Data");
    }
    
    /* User setting auto added when user registered */
    
    @PutMapping("/{userId}/settings") // type = settingType; value = true/false
    public ResponseEntity<?> updateUserSetting(@PathVariable String userId, @RequestBody UserSettingsUpdateRequest request) {
    	return GlobalAcceptions.data(userService.updateSetting(userId, request), "Updated User Setting Data");
    }

    /* ========================================== USER CONTACTABLE PERSON =========================================== */
    @GetMapping("/{userId}/contactable")
    public ResponseEntity<?> getActiveContactAblePerson(@PathVariable String userId) {
    	return GlobalAcceptions.listData(userService.getContactAblePerson(userId), "All Active Contactable Person");
    }
    
    @PostMapping("/{userId}/contactable")
    public ResponseEntity<?> addContactAblePerson(@PathVariable String userId, @Valid @RequestBody ContactAblePersonCreateRequest request, Errors errors) {
        Validated.request(errors);
        return GlobalAcceptions.data(userService.addContactAblePerson(userId, request), "Created Contactable Person Data");
    }
    
    @PutMapping("/{userId}/contactable")	// person = contactablePersonId
    public ResponseEntity<?> updateContactAblePerson(@RequestParam String person, @PathVariable String userId, @Valid @RequestBody ContactAblePersonCreateRequest request) {
        return GlobalAcceptions.data(userService.editContactAblePerson(person, userId, request), "Updated Contactable Person Data");
    }
    
    @DeleteMapping("/{userId}/contactable")	// person = contactablePersonId
    public ResponseEntity<?> deleteContactAblePerson(@RequestParam String person, @PathVariable String userId) {
        return GlobalAcceptions.data(userService.deleteContactAblePerson(person, userId), "Deleted Contactable Person Data");
    }
}