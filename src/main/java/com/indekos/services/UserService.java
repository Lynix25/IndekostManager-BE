package com.indekos.services;

import com.indekos.common.helper.exception.BadRequestException;
import com.indekos.dto.UserSettingsDTO;
import com.indekos.dto.request.AuditableRequest;
import com.indekos.dto.request.ContactAblePersonCreateRequest;
import com.indekos.model.ContactAblePerson;
import com.indekos.repository.RoomRepository;
import com.indekos.utils.Utils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.model.User;
import com.indekos.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    public List<User> getAll() {
        return userRepository.findAllActiveUserOrderByName();
    }

    public List<User> getAllByRoomId(String roomId){
        return userRepository.findAllByRoomId(roomId);
    }
    public User getById(String id){
        try {
            User user = userRepository.findById(id).get();
            user.setIdentityCardImage(Utils.decompressImage(user.getIdentityCardImage()));
            return user;
        }catch (NoSuchElementException e){
            throw new ResourceNotFoundException("User not found for this id :: " + id);
        }
    }

//    public User getByAccountId(String accountId){
//        try {
//            User user = userRepository.findByAccountId(accountId);
//            if(user == null) throw new InvalidUserCredentialException("Invalid Account ID");
//            return user;
//        }catch (NoSuchElementException e){
//            throw new InvalidUserCredentialException("Invalid Account ID");
//        }
//    }

    public User register(UserRegisterRequest userRegisterRequest){
        modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
            mapper.map(src -> false, User::setDeleted);
            mapper.map(src -> System.currentTimeMillis(), User::setJoinedOn);
            mapper.map(src -> System.currentTimeMillis(), User::setInactiveSince);
            mapper.map(src -> src.getRequesterIdUser(), User::create);
//            mapper.map(src -> {return Utils.compressImage(userRegisterRequest.getIdentityCardImage());}, User::setIdentityCardImage);
        });

        User user = modelMapper.map(userRegisterRequest, User.class);
        user.setIdentityCardImage(Utils.compressImage(userRegisterRequest.getIdentityCardImage()));
        save(user);
        return user;
    }

    public User addContactAblePesson(String id,ContactAblePersonCreateRequest request){
        User user = getById(id);
        ContactAblePerson contactAblePerson = modelMapper.map(request, ContactAblePerson.class);

        user.getContactAblePeople().add(contactAblePerson);

        save(user);
        return user;
    }

    private void save(User user){
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public User update(String userId, UserRegisterRequest request) {
    	User user = getById(userId);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
           mapper.map(UserRegisterRequest::getRequesterIdUser, User::update);
        });

        modelMapper.map(request, user);
//        user.update(request.getRequesterIdUser());
        save(user);
        return user;
    }

    public User uploadIdentityCard(String userId, MultipartFile request){
        User user = getById(userId);

        user.setIdentityCardImage(Utils.compressImage(request));
        user.update("System By Pass");
        save(user);
        return user;
    }
    
    public boolean delete(String userId, AuditableRequest request) {
    	User user = getById(userId);
    	
    	user.setDeleted(true);
        user.update(request.getRequesterIdUser());

    	userRepository.save(user);

    	return true;
    }

    public UserSettingsDTO getSettings(String id){
        User user = getById(id);

        UserSettingsDTO userSettings = modelMapper.map(user, UserSettingsDTO.class);

        return userSettings;
    }
}
