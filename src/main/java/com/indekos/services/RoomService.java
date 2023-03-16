package com.indekos.services;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.InternalServerErrorException;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.dto.response.RoomResponse;
import com.indekos.model.Room;
import com.indekos.model.RoomPriceDetail;
import com.indekos.repository.RoomRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
			throw new ResourceNotFoundException("Room not found for this id :: " + id);
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
		modelMapper.typeMap(RoomCreateRequest.class, Room.class).addMappings(mapper -> {
			mapper.map(RoomCreateRequest::getRequesterIdUser, Room::create);
		});
		Room room = modelMapper.map(request, Room.class);
		save(room);
		return room;
	}

	public Room update(String roomId, RoomCreateRequest request) {
		Room room = getById(roomId);

		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.typeMap(RoomCreateRequest.class, Room.class).addMappings(mapper -> {
			mapper.map(RoomCreateRequest::getRequesterIdUser, Room::update);
		});

		if(roomRepository.findByNameAndIdNot(request.getName(), roomId) != null) throw new DataAlreadyExistException();

		modelMapper.map(request, room);
		save(room);
		return room;
	}

	public RoomPriceDetail addRoomDetail(String roomId, RoomPriceCreateRequest request){
		Room room = getById(roomId);
		RoomPriceDetail newRoomPriceDetail = modelMapper.map(request, RoomPriceDetail.class);
		room.getPrices().add(newRoomPriceDetail);

		save(room);

		return newRoomPriceDetail;
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

	public boolean deleteV2(String roomId){
		Room room = getById(roomId);

		if(room.getUsers().size() == 0){
			room.setDeleted(false);

			save(room);
			return true;
		}else{
			throw new InvalidRequestException("This room is still rented by the tenant!");
		}
	}

	public void save(Room room){
		try {
			roomRepository.save(room);
		}
		catch (DataIntegrityViolationException e){
			System.out.println(e);
			throw new InvalidRequestException("DataIntegrityViolationException");
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
}