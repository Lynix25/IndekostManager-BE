package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.DataIdDTO;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.dto.request.AccountLoginRequest;
import com.indekos.model.Account;
import com.indekos.services.AccountService;
import com.indekos.utils.Validated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    
	@Autowired
    private AccountService accountService;
    
	@Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllAccount(){
        return GlobalAcceptions.listData(accountService.getAll(), "All User Account Data");
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable String accountId) {
    	return GlobalAcceptions.data(accountService.getById(accountId), "User Account Data");
    }
    
    /* Account autocreate when user registered by owner */
    
    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody AccountLoginRequest accountLoginRequest, Errors errors){
        Validated.request(errors);
        Account account = accountService.getByUsername(accountLoginRequest.getUsername());
        if(accountService.comparePasswordTo(account, accountLoginRequest.getPassword())){
            return GlobalAcceptions.loginAllowed(account.getUser(), "Success Login");
        }
        throw new InvalidUserCredentialException("Invalid username or password");
    }
    
    @PutMapping("/{accountId}")
    public ResponseEntity<?> changePassword(@PathVariable String accountId, @Valid @RequestBody AccountChangePasswordRequest requestBody, Errors errors){
        Validated.request(errors);
        
        DataIdDTO data = modelMapper.map(accountService.changePassword(accountId,requestBody), DataIdDTO.class);
        return GlobalAcceptions.data(data, "Success changes password");
    }
}