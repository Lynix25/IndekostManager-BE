package com.indekos.services;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.InternalServerErrorException;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.request.RoomDetailsCreateRequest;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.dto.response.AvailableRoomResponse;
import com.indekos.dto.response.RoomDTO;
import com.indekos.model.MasterRoomDetailCategory;
import com.indekos.model.Room;
import com.indekos.model.RoomDetail;
import com.indekos.model.RoomPriceDetail;
import com.indekos.repository.RoomRepository;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private RoomDetailService roomDetailService;
	
	public List<MasterRoomDetailCategory> getRoomDetailsCategory() {
		return roomDetailService.getRoomDetailCategory();
	}

//	public Room getByUser(User user){
//		List<Room> rooms = roomRepository.findAll();
//
//		for (Room room: rooms) {
//			if(room.hasUser(user)) return room;
//		}
//
//		return null;
//	}
	
	public List<Room> getAll() {
		List<Room> rooms = roomRepository.findAllByOrderByNameAsc();
		return rooms;
	}

	public RoomDTO getById(String roomId){
		Room targetRoom = roomRepository.findById(roomId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Room ID"));
		return getRoomDetail(targetRoom);
	}
	
	public List<AvailableRoomResponse> getAllAvailable(String keyword) {
		return roomRepository.findAllAvailableRoom(keyword);
	}
	
	public List<RoomDetail> getDetailsByRoom(String roomId) {
		Room targetRoom = roomRepository.findById(roomId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Room ID"));
		
		return roomDetailService.getDetailsByRoom(targetRoom);
	}
	
	public List<RoomPriceDetail> getPriceDetailsByRoom(String roomId) {
		Room targetRoom = roomRepository.findById(roomId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Room ID"));
		
		return roomDetailService.getPriceDetailsByRoom(targetRoom);
	}
	
	public RoomDTO create(RoomCreateRequest request){
		
		Room targetRoom = roomRepository.findByName(request.getName());
		if(targetRoom != null) {
			if(targetRoom.isDeleted()) {
				modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
				modelMapper.typeMap(RoomCreateRequest.class, Room.class).addMappings(mapper -> {
					mapper.map(RoomCreateRequest::getRequesterId, Room::update);
				});
				targetRoom.setDeleted(false);
				modelMapper.map(request, targetRoom);
				save(targetRoom);
				return getRoomDetail(targetRoom);
			} else throw new DataAlreadyExistException();
		}
		else {
			modelMapper.typeMap(RoomCreateRequest.class, Room.class).addMappings(mapper -> {
				mapper.map(RoomCreateRequest::getRequesterId, Room::create);
			});
			Room room = modelMapper.map(request, Room.class);
			save(room);
			roomDetailService.initializeDefaultRoomFacility(room);
			return getRoomDetail(room);
		}
	}

	public RoomDTO update(String roomId, RoomCreateRequest request) {
		Room room = getById(roomId).getRoom();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.typeMap(RoomCreateRequest.class, Room.class).addMappings(mapper -> {
			mapper.map(RoomCreateRequest::getRequesterId, Room::update);
		});

		if(roomRepository.findByNameAndIdNot(request.getName(), roomId) != null) throw new DataAlreadyExistException();
		modelMapper.map(request, room);
		save(room);
		
		return getRoomDetail(room);
	}
	
	public RoomDetail addRoomDetail(String roomId, RoomDetailsCreateRequest request){
		Room room = getById(roomId).getRoom();
		RoomDetail newRoomDetail = roomDetailService.addRoomDetail(room, request);
		room.update(request.getRequesterId());
		save(room);
	
		return newRoomDetail;
	}
	
	public RoomPriceDetail addRoomPrice(String roomId, RoomPriceCreateRequest request){
		Room room = getById(roomId).getRoom();
		RoomPriceDetail newRoomPriceDetail = roomDetailService.addRoomPrice(room, request);
		room.update(request.getRequesterId());
		save(room);
		
		return newRoomPriceDetail;
	}
	
	public RoomDetail editRoomDetail(Long roomDetailId, String roomId, RoomDetailsCreateRequest request){
		Room room = getById(roomId).getRoom();
		RoomDetail roomDetail = roomDetailService.editRoomDetail(roomDetailId, request, room);
		room.update(request.getRequesterId());
		save(room);
	
		return roomDetail;
	}
	
	public RoomPriceDetail editRoomPrice(Long roomPriceDetailId, String roomId, RoomPriceCreateRequest request){
		Room room = getById(roomId).getRoom();
		RoomPriceDetail roomPriceDetail = roomDetailService.editRoomPrice(roomPriceDetailId, request, room);
		room.update(request.getRequesterId());
		save(room);
		
		return roomPriceDetail;
	}
	
	public RoomDTO delete(String roomId, String requesterIdUser) {
		Room data = getById(roomId).getRoom();
		if(roomRepository.countCurrentTenantsOfRoom(roomId) == 0) {
			data.setDeleted(true);
			data.update(requesterIdUser);
			roomRepository.save(data);
			return getRoomDetail(data);
		} else throw new InternalServerErrorException("This room is still rented by the tenant!");
	}
	
	public RoomDetail removeRoomDetail(Long roomDetailId, String requesterIdUser, String roomId) {
		Room room = getById(roomId).getRoom();
		RoomDetail roomDetail = roomDetailService.removeRoomDetail(roomDetailId, room);
		room.update(requesterIdUser);
		save(room);
		return roomDetail;
	}
	
	public RoomPriceDetail removeRoomPrice(Long roomPriceDetailId, String requesterIdUser, String roomId) {
		Room room = getById(roomId).getRoom();
		RoomPriceDetail roomPriceDetail = roomDetailService.removeRoomPrice(roomPriceDetailId, room);
		room.update(requesterIdUser);
		save(room);
		return roomPriceDetail;
	}

	public void save(Room room){
		try {
			roomRepository.save(room);
		} catch (DataIntegrityViolationException e){
			System.out.println(e);
			throw new InvalidRequestException("Duplicate Data");
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	public RoomDTO getRoomDetail(Room room) {
		RoomDTO roomDTO = new RoomDTO();
		int tenants = roomRepository.countCurrentTenantsOfRoom(room.getId());
		
		roomDTO.setRoom(room);
		roomDTO.setTotalTenants(tenants);
		if(tenants > 0 && roomRepository.checkIfRoomIsShared(room.getId()) == 0) roomDTO.setStatus("Disewa pribadi");
		else if(room.getQuota() - tenants == 0) roomDTO.setStatus("Penuh");
		else roomDTO.setStatus("Tersedia");
		
		return roomDTO;
	}
	
	public boolean isRoomShared(String roomId) {
		return roomRepository.checkIfRoomIsShared(roomId) > 0 ? true : false;
    }
	
	public boolean isRoomFullyBooked(String roomId) {
		Room room = getById(roomId).getRoom();
		return (room.getQuota() - room.getUsers().size()) <= 0 ? true : false;
	}
}