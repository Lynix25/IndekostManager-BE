package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.ServiceCreateRequest;
import com.indekos.model.MasterService;
import com.indekos.model.Service;
import com.indekos.repository.MasterServiceRepository;
import com.indekos.repository.ServiceRepository;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
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
    
    public List<MasterService> getAllServiceCategory() {
    	return masterServiceRepository.findAllByOrderByIdAsc();
    }
    
    public List<Service> getAll(){
        return serviceRepository.findAllByOrderByVariantAsc();
    }

    public List<Service> getAllUnpaid(String userId){
        return serviceRepository.findUnpaidById(userId);
    }

    public Service getByID(String id){
        try {
            return serviceRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid Service ID");
        }
    }

    public List<Service> getManyById(List<String> ids){
        List<Service> services = new ArrayList<>();

        for (String id: ids) {
            services.add(getByID(id));
        }

        return services;
    }

    public Service register(ServiceCreateRequest requestBody){
        Service service = modelMapper.map(requestBody, Service.class);
        service.create(requestBody.getRequesterId());

        serviceRepository.save(service);
        return service;
    }
    
    public Service update(String id, ServiceCreateRequest requestBody){
    	modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    	
        Service service = getByID(id);
        modelMapper.map(requestBody, service);
        service.update(requestBody.getRequesterId());
        serviceRepository.save(service);
        return service;
    }
    
    public boolean delete(String serviceId) {
    	Service service = serviceRepository.findById(serviceId).get();
    	serviceRepository.delete(service);
    	return true;
    }
}
