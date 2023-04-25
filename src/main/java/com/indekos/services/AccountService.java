package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.dto.request.AccountForgotPasswordRequest;
import com.indekos.dto.request.AccountLoginRequest;
import com.indekos.model.Account;
import com.indekos.model.User;
import com.indekos.repository.AccountRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
	
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    AccountRepository accountRepository;
    
    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    
    public Account getById(String accountId){
        Account account = accountRepository.findById(accountId)
        		.orElseThrow(() -> new InvalidRequestIdException("Invalid Account ID"));
    
        return account;
    }
    
    public Account getByUsername(String username){
    	Account account = accountRepository.findByUsername(username);
    	return account;
    }
    
    public Account getByUser(User user) {
    	Account account = accountRepository.findByUser(user);
    	if(account == null)
    		throw new InvalidRequestIdException("This user doesn't have an account. Please contact owner/ admin.");
    	
    	return account;
    }
    
    public Account register(User user){
    	String credential = createCredential(user);
        modelMapper.typeMap(User.class, Account.class).addMappings(mapper -> {
           mapper.map(src -> credential, Account::setUsername);
           mapper.map(src -> Utils.passwordHashing(credential), Account::setPassword);
           mapper.map(src -> {return user;}, Account::setUser);
        });
        Account account = modelMapper.map(user, Account.class);
        save(account);
        return account;
    }

    public Account login(AccountLoginRequest request) {
        Account account = getByUsername(request.getUsername());

        if(account.authorized(request.getPassword())){
            account.setLoginTime(System.currentTimeMillis());
            save(account);

            return account;
        }

        throw new InvalidUserCredentialException("Username atau password tidak valid");
    }

    public Account changePassword(User user, AccountChangePasswordRequest request){
    	Account account = getByUser(user);

        if(!account.authorized(request.getOldPassword()))
            throw new InvalidUserCredentialException("Password lama salah");

        if (request.getNewPassword().compareTo(request.getReTypeNewPassword()) != 0)
            throw new InvalidRequestException("Password baru dan konfirmasi tidak sesuai", new ArrayList<>());

        if(request.getNewPassword().length() < 8 || !Utils.isAlphaNumeric(request.getNewPassword()))
        	throw new InvalidRequestException("Panjang password harus minimal 8 karakter dalam alfanumerik");

        account.setPassword(Utils.passwordHashing(request.getNewPassword()));
        save(account);
        
        return account;
    }
    
    public Account forgotPassword(AccountForgotPasswordRequest requestData){
    	Account account = getByUsername(requestData.getUsername());
    	
    	if (requestData.getNewPassword().compareTo(requestData.getReTypeNewPassword()) != 0)
            throw new InvalidRequestException("Password baru dan konfirmasi tidak sesuai", new ArrayList<>());

        if(requestData.getNewPassword().length() < 8 || !Utils.isAlphaNumeric(requestData.getNewPassword()))
            throw new InvalidRequestException("Panjang password harus minimal 8 karakter dalam alfanumerik");

        account.setPassword(Utils.passwordHashing(requestData.getNewPassword()));
        save(account);
        return account;
    }
    
    public String createCredential(User user){
    	/* Default: username = password = alphanumeric generated lowercase(firstname) + 3 digits random number */
    	/*  */
    	String[] splittedName = (user.getName()).split("\\s+");
    	String chosenName = "";
    	for (String name : splittedName) {
			if(name.length() > 3) {
				chosenName = name;
				break;
			}
		}
    	
    	if(chosenName.equals("")) chosenName = "user";
    	
    	String credential = "";
    	while (credential.equals("") || accountRepository.findByUsername(credential) != null) {
    		credential = (chosenName).toLowerCase() + String.valueOf((int)(Math.random() * (999 - 100) + 100));
		}
    	return credential;
    }
    
    public boolean comparePasswordTo(Account account, String anotherPassword){
        if(account.getPassword().compareTo(Utils.passwordHashing(anotherPassword)) == 0){
            return true;
        }
        return false;
    }
    
    public void save(Account account){
        try {
            accountRepository.save(account);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
            throw new InvalidRequestException("Duplicate Data");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}