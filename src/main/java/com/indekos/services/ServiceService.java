package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.ServiceCreateRequest;
import com.indekos.model.Service;
import com.indekos.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ServiceRepository serviceRepository;
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
