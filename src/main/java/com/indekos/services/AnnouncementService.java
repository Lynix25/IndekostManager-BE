package com.indekos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.AnnouncementRequest;
import com.indekos.model.Announcement;
import com.indekos.repository.AnnouncementRepository;
import com.indekos.utils.Utils;

@Service
public class AnnouncementService {
	
	@Autowired
	private AnnouncementRepository announcementRepository;
	
	public Announcement getById(String announcementId) {
		
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + announcementId));
		
		announcement.setImage(Utils.decompressImage(announcement.getImage()));
		return announcement;
	}
	
	public List<Announcement> getTop(int limit) {
		List<Announcement> announcements = new ArrayList<>();
		
		announcementRepository.getTopAnnouncement(limit).forEach(data -> {
			data.setImage(Utils.decompressImage(data.getImage()));
			announcements.add(data);
		});
		return announcements;
	}
	
	public List<Announcement> getAll() {
		List<Announcement> announcements = new ArrayList<>();
		
		announcementRepository.findAllByOrderByCreatedDateDesc().forEach(data -> {
			data.setImage(Utils.decompressImage(data.getImage()));
			announcements.add(data);
		});
		return announcements;
	}
	
	public List<Announcement> getSearch(String keyword) {
		return announcementRepository.findSearchAnnouncement(keyword);
	}
	
	public Announcement create(AnnouncementRequest request) {
		if(announcementRepository.findByTitle(request.getTitle()) != null) throw new DataAlreadyExistException();
		else {
			Announcement newData = new Announcement();
			newData.setTitle(request.getTitle());
			newData.setDescription(request.getDescription());
			newData.setPeriod(request.getPeriod());
			newData.updateCreated(request.getRequesterIdUser());
			newData.updateLastModified(request.getRequesterIdUser());
			newData.setImage(Utils.compressImage(request.getImage()));

			final Announcement createdData = announcementRepository.save(newData);
			return createdData;
		}
	}

	public Announcement update(String announcementId, AnnouncementRequest request) {
		Announcement data = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + announcementId));
	
		if(announcementRepository.findByTitleAndIdNot(request.getTitle(), announcementId) != null) throw new DataAlreadyExistException();
		else {
			data.setTitle(request.getTitle());
			data.setDescription(request.getDescription());
			data.setPeriod(request.getPeriod());
			data.setImage(Utils.compressImage(request.getImage()));
			data.updateLastModified(request.getRequesterIdUser());
			
			final Announcement updatedData = announcementRepository.save(data);
			return updatedData;
		}
	}
	
	public Announcement uploadImage(String annoucementId, MultipartFile file) {
		
		Announcement announcement = announcementRepository.findById(annoucementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + annoucementId));
		
		announcement.setImage(Utils.compressImage(file));
		final Announcement updatedData = announcementRepository.save(announcement);
		return updatedData;
	}

	public Announcement delete(String announcementId) {
		Announcement data = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + announcementId));
	
		final Announcement deletedData = data;
		announcementRepository.delete(data);
		
		return deletedData;
	}
}