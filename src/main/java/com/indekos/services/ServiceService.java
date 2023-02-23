package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.ServiceCreateRequest;
import com.indekos.model.Service;
import com.indekos.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ServiceRepository serviceRepository;
    public List<Service> getAll(){
        return serviceRepository.findAll();
    }

//    public List<Service> getAllUnpaid(String id){
//
//    }

    public Service getByID(String id){
        try {
            return serviceRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
    public Service register(ServiceCreateRequest requestBody){
        Service service = modelMapper.map(requestBody, Service.class);
        service.create(requestBody.getRequesterIdUser());

        serviceRepository.save(service);
        return service;
    }
    public Service update(String id, ServiceCreateRequest requestBody){
        Service service = getByID(id);

        modelMapper.map(requestBody, service);
        service.update(requestBody.getRequesterIdUser());
        serviceRepository.save(service);
        return service;
    }
}
