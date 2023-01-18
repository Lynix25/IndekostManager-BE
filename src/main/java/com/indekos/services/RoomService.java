package com.indekos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.InternalServerErrorException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.RoomRequest;
import com.indekos.dto.response.RoomResponse;
import com.indekos.model.Room;
import com.indekos.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public List<Room> getAll() {
		return roomRepository.findAllByOrderByNameAsc();
	}
	
	public List<RoomResponse> getAllAvailable(String keyword) {
		return roomRepository.findAllAvailableRoom(keyword);
	}
	
	public Room create(RoomRequest request) {
		Room targetRoom =roomRepository.findByName(request.getName());
		if(targetRoom != null) {
			if(targetRoom.isDeleted()) {
				targetRoom.setDeleted(false);
				targetRoom.setName(request.getName());
				targetRoom.setDescription(request.getDescription());
				targetRoom.setQuota(request.getQuota());
				targetRoom.updateLastModified(request.getUser());
				
				final Room createdData = roomRepository.save(targetRoom);
				return createdData;
			} else throw new DataAlreadyExistException();
		}
		else {
			Room newData = new Room();
			newData.setDeleted(false);
			newData.setName(request.getName());
			newData.setDescription(request.getDescription());
			newData.setQuota(request.getQuota());
//			newData.updateCreated(request.getUser());
			newData.updateLastModified(request.getUser());
			
			final Room createdData = roomRepository.save(newData);
			return createdData;
		}
	}

	public Room update(String roomId, RoomRequest request) {
		Room data = roomRepository.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found for this id :: " + roomId));
	
		if(roomRepository.findByNameAndIdNot(request.getName(), roomId) != null) throw new DataAlreadyExistException();
		else {
			data.setName(request.getName());
			data.setDescription(request.getDescription());
			data.setQuota(request.getQuota());
			data.updateLastModified(request.getUser());
			
			final Room updatedData = roomRepository.save(data);
			return updatedData;
		}
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
}