package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.AuditableRequest;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.request.RoomDetailsCreateRequest;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.dto.response.RoomWithDetails;
import com.indekos.services.RoomService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllRoomDetailCategory() {
		return GlobalAcceptions.listData(roomService.getRoomDetailsCategory(), "All Room Category Details");
	}
	
	@GetMapping
	public ResponseEntity<?> getAllRoom() throws InterruptedException {
		return GlobalAcceptions.listData(roomService.getAll(), "All Room Data");
	}

	@GetMapping("/{roomId}")
	public ResponseEntity<?> getRoom (@PathVariable String roomId){
		RoomWithDetails room = roomService.getById(roomId);
		return GlobalAcceptions.data(room, "Room Data");
	}
	
	@GetMapping("/available") // room = roomName
	public ResponseEntity<?> getAllAvailableRoom(@RequestParam String room) {
		return GlobalAcceptions.listData(roomService.getAllAvailable(room), "Available Room Data");
	}
	
	@GetMapping("/{roomId}/details")
	public ResponseEntity<?> getRoomDetail(@PathVariable String roomId){
		return GlobalAcceptions.listData(roomService.getDetailsByRoom(roomId), "All Room Detail Data");
	}
	
	@GetMapping("/{roomId}/prices")
	public ResponseEntity<?> getRoomPrice(@PathVariable String roomId){
		return GlobalAcceptions.listData(roomService.getPriceDetailsByRoom(roomId), "All Room Price Data");
	}
	
	@PostMapping
	public ResponseEntity<?> createRoom(@Valid @RequestBody RoomCreateRequest request, Errors errors) {
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.create(request), "New Room Data");
	}

	@PostMapping("/{roomId}/details")
	public ResponseEntity<?> addRoomDetail(@PathVariable String roomId, @Valid @RequestBody RoomDetailsCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.addRoomDetail(roomId, requestBody), "New Room Detail Data");
	}
	
	@PostMapping("/{roomId}/prices")
	public ResponseEntity<?> addRoomPrice(@PathVariable String roomId, @Valid @RequestBody RoomPriceCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.addRoomPrice(roomId, requestBody), "New Room Price Data");
	}
	
	@PutMapping("/{roomId}")
	public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody RoomCreateRequest request) {
		return GlobalAcceptions.data(roomService.update(roomId, request), "Updated Room Data");
	}
	
	@PutMapping("/{roomId}/details") // edit = roomDetailId
	public ResponseEntity<?> updateRoomDetail(@RequestParam Long edit, @PathVariable String roomId, @Valid @RequestBody RoomDetailsCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.editRoomDetail(edit, roomId, requestBody), "Updated Room Detail Data");
	}
	
	@PutMapping("/{roomId}/prices") // edit = roomPriceDetailId
	public ResponseEntity<?> updateRoomPrice(@RequestParam Long edit, @PathVariable String roomId, @Valid @RequestBody RoomPriceCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.editRoomPrice(edit, roomId, requestBody), "Updated Room Price Data");
	}
	
	@DeleteMapping("/{roomId}")
	public ResponseEntity<?> deleteRoom(@PathVariable String roomId, @Valid @RequestBody AuditableRequest request) {
		return GlobalAcceptions.data(roomService.delete(roomId, request.getRequesterId()), "Deleted Room Data");
	}
	
	@DeleteMapping("/{roomId}/details") // delete = roomDetailId
	public ResponseEntity<?> deleteRoomDetail(@RequestParam Long delete, @PathVariable String roomId, @Valid @RequestBody AuditableRequest request){
		return GlobalAcceptions.data(roomService.removeRoomDetail(delete, request.getRequesterId(), roomId), "Deleted Room Detail Data");
	}
	
	@DeleteMapping("/{roomId}/prices") // delete = roomPriceDetailId
	public ResponseEntity<?> deleteRoomPrice(@RequestParam Long delete, @PathVariable String roomId, @Valid @RequestBody AuditableRequest request){
		return GlobalAcceptions.data(roomService.removeRoomPrice(delete, request.getRequesterId(), roomId), "Deleted Room Price Data");
	}
}
