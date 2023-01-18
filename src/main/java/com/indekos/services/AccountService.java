package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.dto.request.AccountRegisterRequest;
import com.indekos.model.Account;
import com.indekos.repository.AccountRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        account.create(accountRegisterRequest.getRequesterIdUser());
        account.setPassword(Utils.passwordHashing(accountRegisterRequest.getPassword()));
        accountRepository.save(account);
        return account;
    }
    public Account updatePassword(String id, AccountChangePasswordRequest requestData){
        if (requestData.getNewPassword().compareTo(requestData.getReTypeNewPassword()) != 0){
            throw new InvalidRequestException("Miss match retype password", new ArrayList<>());
        }
        Account account = getByID(id);
        if(account.getPassword().compareTo(Utils.passwordHashing(requestData.getOldPassword())) != 0){
            throw new InvalidUserCredentialException("Wrong old password");
        }
        account.update(requestData.getRequesterIdUser());
        account.setPassword(Utils.passwordHashing(requestData.getNewPassword()));
        accountRepository.save(account);
        return account;
    }
    public List<Account> allUser(){
        return accountRepository.findAll();
    }
    public boolean comparePasswordTo(Account account, String anotherPassword){
        if(account.getPassword().compareTo(Utils.passwordHashing(anotherPassword)) == 0){
            return true;
        }
        return false;
    }
}
