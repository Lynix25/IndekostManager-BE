package com.indekos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.AnnouncementRequest;
import com.indekos.dto.response.Response;
import com.indekos.model.Announcement;
import com.indekos.services.AnnouncementService;
import com.indekos.utils.Validated;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

	@Autowired
	private AnnouncementService announcementService;
	
	@GetMapping("/{announcementId}")
	public ResponseEntity getAnnouncement(@PathVariable String announcementId) {
		return ResponseEntity.ok().body(announcementService.getById(announcementId));
	}
	
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
	public ResponseEntity createAnnouncement(@ModelAttribute MultipartFile image, @Valid @ModelAttribute AnnouncementRequest request, Errors errors) {
		Validated.request(errors);
		request.setImage(image);
		Announcement announcement = announcementService.create(request);
		return new ResponseEntity(new Response("Berhasil", "Pengumuman berhasil ditambahkan"), HttpStatus.OK);
	}
	
	@PutMapping("/{announcementId}")
	public ResponseEntity updateRole(@PathVariable String announcementId, @ModelAttribute MultipartFile image, @Valid @ModelAttribute AnnouncementRequest request) throws ResourceNotFoundException {
		request.setImage(image);
		final Announcement updatedAnnouncement = announcementService.update(announcementId, request);
		return GlobalAcceptions.data(updatedAnnouncement, "Success");
	}
	
	@DeleteMapping("/{announcementId}")
	public ResponseEntity<Announcement> deleteAnnouncement(@PathVariable String announcementId) throws ResourceNotFoundException {
		Announcement deleted = announcementService.delete(announcementId);
		return ResponseEntity.ok(deleted);
	}
}