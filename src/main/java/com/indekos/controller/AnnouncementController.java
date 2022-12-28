package com.indekos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.AnnouncementRequest;
import com.indekos.model.Announcement;
import com.indekos.services.AnnouncementService;

@RestController
@CrossOrigin
@RequestMapping("/announcement")
public class AnnouncementController {

	@Autowired
	private AnnouncementService announcementService;
	
	@GetMapping("/top")
	public List<Announcement> getTopAnnouncement(@RequestParam int size) {
		return announcementService.getTop(size);
	}
	
	@GetMapping("/all")
	public List<Announcement> getAllAnnouncement() {
		return announcementService.getAll();
	}
	
	@GetMapping
	public List<Announcement> getAllActiveAnnouncement(@RequestParam String search){
		return announcementService.getAllActive(search);
	}
	
	@PostMapping
	public Announcement createAnnouncement(@Validated @RequestBody AnnouncementRequest request) {
		return announcementService.create(request);
	}
	
	@PutMapping("/{announcementId}")
	public ResponseEntity<Announcement> updateRole(@PathVariable String announcementId, @Validated @RequestBody AnnouncementRequest request) throws ResourceNotFoundException {
		final Announcement updatedAnnouncement = announcementService.update(announcementId, request);
		return ResponseEntity.ok(updatedAnnouncement);
	}
	
	@DeleteMapping("/{announcementId}")
	public Map<String, Boolean> deleteAnnouncement(@PathVariable String announcementId) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", announcementService.delete(announcementId));
		return response;
	}
}
