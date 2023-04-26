package com.indekos.services;

import com.indekos.common.helper.exception.InsertDataErrorException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.AccountDTO;
import com.indekos.dto.DataIdDTO;
import com.indekos.dto.request.*;
import com.indekos.model.Account;
import com.indekos.model.ContactAblePerson;
import com.indekos.model.Room;
import com.indekos.controller.repository.ContactAblePersonRepository;
import com.indekos.controller.repository.UserDocumentRepository;
import com.indekos.utils.Constant;
import com.indekos.utils.Utils;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.dto.response.UserResponse;
import com.indekos.model.User;
import com.indekos.model.UserDocument;
import com.indekos.model.UserSetting;
import com.indekos.controller.repository.UserRepository;
import com.indekos.controller.repository.UserSettingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserDocumentRepository userDocumentRepository;
    
    @Autowired
    UserSettingRepository userSettingRepository;
    
    @Autowired
    ContactAblePersonRepository contactAblePersonRepository;
    
    @Autowired
    RoomService roomService;
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    AccountService accountService;

    /* ================================================ USER ACCOUNT ================================================ */
    public Account login(AccountLoginRequest accountLoginRequest) {
    	Account account = accountService.getByUsername(accountLoginRequest.getUsername());
    	if(account == null || account.getUser().isDeleted())
    		throw new InvalidUserCredentialException("User tidak terdaftar");
    	
        if(account.authorized(accountLoginRequest.getPassword())){
        	account.setLoginTime(System.currentTimeMillis());
        	accountService.save(account);
            return account;
        }
        throw new InvalidUserCredentialException("Username atau password tidak valid");
    }
    
    public Account changePassword(AccountChangePasswordRequest request) {
    	User user = getById(request.getRequesterId());
    	Account account = accountService.changePassword(user, request);
        user.update(account.getId());
        save(request.getRequesterId(),user);
        return account;
    }
    
    public Account forgotPassword(AccountForgotPasswordRequest request) {
    	Account account = accountService.forgotPassword(request);
        User user = account.getUser();
        user.update(accountService.getByUsername(request.getUsername()).getUser().getId());
        save("system",user);
        return account;
    }
    
    public Account logout(String userId) {
    	Account account = accountService.getByUser(getById(userId));
        try {
        	account.setLogoutTime(System.currentTimeMillis());
        	accountService.save(account);
        	return account;
		} catch (Exception e) {
			throw e;
		}
    }
    
    /* ==================================================== USER ==================================================== */
    public List<UserResponse> getAll() {
    	List<UserResponse> listResponse =  new ArrayList<>();
    	List<User> users = userRepository.findAllActiveUserOrderByName();
    	users.forEach(user -> {
    		listResponse.add(getUserWithConvertedDocumentImage(user));
    	});
    	return listResponse;
    }

    public List<UserResponse> getAllByRoomId(String roomId){
    	Room room = roomService.getById(roomId).getRoom();
    	List<UserResponse> listUser =  new ArrayList<>();
    	List<User> users = userRepository.findAllByRoomId(room.getId());
    	users.forEach(user -> {
    		listUser.add(getUserWithConvertedDocumentImage(user));
    	});
    	return listUser;
    }
    
    public User getById(String userId){
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("User ID tidak valid"));

    	return user;
    }

    
    public UserResponse register(UserRegisterRequest request) {
    	modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
        	mapper.map(src -> src.getRequesterId(), User::create);
        	mapper.map(src -> System.currentTimeMillis(), User::setJoinedOn);
            mapper.map(src -> null, User::setInActiveSince);
            mapper.map(src -> false, User::setDeleted);

        });
        User user = modelMapper.map(request, User.class);
        user.setIdentityCardImage(Utils.compressImage(request.getIdentityCardImage()));
        user.setSetting(new UserSetting(user));
        user.setRole(roleService.getById(request.getRoleId()));
        
        if (request.getRoomId() == null || ((request.getRoomId()).trim()).equals("")) {
    		if((user.getRole().getName()).equalsIgnoreCase("Tenant")) 
    			throw new InsertDataErrorException("User room id can't be empty");
    		else user.setRoom(null);
		} else {
			if(!isRoomAvailable(request.getRoomId()))
				throw new InsertDataErrorException("Kamar penuh");
			else {
				Room room = roomService.getById(request.getRoomId()).getRoom();
				if(room.getAllotment().equals(Constant.PUTRA) && request.getGender().equals(Constant.PEREMPUAN))
					throw new InsertDataErrorException("Kamar khusus putra");
				else if(room.getAllotment().equals(Constant.PUTRI) && request.getGender().equals(Constant.LAKI_LAKI))
					throw new InsertDataErrorException("Kamar khusus putri");
				else user.setRoom(room);
			}
		} 
        
        save(request.getRequesterId(),user);
        accountService.register(user);
        return getUserWithConvertedDocumentImage(user);
    }
    
    public UserResponse update(String userId, UserRegisterRequest request) {
    	User user = getById(userId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
//           mapper.map(src -> src.getRequesterId(), User::update);
//		   mapper.map(src -> roleService.getById(src.getRoleId()), User::setRole);
        });
        modelMapper.map(request, user);
        if(request.getIdentityCardImage() != null)
        	user.setIdentityCardImage(Utils.compressImage(request.getIdentityCardImage()));
        
        if(request.getRoleId() != null)
        	user.setRole(roleService.getById(request.getRoleId()));
        
        if(request.getRoomId() != null)
        	user.setRoom(roomService.getById(request.getRoomId()).getRoom());
        else {
        	if(!(user.getRole().getName()).equalsIgnoreCase("Tenant")) user.setRoom(null);
        }
        
        save(request.getRequesterId(), user);
        return getUserWithConvertedDocumentImage(user);
    }
    
    public User delete(String userId, AuditableRequest request) {
    	User user = getById(userId);
		user.delete();

        save(request.getRequesterId(), user);
		return user;
    }
    
    /* ================================================ USER DOCUMENT =============================================== */
    public DataIdDTO removeUserDocument(String userDocumentId, String userId) {
    	User user = getById(userId);
    	UserDocument userDocument = userDocumentRepository.findById(userDocumentId)
    		.orElseThrow(() -> new InvalidRequestIdException("Invalid User Document ID"));
    	
    	DataIdDTO deleted = new DataIdDTO();
    	deleted.setId(userDocument.getId());
    	
    	userDocumentRepository.deleteById(userDocument.getId());
    	save(userId, user);
    	
        return deleted;
    }
    
    /* ================================================ USER SETTING ================================================ */
    public UserSetting getSetting(String userId) {
		User user = getById(userId);
		return userSettingRepository.findByUser(user);
	}
    
    public UserSetting updateSetting(String userId, UserSettingsUpdateRequest request) {
		UserSetting userSetting = getSetting(userId);

		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(request, userSetting);

    	final UserSetting updated = userSettingRepository.save(userSetting);
//    	User user = getById(userId);
//    	save(userId,user);
    	
    	return updated;
    }

    /* ========================================== USER CONTACTABLE PERSON =========================================== */
    public List<ContactAblePerson> getContactAblePerson(String userId, String contactableId) {
    	return contactAblePersonRepository.findActiveContactable(userId, contactableId);
    }
    
    public ContactAblePerson addContactAblePerson(String userId, ContactAblePersonCreateRequest request){
    	User user = getById(userId);
    	ContactAblePerson contactAblePerson = modelMapper.map(request, ContactAblePerson.class);
		contactAblePerson.setUser(user);

    	try {
			contactAblePersonRepository.save(contactAblePerson);
		} catch (Exception e) {
			System.out.println(e);
			throw new InsertDataErrorException("Gagal menambahkan data relasi yang dapat dihubungi");
		}
        return contactAblePerson;
    }
    
    public ContactAblePerson editContactAblePerson(String contactAblePersonId, String userId, ContactAblePersonCreateRequest request) {
    	ContactAblePerson contactAblePerson = contactAblePersonRepository.findById(contactAblePersonId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User Contactable Person ID"));
    	
    	modelMapper.map(request, contactAblePerson);
    	
    	final ContactAblePerson updated = contactAblePersonRepository.save(contactAblePerson);
    	User user = getById(userId);
    	save(userId, user);
    	
    	return updated;
    }
    
    public ContactAblePerson deleteContactAblePerson(String contactAblePersonId, String userId) {
    	ContactAblePerson contactAblePerson = contactAblePersonRepository.findById(contactAblePersonId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User Contactable Person ID"));

    	final ContactAblePerson deleted = contactAblePersonRepository.save(contactAblePerson);
    	User user = getById(userId);
    	save(userId, user);

		return contactAblePerson;
    }
    
    /* ==================================================== UTILS ==================================================== */
    private void save(String modifierId, User user){
        try {
			user.update(modifierId);
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
			throw new RuntimeException();
        }
    }
    
    private boolean isRoomAvailable(String roomId) {
    	if(userRepository.findAllByRoomId(roomId).size() == 0) return true;
    	else {
    		if(roomService.isRoomShared(roomId) && !roomService.isRoomFullyBooked(roomId)) return true;
    		else return false;
    	}
    }
    
    private List<UserDocument> getAllUserDocument(User user) {
    	List<UserDocument> documents = userDocumentRepository.findByUser(user);
    	if(documents != null) {
    		List<UserDocument> listConvertedDocument = new ArrayList<>();
			documents.forEach(document -> {
    			UserDocument userDocument = document;
    			userDocument.setImage(Utils.decompressImage(document.getImage()));
        		listConvertedDocument.add(userDocument);
        	});
			return listConvertedDocument;
		}
    	return null;
    }
    
    private UserResponse getUserWithConvertedDocumentImage(User user) { 
    	UserResponse response = new UserResponse();
    	Account account = accountService.getByUser(user);
    	response.setAccount(new AccountDTO(account.getId(), account.getUsername()));
//    	user.setIdentityCardImage(Utils.decompressImage(user.getIdentityCardImage()));
    	response.setUser(user);
    	return response;
    }
    
//	  public User uploadIdentityCard(String userId, MultipartFile[] requests) throws FileSizeLimitExceededException {
//		User user = getById(userId).getUser();
//	    List<byte []> listUserDocumentRequests = new ArrayList<>();
//	    Arrays.asList(requests).stream().forEach(request -> {
//	    	listUserDocumentRequests.add(Utils.compressImage(request));
//	    });
//	    
//	    listUserDocumentRequests.forEach(validRequest -> {
//	    	UserDocument userDocument = new UserDocument();
//	    	userDocument.setIdentityCardImage(validRequest);
//	    	userDocument.setUser(user);
//	    	try {
//				userDocumentRepository.save(userDocument);
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//	    });
//	    user.update(userId);
//	    save(user);
//	    return user;
//	}
}