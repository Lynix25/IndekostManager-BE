package com.indekos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.dto.request.AnnouncementRequest;
import com.indekos.model.Announcement;
import com.indekos.repository.AnnouncementRepository;

@Service
public class AnnouncementService {
	
	@Autowired
	private AnnouncementRepository announcementRepository;
	
	public List<Announcement> getTop(int limit) {
		return announcementRepository.getTopAnnouncement(limit);
	}
	
	public List<Announcement> getAll() {
		return announcementRepository.findAll();
	}
	
	public List<Announcement> getAllActive(String keyword) {
		return announcementRepository.findAllActiveAnnouncement(keyword);
	}
	
	public Announcement create(AnnouncementRequest request) {
		Announcement targetAnnouncement = announcementRepository.findByTitle(request.getTitle());
		if(targetAnnouncement != null) {
			if(targetAnnouncement.isActive()) 
				throw new DataAlreadyExistException();
			else {
				targetAnnouncement.setActive(true);
				targetAnnouncement.setDescription(request.getDescription());
				targetAnnouncement.setPeriod(request.getPeriod());
				targetAnnouncement.updateLastModified(request.getUser());
				
				final Announcement createdData = announcementRepository.save(targetAnnouncement);
				return createdData;
			}
		} else {
			Announcement newData = new Announcement();
			newData.setActive(true);
			newData.setTitle(request.getTitle());
			newData.setDescription(request.getDescription());
			newData.setPeriod(request.getPeriod());
			newData.updateCreated(request.getUser());
			newData.updateLastModified(request.getUser());

			final Announcement createdData = announcementRepository.save(newData);
			return createdData;
		}
	}

	public Announcement update(String announcementId, AnnouncementRequest request) {
		Announcement data = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + announcementId));
	
		Announcement targetAnnouncement = announcementRepository.findByTitleAndIdNot(request.getTitle(), announcementId); 
		if(targetAnnouncement != null) {
			if(targetAnnouncement.isActive()) 
				throw new DataAlreadyExistException();
			else {
				targetAnnouncement.setActive(request.isActive());
				targetAnnouncement.setTitle(request.getTitle());
				targetAnnouncement.setDescription(request.getDescription());
				targetAnnouncement.setPeriod(request.getPeriod());
				targetAnnouncement.updateLastModified(request.getUser());
				
				final Announcement updatedData = announcementRepository.save(targetAnnouncement);
				return updatedData;
			}
		} else {
			data.setActive(request.isActive());
			data.setTitle(request.getTitle());
			data.setDescription(request.getDescription());
			data.setPeriod(request.getPeriod());
			data.updateLastModified(request.getUser());
			
			final Announcement updatedData = announcementRepository.save(data);
			return updatedData;
		}
	}

	public boolean delete(String announcementId) {
		Announcement data = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + announcementId));
	
		return true;
	}
}