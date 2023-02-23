package com.indekos.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.RoomDetailCreateRequest;
import com.indekos.model.RoomDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.InternalServerErrorException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.response.RoomResponse;
import com.indekos.model.Room;
import com.indekos.repository.RoomRepository;

@Service
public class RoomService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private RoomRepository roomRepository;
	
	public List<Room> getAll() {
		return roomRepository.findAllByOrderByNameAsc();
	}

	public Room getById(String id){
		try {
			return roomRepository.findById(id).get();
		}catch (NoSuchElementException e){
			throw new InvalidUserCredentialException("Invalid Room ID");
		}
	}
	
	public List<RoomResponse> getAllAvailable(String keyword) {
		return roomRepository.findAllAvailableRoom(keyword);
	}
	
//	public MasterRoom create(RoomCreateRequest request) {
//		MasterRoom targetMasterRoom =roomRepository.findByName(request.getName());
//		if(targetMasterRoom != null) {
//			if(targetMasterRoom.isDeleted()) {
//				targetMasterRoom.setDeleted(false);
//				targetMasterRoom.setName(request.getName());
//				targetMasterRoom.setDescription(request.getDescription());
//				targetMasterRoom.setQuota(request.getQuota());
//				targetMasterRoom.update(request.getRequesterIdUser());
//
//				final MasterRoom createdData = roomRepository.save(targetMasterRoom);
//				return createdData;
//			} else throw new DataAlreadyExistException();
//		}
//		else {
//			MasterRoom masterRoom = modelMapper.map(request, MasterRoom.class);
//			masterRoom.create(request.getRequesterIdUser());
//			final MasterRoom createdData = roomRepository.save(masterRoom);
//			return createdData;
//		}
//	}
	public Room create(RoomCreateRequest request){
		Room room = modelMapper.map(request, Room.class);
		room.create(request.getRequesterIdUser());

		save(room);

		return room;
	}

	public Room update(String roomId, RoomCreateRequest request) {
		Room data = roomRepository.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found for this id :: " + roomId));
	
		if(roomRepository.findByNameAndIdNot(request.getName(), roomId) != null) throw new DataAlreadyExistException();
		else {
			data.setName(request.getName());
			data.setDescription(request.getDescription());
			data.setQuota(request.getQuota());
			data.update(request.getRequesterIdUser());
			
			final Room updatedData = roomRepository.save(data);
			return updatedData;
		}
	}

	public RoomDetail addRoomDetail(String roomId, RoomDetailCreateRequest request){
		Room room = getById(roomId);
		RoomDetail newRoomDetail = modelMapper.map(request, RoomDetail.class);
		room.getDetails().add(newRoomDetail);

		save(room);

		return newRoomDetail;
	}
	public boolean delete(String roomId) {
		Room data = roomRepository.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found for this id :: " + roomId));
	
		if(roomRepository.countCurrentTenantsOfRoom(roomId) == 0) {
			data.setDeleted(true);
			roomRepository.save(data);
			
			return true;
		} else throw new InternalServerErrorException("This room is still rented by the tenant!");
	}

	public void save(Room room){
		try {
			roomRepository.save(room);
		}
		catch (DataIntegrityViolationException e){
			System.out.println(e);
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
}