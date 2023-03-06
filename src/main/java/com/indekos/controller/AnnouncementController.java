package com.indekos.controller;

import java.util.List;

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
	public List<Announcement> getSearchAnnouncement(@RequestParam String search){
		return announcementService.getSearch(search);
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
	public ResponseEntity<Announcement> deleteAnnouncement(@PathVariable String announcementId) throws ResourceNotFoundException {
		Announcement deleted = announcementService.delete(announcementId);
		return ResponseEntity.ok(deleted);
	}
}