package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.CreateMasterCategory;
import com.indekos.model.Account;
import com.indekos.model.Service;
import com.indekos.repository.ServiceRepository;
import com.indekos.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    public Service register(CreateMasterCategory createMasterCategory){
        Service service = new Service();

        // Reqeust input define
        service.setId(Utils.UUID4());
        service.setServiceName(createMasterCategory.getServiceName());
        service.setVariant(createMasterCategory.getVariant());
        service.setPrice(createMasterCategory.getPrice());

        serviceRepository.save(service);
        return service;
    }

    public List<Service> getAll(){
        return serviceRepository.findAll();
    }

    public Service getByID(String id){
        try {
            return serviceRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
}
