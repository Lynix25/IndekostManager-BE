package com.indekos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.dto.response.RoomResponse;
import com.indekos.model.Room;
import com.indekos.services.RoomService;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public ResponseEntity getAllRoom() throws InterruptedException {
		return GlobalAcceptions.listData(roomService.getAll(), "All Room Data");
	}

	@GetMapping("/{id}")
	public ResponseEntity getRoom (@PathVariable String id){
		Room room = roomService.getById(id);

		return GlobalAcceptions.data(room,"Room Data");
	}
	
	@GetMapping("/available")
	public ResponseEntity getAllAvailableRoom(@RequestParam String roomName) {
		return GlobalAcceptions.listData(roomService.getAllAvailable(roomName), "Room List Data");
	}
	
	@PostMapping
	public ResponseEntity createRoom(@Valid @RequestBody RoomCreateRequest request, Errors errors) {
		Validated.request(errors);

		return new ResponseEntity<>(roomService.create(request), HttpStatus.OK);
	}

	@PostMapping("/{id}/details")
	public ResponseEntity addRoomDetail(@PathVariable(value = "id") String roomId, @Valid @RequestBody RoomPriceCreateRequest requestBody, Errors errors){
		Validated.request(errors);

		return GlobalAcceptions.data(roomService.addRoomDetail(roomId, requestBody),"New Room Detail Data");
	}
	
	@PutMapping("/{roomId}")
	public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @RequestBody RoomCreateRequest request) {
		Room updatedRoom = roomService.update(roomId, request);
		return ResponseEntity.ok(updatedRoom);
	}
	
	@DeleteMapping("/{roomId}")
	public ResponseEntity deleteRoom(@PathVariable String roomId) {
		return GlobalAcceptions.data(roomService.delete(roomId), "Deleted");
	}

	@DeleteMapping("/test/{roomId}")
	public ResponseEntity testDeleteRoom(@PathVariable String roomId) {
		roomService.deleteV2(roomId);
		System.out.println(roomId);
		return new ResponseEntity<>("Deleted", HttpStatus.OK);
	}
}
