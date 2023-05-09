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
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void initializeService() {
    	// Master Service
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (1, 'Laundry')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (2, 'Pembersihan Kamar')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (3, 'Perbaikan Fasilitas')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_service (id, name) VALUES (4, 'Layanan Lainnya')");
    	
    	// Other Service
    	String insertKeyAuditData = "id, created_by, created_date, last_modified_by, last_modified_date, ";
    	String insertValueAuditData = "'system', UNIX_TIMESTAMP(), 'system', UNIX_TIMESTAMP(), ";
    	jdbcTemplate.update("INSERT IGNORE INTO service (" + insertKeyAuditData + "service_name, variant) VALUES ('e2e295f3-e3e4-11ed-a584-00155ddd50f3', " + insertValueAuditData + "(SELECT name from master_service WHERE id = 2), 'Lainnya')");
    	jdbcTemplate.update("INSERT IGNORE INTO service (" + insertKeyAuditData + "service_name, variant) VALUES ('e2e295f3-e3e4-11ed-a584-00155ddd50f3', " + insertValueAuditData + "(SELECT name from master_service WHERE id = 2), 'Lainnya')");
    	jdbcTemplate.update("INSERT IGNORE INTO service (" + insertKeyAuditData + "service_name, variant) VALUES ('e2e305f3-e3e4-11ed-a584-00155ddd50f3', " + insertValueAuditData + "(SELECT name from master_service WHERE id = 3), 'Lainnya')");
    	jdbcTemplate.update("INSERT IGNORE INTO service (" + insertKeyAuditData + "service_name, variant) VALUES ('e2e315f3-e3e4-11ed-a584-00155ddd50f3', " + insertValueAuditData + "(SELECT name from master_service WHERE id = 4), 'Lainnya')");
    	jdbcTemplate.update("INSERT IGNORE INTO service (" + insertKeyAuditData + "service_name, variant) VALUES ('e2e335f3-e3e4-11ed-a584-00155ddd50f3', " + insertValueAuditData + "(SELECT name from master_service WHERE id = 4), 'Pindah Kamar')");
    }
    
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
}
