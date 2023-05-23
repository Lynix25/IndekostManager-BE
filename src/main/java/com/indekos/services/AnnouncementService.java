package com.indekos.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;
	
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
		if(announcementRepository.findByTitle(request.getTitle()) != null) 
			throw new DataAlreadyExistException("Sudah ada pengumuman dengan judul ini. Mohon ganti judul!");
		else {
			Announcement newData = new Announcement();
			newData.setTitle(request.getTitle());
			newData.setDescription(request.getDescription());
			newData.setPeriod(request.getPeriod());
			newData.create(request.getRequesterId());
			newData.update(request.getRequesterId());
			newData.setImage(Utils.compressImage(request.getImage()));

			final Announcement createdData = announcementRepository.save(newData);
			return createdData;
		}
	}

	public Announcement update(String announcementId, AnnouncementRequest request) {
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new InvalidRequestIdException("Invalid Announcement ID"));
	
		if(announcementRepository.findByTitleAndIdNot(request.getTitle(), announcementId) != null) 
			throw new DataAlreadyExistException("Sudah ada pengumuman dengan judul ini. Mohon ganti judul!");
		else {
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	        modelMapper.typeMap(AnnouncementRequest.class, Announcement.class).addMappings(mapper -> {
	           mapper.map(src -> src.getRequesterId(), Announcement::update);
	        });
	        
	        modelMapper.map(request, announcement);
	        if(request.getImage() != null) 
	        	announcement.setImage(Utils.compressImage(request.getImage()));
			
	        Announcement updatedAnnouncement = announcementRepository.save(announcement);
	        updatedAnnouncement.setImage(Utils.decompressImage(announcement.getImage()));
	        
			return updatedAnnouncement;
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