package com.indekosservice.service;

import com.indekosservice.dto.request.AccountLinkRequset;
import com.indekosservice.dto.request.AccountRegisterRequest;
import com.indekosservice.model.Account;
import com.indekosservice.repository.AccountRepository;
import com.indekosservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public Account getByUsername(String userName){
        Optional<Account> account = accountRepository.findByUsername(userName);
        return account.get();
    }

    public Account register(AccountRegisterRequest accountRegisterRequest){
        Account account = new Account();
        account.setAccoutID(UUID.randomUUID().toString());
        account.setUsername(accountRegisterRequest.getUsername());
        account.setPassword(Utils.passwordHashing(accountRegisterRequest.getPassword()));
        System.out.println(account.getAccoutID());
        accountRepository.save(account);
        return account;
    }

    public Account linkUser(AccountLinkRequset accountLinkRequset){
        Account account = accountRepository.findById(accountLinkRequset.getAccountID()).get();
        account.setUserID(accountLinkRequset.getUserID());
        accountRepository.save(account);
        return account;
    }

    public List<Account> allUser(){
        return accountRepository.findAll();
    }
}
