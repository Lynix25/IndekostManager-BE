package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.dto.request.AccountRegisterRequest;
import com.indekos.model.Account;
import com.indekos.model.User;
import com.indekos.repository.AccountRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    UserService userService;
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
    private void save(Account account){
        try {
            accountRepository.save(account);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public Account register(AccountRegisterRequest accountRegisterRequest){
//        typeMap.addMappings(mapper -> mapper.<String>map(src -> src.getPerson().getFirstName(), (dest, v) -> dest.getCustomer().setName(v)));
        modelMapper.typeMap(AccountRegisterRequest.class, Account.class).addMappings(mapper -> {
           mapper.map(AccountRegisterRequest::getRequesterIdUser, Account::create);
           mapper.map(src -> {
               return Utils.passwordHashing(accountRegisterRequest.getPassword());
           }, Account::setPassword);
//           Deep Mapping For User
//           mapper.map(src -> false, User::setDeleted);
//           mapper.map(src -> System.currentTimeMillis(), User::setJoinedOn);
//           mapper.map(src -> System.currentTimeMillis(), User::setInactiveSince);
//           mapper.map(src -> src.getRequesterIdUser(), User::create);
//           mapper.map(src -> {return Utils.compressImage(userRegisterRequest.getIdentityCardImage());}, User::setIdentityCardImage);
//           mapper.<Boolean>map(src -> false, (account, b) -> account.getUser().setDeleted(b));
//           mapper.<Long>map(src -> System.currentTimeMillis(), (account, aLong) -> {
//                account.getUser().setJoinedOn(aLong);
//                account.getUser().setInactiveSince(aLong);
//            });
//           mapper.<String>map(AccountRegisterRequest::getRequesterIdUser, (account, o) -> account.getUser().create(o));
//           mapper.<byte[]>map(src -> {return Utils.compressImage(accountRegisterRequest.getUser().getIdentityCardImage());},
//                   (account, bytes) -> account.getUser().setIdentityCardImage(bytes));
        });
        Account account = modelMapper.map(accountRegisterRequest, Account.class);

        save(account);
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
//    public Account addUser(Account account,User user){
//        account.setUser(user);
//        save(account);
//        return account;
//    }
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
