package com.indekos.services;

import com.indekos.common.helper.exception.InsertDataErrorException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.AccountDTO;
import com.indekos.dto.DataIdDTO;
import com.indekos.dto.request.*;
import com.indekos.model.Account;
import com.indekos.model.ContactAblePerson;
import com.indekos.model.Room;
import com.indekos.repository.ContactAblePersonRepository;
import com.indekos.repository.UserDocumentRepository;
import com.indekos.utils.Constant;
import com.indekos.utils.Utils;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.indekos.dto.response.UserDocumentResponse;
import com.indekos.dto.response.UserResponse;
import com.indekos.model.User;
import com.indekos.model.UserDocument;
import com.indekos.model.UserSetting;
import com.indekos.repository.UserRepository;
import com.indekos.repository.UserSettingRepository;

import org.springframework.web.multipart.MultipartFile;

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
    
    public Account logout(String accountId) {
    	Account account = accountService.getById(accountId);
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
    		listResponse.add(getUserWithConvertedDocumentImage(user, null, Constant.DECOMPRESS_MODE));
    	});
    	return listResponse;
    }

    public List<UserResponse> getAllByRoomId(String roomId){
    	Room room = roomService.getById(roomId).getRoom();
    	List<UserResponse> listUser =  new ArrayList<>();
    	List<User> users = userRepository.findAllByRoomId(room.getId());
    	users.forEach(user -> {
    		listUser.add(getUserWithConvertedDocumentImage(user, null, Constant.DECOMPRESS_MODE));
    	});
    	return listUser;
    }
    
    public User getById(String userId){
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	
//    	return getUserWithConvertedDocumentImage(user, null, Constant.DECOMPRESS_MODE);
		return user;
    }

    
    public UserResponse register(UserRegisterRequest request) {
    	modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
        	mapper.map(src -> src.getRequesterId(), User::create);
			mapper.map(UserRegisterRequest::getRoleId, User::setRole);
        	mapper.map(src -> System.currentTimeMillis(), User::setJoinedOn);
            mapper.map(src -> null, User::setInActiveSince);
            mapper.map(src -> false, User::setDeleted);

        });
        User user = modelMapper.map(request, User.class);
        user.setSetting(new UserSetting(user));
//        user.setRole(roleService.getByRoleId(request.getRoleId()));
        
        if (request.getRoomId() == null || ((request.getRoomId()).trim()).equals("")) {
    		if((user.getRole().getName()).equalsIgnoreCase("Tenant")) 
    			throw new InsertDataErrorException("User room id can't be empty");
    		else user.setRoom(null);
		} else {
			if(!isRoomAvailable(request.getRoomId()))
				throw new InsertDataErrorException("Selected room is fully booked!");
			else {
				Room room = roomService.getById(request.getRoomId()).getRoom();
				if(room.getAllotment().equals(Constant.PUTRA) && request.getGender().equals(Constant.PEREMPUAN))
					throw new InsertDataErrorException("Selected room is for men only");
				else if(room.getAllotment().equals(Constant.PUTRI) && request.getGender().equals(Constant.LAKI_LAKI))
					throw new InsertDataErrorException("Selected room is for women only");
				else user.setRoom(room);
			}
		} 
        
        save(request.getRequesterId(),user);
        accountService.register(user);
        return getUserWithConvertedDocumentImage(user, request.getUserDocument(), Constant.COMPRESS_MODE);
    }
    
    public UserResponse update(String userId, UserRegisterRequest request) {
    	User user = getById(userId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
//           mapper.map(src -> src.getRequesterId(), User::update);
		   mapper.map(src -> roleService.getById(src.getRoleId()), User::setRole);
        });
        modelMapper.map(request, user);
        
        if (request.getRoomId() == null || ((request.getRoomId()).trim()).equals("")) {
    		if((user.getRole().getName()).equalsIgnoreCase("Tenant")) 
    			throw new InsertDataErrorException("user room id can't be empty");
    		else user.setRoom(null);
		} else user.setRoom(roomService.getById(request.getRoomId()).getRoom());  
        
        save(request.getRequesterId(), user);
        return getUserWithConvertedDocumentImage(user, request.getUserDocument(), Constant.COMPRESS_MODE);
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
    public List<ContactAblePerson> getContactAblePerson(String userId) {
    	User user = getById(userId);
    	return contactAblePersonRepository.findByUserAndIsDeleted(user, false);
    }
    
    public ContactAblePerson addContactAblePerson(String userId, ContactAblePersonCreateRequest request){
    	User user = getById(userId);
    	ContactAblePerson contactAblePerson = modelMapper.map(request, ContactAblePerson.class);
		contactAblePerson.setUser(user);
		user.getContactAblePersons().add(contactAblePerson);

    	try {
			save(userId, user);
		} catch (Exception e) {
			System.out.println(e);
			throw new InsertDataErrorException("Failed add contactable person");
		}
//        user.update(userId);
//        save(userId,user);
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
    	
    	contactAblePerson.setDeleted(true);
    	final ContactAblePerson deleted = contactAblePersonRepository.save(contactAblePerson);
    	User user = getById(userId);
    	save(userId,user);

    	return deleted;
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
    
    private UserResponse getUserWithConvertedDocumentImage(User user, List<MultipartFile> documents, String convertMode) {
    	if(convertMode.equals(Constant.COMPRESS_MODE)) {
    		if(documents != null) {
    			documents.forEach(document -> {
        			UserDocument userDocument = new UserDocument();
            		userDocument.setImage(Utils.compressImage(document));
            		userDocument.setUser(user);
            		userDocumentRepository.save(userDocument);
            	});
    		}
    	}
    	
    	UserResponse response = new UserResponse();
    	response.setUser(user);
    	
    	Account account = accountService.getByUser(user);
    	response.setAccount(new AccountDTO(account.getId(), account.getUsername()));
    	
    	List<UserDocumentResponse> listUserDocumentConverted = new ArrayList<>();
    	List<UserDocument> listUserDocument = userDocumentRepository.findByUser(user);
    	listUserDocument.forEach(document -> {
			UserDocumentResponse userDocument = new UserDocumentResponse();
			userDocument.setId(document.getId());
			userDocument.setIdentityCardImage(Utils.decompressImage(document.getImage()));
			listUserDocumentConverted.add(userDocument);
    	});
		response.setUserDocuments(listUserDocumentConverted);
    	
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