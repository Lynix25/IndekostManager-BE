package com.indekos.services;

import com.indekos.common.helper.exception.InsertDataErrorException;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.*;
import com.indekos.dto.response.UserResponse;
import com.indekos.model.*;
import com.indekos.repository.ContactAblePersonRepository;
import com.indekos.repository.UserDocumentRepository;
import com.indekos.repository.UserRepository;
import com.indekos.repository.UserSettingRepository;
import com.indekos.utils.Utils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

	@Autowired
	RememberMeTokenService rememberMeTokenService;

    /* ================================================ USER ACCOUNT ================================================ */
    public Account login(AccountLoginRequest request) {
    	Account account = request.getToken() != null ? rememberMeTokenService.getById(request.getToken()).getAccount() : accountService.getByUsername(request.getUsername());

		if(request.getToken() != null){
			account.setLoginTime(System.currentTimeMillis());
			accountService.save(account);
			return account;
		}else{
			if(account == null || account.getUser().isDeleted())
				throw new InvalidUserCredentialException("User tidak terdaftar");

			if(account.authorized(request.getPassword())){
				account.setLoginTime(System.currentTimeMillis());
				accountService.save(account);
				return account;
			}
		}
        throw new InvalidUserCredentialException("Username atau password tidak valid");
    }
    
    public Account changePassword(AccountChangePasswordRequest request) {
    	User user = userRepository.findById(request.getRequesterId())
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	
    	Account account = accountService.changePassword(user, request);
        user.update(account.getId());
        
        User updatedUser = save(request.getRequesterId(),user);
        return updatedUser.getAccount();
    }
    
    public Account forgotPassword(AccountForgotPasswordRequest request) {
    	Account account = accountService.forgotPassword(request);
        User user = account.getUser();
        user.update(accountService.getByUsername(request.getUsername()).getUser().getId());
        User updatedUser = save("system", user);
        return updatedUser.getAccount();
    }
    
    public Account logout(String userId) {
    	
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	
    	Account account = accountService.getByUser(user);
        try {
        	account.setLogoutTime(System.currentTimeMillis());
        	accountService.save(account);

			rememberMeTokenService.deleteByAccount(account);

        	return account;
		} catch (Exception e) {
			System.out.println(e);
			throw new  InvalidRequestException(e.getMessage());
		}
    }
    
    /* ==================================================== USER ==================================================== */
    public List<UserResponse> getAll() {
    	List<UserResponse> listResponse =  new ArrayList<>();
    	List<User> users = userRepository.findAllActiveUserOrderByName();
    	System.err.println(users.size());
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

	public List<User> getAllByRoom(Room room){
		List<User> users = userRepository.findAllByRoom(room);
		return users;
	}

	public List<User> getAllByRole(MasterRole role){
		List<User> users = userRepository.findAllByRole(role);
		return users;
	}
    
    public UserResponse getById(String userId){
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("User ID tidak valid"));

    	return getUserWithConvertedDocumentImage(user);
    }
    
    public UserResponse register(UserRegisterRequest request){
    	modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
        	mapper.map(src -> src.getRequesterId(), User::create);
        	mapper.map(src -> System.currentTimeMillis(), User::setJoinedOn);
            mapper.map(src -> null, User::setInActiveSince);
            mapper.map(src -> false, User::setDeleted);

        });
        User user = modelMapper.map(request, User.class);
        user.setIdentityCardImage(Utils.compressImage(request.getIdentityCardImage()));
        user.setSetting(new UserSetting(user));
        user.setRole(roleService.getByName(request.getRole()));
        user.setAccount(accountService.register(user));
//        if (request.getRoom() == null || ((request.getRoom()).trim()).equals("")) {
//    		if((user.getRole().getName()).equalsIgnoreCase("Tenant"))
//    			throw new InsertDataErrorException("User room id can't be empty");
//    		else user.setRoom(null);
//		} else {
//			Room room = roomService.getByName(request.getRoom());
//			if(!isRoomAvailable(room.getId()))
//				throw new InsertDataErrorException("Kamar yang dipilih penuh");
//			else {
//				if(room.getAllotment().equals(Constant.PUTRA) && request.getGender().equals(Constant.PEREMPUAN))
//					throw new InsertDataErrorException("Kamar yang dipilih khusus putra");
//				else if(room.getAllotment().equals(Constant.PUTRI) && request.getGender().equals(Constant.LAKI_LAKI))
//					throw new InsertDataErrorException("Kamar yang dipilih khusus putri");
//				else user.setRoom(room);
//			}
//		}
        
        User newUser = save(request.getRequesterId(),user);
        return getUserWithConvertedDocumentImage(newUser);
    }
    
    public UserResponse update(String userId, UserRegisterRequest request) {
    	User user = getById(userId).getUser();

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(UserRegisterRequest.class, User.class).addMappings(mapper -> {
           mapper.map(src -> src.getRequesterId(), User::update);
        });
        
        modelMapper.map(request, user);
        if(request.getIdentityCardImage() != null)
        	user.setIdentityCardImage(Utils.compressImage(request.getIdentityCardImage()));
        
        if(request.getRole() != null)
        	user.setRole(roleService.getByName(request.getRole()));
        
//        if (request.getRoom() == null || ((request.getRoom()).trim()).equals("")) {
//    		if((user.getRole().getName()).equalsIgnoreCase("Tenant"))
//    			throw new InsertDataErrorException("User room id can't be empty");
//    		else user.setRoom(null);
//		} else {
//			Room room = roomService.getByName(request.getRoom());
//			if(!isRoomAvailable(room.getId()))
//				throw new InsertDataErrorException("Kamar yang dipilih penuh");
//			else {
//				if(room.getAllotment().equals(Constant.PUTRA) && request.getGender().equals(Constant.PEREMPUAN))
//					throw new InsertDataErrorException("Kamar yang dipilih khusus putra");
//				else if(room.getAllotment().equals(Constant.PUTRI) && request.getGender().equals(Constant.LAKI_LAKI))
//					throw new InsertDataErrorException("Kamar yang dipilih khusus putri");
//				else user.setRoom(room);
//			}
//		}
        
        User updatedUser = save(request.getRequesterId(), user);
        return getUserWithConvertedDocumentImage(updatedUser);
    }
    
    public User delete(String userId, String requester) {
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));;
		user.delete();

		User deletedUser = save(requester, user);
		return deletedUser;
    }
    
    /* ================================================ USER DOCUMENT =============================================== */
    public UserDocument removeUserDocument(String userDocumentId, String userId) {
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	UserDocument userDocument = userDocumentRepository.findById(userDocumentId)
    		.orElseThrow(() -> new InvalidRequestIdException("Invalid User Document ID"));
    	
    	userDocumentRepository.deleteById(userDocument.getId());
    	save(userId, user);
    	
        return userDocument;
    }
    
    /* ================================================ USER SETTING ================================================ */
    public UserSetting getSetting(String userId) {
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
		return userSettingRepository.findByUser(user);
	}
    
    public UserSetting updateSetting(String userId, UserSettingsUpdateRequest request) {
		UserSetting userSetting = getSetting(userId);

		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(request, userSetting);

    	final UserSetting updated = userSettingRepository.save(userSetting);
    	
    	return updated;
    }

    /* ========================================== USER CONTACTABLE PERSON =========================================== */
    public List<ContactAblePerson> getContactAblePerson(String userId, String contactableId) {
    	return contactAblePersonRepository.findActiveContactable(userId, contactableId);
    }
    
    public ContactAblePerson addContactAblePerson(String userId, ContactAblePersonCreateRequest request){
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	user.update(userId);
    	ContactAblePerson contactAblePerson = modelMapper.map(request, ContactAblePerson.class);
		contactAblePerson.setUser(user);
		save(userId, user);
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
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	user.update(userId);
    	save(userId, user);
    	
    	return updated;
    }
    
    public ContactAblePerson deleteContactAblePerson(String contactAblePersonId, String userId) {
    	ContactAblePerson contactAblePerson = contactAblePersonRepository.findById(contactAblePersonId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User Contactable Person ID"));

    	contactAblePerson.setDeleted(true);
    	final ContactAblePerson deleted = contactAblePersonRepository.save(contactAblePerson);
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid User ID"));
    	user.update(userId);
    	save(userId, user);

		return deleted;
    }
    
    /* ==================================================== UTILS ==================================================== */
    public User save(String modifierId, User user){
        try {
			user.update(modifierId);
            return userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
			throw new RuntimeException();
        }
        return null;
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
    	response.setAccount(user.getAccount());
    	user.setIdentityCardImage(Utils.decompressImage(user.getIdentityCardImage()));
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