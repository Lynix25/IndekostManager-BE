package com.indekos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.DataAlreadyExistException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
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
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Announcement ID"));
		
		announcement.setImage(Utils.decompressImage(announcement.getImage()));
		return announcement;
	}
	
	public List<Announcement> getAll() {
		List<Announcement> announcements = new ArrayList<>();
		announcementRepository.findAllByOrderByCreatedDateDesc().forEach(data -> {
			data.setImage(Utils.decompressImage(data.getImage()));
			announcements.add(data);
		});
		return announcements;
	}
	
	public Announcement create(AnnouncementRequest request) {
		if(announcementRepository.findByTitle(request.getTitle()) != null) throw new DataAlreadyExistException();
		else {
			Announcement newData = new Announcement();
			newData.setTitle(request.getTitle());
			newData.setDescription(request.getDescription());
			newData.setPeriod(request.getPeriod());
			newData.create(request.getRequesterIdUser());
			newData.update(request.getRequesterIdUser());
			newData.setImage(Utils.compressImage(request.getImage()));

			final Announcement createdData = announcementRepository.save(newData);
			return createdData;
		}
	}

	public Announcement update(String announcementId, AnnouncementRequest request) {
		Announcement data = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Announcement ID"));
	
		if(announcementRepository.findByTitleAndIdNot(request.getTitle(), announcementId) != null) throw new DataAlreadyExistException();
		else {
			data.setTitle(request.getTitle());
			data.setDescription(request.getDescription());
			data.setPeriod(request.getPeriod());
			data.setImage(Utils.compressImage(request.getImage()));
			data.update(request.getRequesterIdUser());
			
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