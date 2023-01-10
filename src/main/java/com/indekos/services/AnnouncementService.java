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
		return announcementRepository.findAllByOrderByCreatedDateDesc();
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
			newData.updateCreated(request.getUser());
			newData.updateLastModified(request.getUser());

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
			data.updateLastModified(request.getUser());
			
			final Announcement updatedData = announcementRepository.save(data);
			return updatedData;
		}
	}

	public Announcement delete(String announcementId) {
		Announcement data = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new ResourceNotFoundException("Announcement not found for this id :: " + announcementId));
	
		final Announcement deletedData = data;
		announcementRepository.delete(data);
		
		return deletedData;
	}
}