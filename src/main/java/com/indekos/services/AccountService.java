package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AccountRegisterRequest;
import com.indekos.dto.request.AccountUpdateRequest;
import com.indekos.model.Account;
import com.indekos.repository.AccountRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AccountRepository accountRepository;
    public Account getByID(String id){
        try {
            return accountRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
    public Account getByUsername(String userName){
        try {
            return accountRepository.findByUsername(userName).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid username");
        }
    }
    public Account register(AccountRegisterRequest accountRegisterRequest){
        Account account = modelMapper.map(accountRegisterRequest, Account.class);
        account.setCreatedBy(accountRegisterRequest.getRequesterIdUser());
        account.setLastModifiedBy(accountRegisterRequest.getRequesterIdUser());
        account.setPassword(Utils.passwordHashing(accountRegisterRequest.getPassword()));
        accountRepository.save(account);
        return account;
    }
    public Account updatePassword(String id, AccountUpdateRequest requestData){
        Account account = getByID(id);
        account.update(requestData.getRequesterIdUser());
        account.setPassword(Utils.passwordHashing(requestData.getPassword()));
        accountRepository.save(account);
        return account;
    }
    public List<Account> allUser(){
        return accountRepository.findAll();
    }
}
