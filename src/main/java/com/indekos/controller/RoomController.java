package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.request.RoomDetailCreateRequest;
import com.indekos.dto.response.RoomResponse;
import com.indekos.model.Room;
import com.indekos.model.RoomDetail;
import com.indekos.services.RoomService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public ResponseEntity getAllRoom() {
		return GlobalAcceptions.listData(roomService.getAll(), "All Room Data");
	}

	@GetMapping("/{id}")
	public ResponseEntity getRoom (@PathVariable String id){
		return GlobalAcceptions.data(roomService.getById(id),"Room Data");
	}
	
	@GetMapping("/available")
	public List<RoomResponse> getAllAvailableRoom(@RequestParam String roomName) {
		return roomService.getAllAvailable(roomName);
	}
	
	@PostMapping
	public Room createRoom(@Valid @RequestBody RoomCreateRequest request, Errors errors) {
		Validated.request(errors);

		return roomService.create(request);
	}

	@PostMapping("/{id}/details")
	public ResponseEntity addRoomDetail(@PathVariable(value = "id") String roomId,@Valid @RequestBody RoomDetailCreateRequest requestBody, Errors erros){
		Validated.request(erros);

		return GlobalAcceptions.data(roomService.addRoomDetail(roomId, requestBody),"New Room Detail Data");
	}
	
	@PutMapping("/{roomId}")
	public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @Valid @RequestBody RoomCreateRequest request) {
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
