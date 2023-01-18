package com.indekos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.response.RoomResponse;
import com.indekos.model.Room;
import com.indekos.services.RoomService;

@RestController
@RequestMapping(value = "/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public List<Room> getAllRoom() {
		return roomService.getAll();
	} 
	
	@GetMapping("/available")
	public List<RoomResponse> getAllAvailableRoom(@RequestParam String roomName) {
		return roomService.getAllAvailable(roomName);
	}
	
	@PostMapping
	public Room createRoom(@Validated @RequestBody RoomCreateRequest request) {
		return roomService.create(request);
	}
	
	@PutMapping("/{roomId}")
	public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @Validated @RequestBody RoomCreateRequest request) {
		Room updatedRoom = roomService.update(roomId, request);
		return ResponseEntity.ok(updatedRoom);
	}
	
	@DeleteMapping("/{roomId}")
	public Map<String, Boolean> deleteRoom(@PathVariable String roomId) {
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", roomService.delete(roomId));
		return response;
	}
}
