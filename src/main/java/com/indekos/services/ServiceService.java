package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.ServiceCreateRequest;
import com.indekos.model.MasterService;
import com.indekos.model.Service;
import com.indekos.repository.MasterServiceRepository;
import com.indekos.repository.ServiceRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;

@org.springframework.stereotype.Service
public class ServiceService {
    
	@Autowired
    ModelMapper modelMapper;
    
    @Autowired
    ServiceRepository serviceRepository;
    
    @Autowired
    MasterServiceRepository masterServiceRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void initializeMasterService() {
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (1, 'Laundry')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (2, 'Pembersihan Kamar')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (3, 'Perbaikan Fasilitas')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (4, 'Layanan Lainnya')");
    }
    
    public List<MasterService> getAllServiceCategory() {
    	return masterServiceRepository.findAllByOrderByIdAsc();
    }
    
    public List<Service> getAll(){
        return serviceRepository.findAll();
    }

    public List<Service> getAllUnpaid(String userId){
        return serviceRepository.findUnpaidById(userId);
    }

    public Service getByID(String id){
        try {
            return serviceRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
    public Service register(ServiceCreateRequest requestBody){
        Service service = modelMapper.map(requestBody, Service.class);
        service.create(requestBody.getRequesterId());

        serviceRepository.save(service);
        return service;
    }
    public Service update(String id, ServiceCreateRequest requestBody){
        Service service = getByID(id);

        modelMapper.map(requestBody, service);
        service.update(requestBody.getRequesterId());
        serviceRepository.save(service);
        return service;
    }
}
