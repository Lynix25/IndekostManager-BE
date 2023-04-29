package com.indekos.controller;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.AnnouncementRequest;
import com.indekos.services.AnnouncementService;
import com.indekos.utils.Validated;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

	@Autowired
	private AnnouncementService announcementService;
	
	@GetMapping("/{announcementId}")
	public ResponseEntity<?> getAnnouncement(@PathVariable String announcementId) {
		return GlobalAcceptions.data(announcementService.getById(announcementId), "Announcement Data");
	}
	
	@GetMapping
	public ResponseEntity<?> getAllAnnouncement() {
		return GlobalAcceptions.listData(announcementService.getAll(), "All Announcement Data");
	}
	
	@PostMapping
	public ResponseEntity<?> createAnnouncement(@ModelAttribute MultipartFile image, @Valid @ModelAttribute AnnouncementRequest request, Errors errors) throws FileSizeLimitExceededException {
		Validated.request(errors);
		request.setImage(image);
		return GlobalAcceptions.data(announcementService.create(request), "Berhasil menambahkan pengumuman");
	}
	
	@PutMapping("/{announcementId}")
	public ResponseEntity<?> updateAnnouncement(@PathVariable String announcementId, @ModelAttribute MultipartFile image, @ModelAttribute AnnouncementRequest request) throws ResourceNotFoundException, FileSizeLimitExceededException {
		request.setImage(image);
		return GlobalAcceptions.data(announcementService.update(announcementId, request), "Berhasil memperbaharui pengumuman");
	}
	
	@DeleteMapping("/{announcementId}")
	public ResponseEntity<?> deleteAnnouncement(@PathVariable String announcementId) throws ResourceNotFoundException {
		return GlobalAcceptions.data(announcementService.delete(announcementId), "Berhasil menghapus pengumuman");
	}
}