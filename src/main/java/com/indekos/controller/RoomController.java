package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.AuditableRequest;
import com.indekos.dto.request.RoomCreateRequest;
import com.indekos.dto.request.RoomDetailsCreateRequest;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.dto.request.RoomUpdateRequest;
import com.indekos.dto.response.RoomDTO;
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
	public ResponseEntity<?> getRoom(@PathVariable String roomId){
		RoomDTO room = roomService.getById(roomId);
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
		return GlobalAcceptions.data(roomService.create(request), "Berhasil menambahkan data kamar");
	}

	@PostMapping("/{roomId}/details")
	public ResponseEntity<?> addRoomDetail(@PathVariable String roomId, @Valid @RequestBody RoomDetailsCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.addRoomDetail(roomId, requestBody), "Berhasil menambahkan detail kamar");
	}
	
	@PostMapping("/{roomId}/prices")
	public ResponseEntity<?> addRoomPrice(@PathVariable String roomId, @Valid @RequestBody RoomPriceCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.addRoomPrice(roomId, requestBody), "Berhasil menambahkan data harga kamar");
	}
	
	@PutMapping("/{roomId}")
	public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody RoomUpdateRequest request) {
		return GlobalAcceptions.data(roomService.update(roomId, request), "Berhasil memperbaharui data kamar");
	}
	
	@PutMapping("/{roomId}/details") // edit = roomDetailId
	public ResponseEntity<?> updateRoomDetail(@RequestParam Long edit, @PathVariable String roomId, @Valid @RequestBody RoomDetailsCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.editRoomDetail(edit, roomId, requestBody), "Berhasil memperbaharui kategori/ spesifikasi");
	}
	
	@PutMapping("/{roomId}/prices") // edit = roomPriceDetailId
	public ResponseEntity<?> updateRoomPrice(@RequestParam Long edit, @PathVariable String roomId, @Valid @RequestBody RoomPriceCreateRequest requestBody, Errors errors){
		Validated.request(errors);
		return GlobalAcceptions.data(roomService.editRoomPrice(edit, roomId, requestBody), "Berhasil memperbaharui data harga kamar");
	}
	
	@DeleteMapping("/{roomId}")	// requester = requesterId
	public ResponseEntity<?> deleteRoom(@PathVariable String roomId,  @RequestParam String requester) {
		return GlobalAcceptions.data(roomService.delete(roomId, requester), "Berhasil menghapus data kamar");
	}
	
	@DeleteMapping("/{roomId}/details") // delete = roomDetailId, requester = requesterId
	public ResponseEntity<?> deleteRoomDetail(@RequestParam Long delete, @RequestParam String requester, @PathVariable String roomId){
		return GlobalAcceptions.data(roomService.removeRoomDetail(delete, requester, roomId), "Berhasil menghapus kategori/ spesifikasi");
	}
	
	@DeleteMapping("/{roomId}/prices") // delete = roomPriceDetailId, requester = requesterId
	public ResponseEntity<?> deleteRoomPrice(@RequestParam Long delete, @RequestParam String requester, @PathVariable String roomId){
		return GlobalAcceptions.data(roomService.removeRoomPrice(delete, requester, roomId), "Berhasil menghapus data harga kamar");
	}
}
