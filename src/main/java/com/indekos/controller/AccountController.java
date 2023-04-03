package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    
	@Autowired
    private AccountService accountService;
    
	@GetMapping
    public ResponseEntity<?> getAllAccount(){
        return GlobalAcceptions.listData(accountService.getAll(), "All User Account Data");
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable String accountId) {
    	return GlobalAcceptions.data(accountService.getById(accountId), "User Account Data");
    }
    
    /* Account autocreate when user registered by owner */
}
