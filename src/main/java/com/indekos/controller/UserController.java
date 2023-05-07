package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.InsertDataErrorException;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.dto.UserDTO;
import com.indekos.dto.request.*;
import com.indekos.model.Account;
import com.indekos.model.RememberMeToken;
import com.indekos.model.User;
import com.indekos.services.RememberMeTokenService;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
    @Autowired
	private UserService userService;

    @Autowired
    private RememberMeTokenService rememberMeTokenService;
    
    /* ================================================ USER ACCOUNT ================================================ */
    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody AccountLoginRequest request, Errors errors){
        Validated.request(errors);
        Account account = userService.login(request);
        if(account != null){
            if(request.isRememberMe()){
                RememberMeToken rememberMeToken = rememberMeTokenService.rememberAccount(account);
                return GlobalAcceptions.loginAllowed(account, "Login berhasil dengan Remember Me", rememberMeToken);
            }
            return GlobalAcceptions.loginAllowed(account,"Login Berhasil", null);
        }
        throw new InvalidRequestException("Account Tidak Terdaftar");
    }
    
    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody AccountChangePasswordRequest requestBody, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(userService.changePassword(requestBody), "Berhasil mengubah password. Silakan coba login ulang.");
    }
    
    @PutMapping("/resetpassword")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody AccountForgotPasswordRequest requestBody, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(userService.forgotPassword(requestBody), "Berhasil mengubah password. Silakan coba login ulang.");
    }
    
    @PutMapping("/logout")	// user = userId
    public ResponseEntity<?> logout (@Valid @RequestParam String user){
       Account account = userService.logout(user);
       return GlobalAcceptions.logoutSuccess("Logout berhasil");
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
    public ResponseEntity<?> registerUser(@Valid @RequestParam MultipartFile identityCardImage, @Valid @ModelAttribute UserRegisterRequest request, Errors errors) throws IOException {
    	Validated.request(errors);
    	if(identityCardImage == null) throw new InsertDataErrorException("Mohon unggah foto KTP Anda");
    	request.setIdentityCardImage(identityCardImage);
        return GlobalAcceptions.data(userService.register(request), "Berhasil menambahkan data user");
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @ModelAttribute MultipartFile identityCardImage, @ModelAttribute UserRegisterRequest request) throws FileSizeLimitExceededException {
    	if(identityCardImage != null) request.setIdentityCardImage(identityCardImage);
    	User user = userService.update(userId, request).getUser();
    	UserDTO userDTO = new UserDTO(user, user.getRoom());
    	return GlobalAcceptions.data(userDTO, "Berhasil memperbaharui profil");
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId, @Valid @RequestBody AuditableRequest request) {
        return GlobalAcceptions.data(userService.delete(userId, request), "Berhasil menghapus data user");
    }
    
    /* ================================================ USER DOCUMENT =============================================== */
    @DeleteMapping("/{userId}/document")	// document = userDocumentId
    public ResponseEntity<?> removeUserDocument(@RequestParam String document, @PathVariable String userId) {
    	return GlobalAcceptions.data(userService.removeUserDocument(document, userId), "Berhasil menghapus dokumen user");
    }
    
    /* ================================================ USER SETTING ================================================ */
    @GetMapping("/{userId}/settings")
    public ResponseEntity<?> getUserSettings(@PathVariable String userId){
        return GlobalAcceptions.data(userService.getSetting(userId), "User Setting Data");
    }
    
    /* User setting auto added when user registered */
    
    @PutMapping("/{userId}/settings") // type = settingType; value = true/false
    public ResponseEntity<?> updateUserSetting(@PathVariable String userId, @RequestBody UserSettingsUpdateRequest request) {
    	return GlobalAcceptions.data(userService.updateSetting(userId, request), "Berhasil memperbaharui pengaturan");
    }

    /* ========================================== USER CONTACTABLE PERSON =========================================== */
    @GetMapping("/{userId}/contactable")	// person = contactablePersonId
    public ResponseEntity<?> getActiveContactAblePerson(@RequestParam String person, @PathVariable String userId) {
    	return GlobalAcceptions.listData(userService.getContactAblePerson(userId, person), "All Active Contactable Person");
    }
    
    @PostMapping("/{userId}/contactable")
    public ResponseEntity<?> addContactAblePerson(@PathVariable String userId, @Valid @RequestBody ContactAblePersonCreateRequest request, Errors errors) {
        Validated.request(errors);
        return GlobalAcceptions.data(userService.addContactAblePerson(userId, request), "Berhasil menambahkan kontak alternatif");
    }
    
    @PutMapping("/{userId}/contactable")	// person = contactablePersonId
    public ResponseEntity<?> updateContactAblePerson(@RequestParam String person, @PathVariable String userId, @Valid @RequestBody ContactAblePersonCreateRequest request) {
        return GlobalAcceptions.data(userService.editContactAblePerson(person, userId, request), "Berhasil memperbaharui kontak alternatif");
    }
    
    @DeleteMapping("/{userId}/contactable")	// person = contactablePersonId
    public ResponseEntity<?> deleteContactAblePerson(@RequestParam String person, @PathVariable String userId) {
        return GlobalAcceptions.data(userService.deleteContactAblePerson(person, userId), "Berhasil menghapus kontak alternatif");
    }
}