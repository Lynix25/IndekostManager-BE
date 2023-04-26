package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.ServiceCreateRequest;
import com.indekos.dto.response.Response;
import com.indekos.dto.MasterServiceDTO;
import com.indekos.model.Service;
import com.indekos.services.ServiceService;
import com.indekos.utils.Validated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/service")
public class ServiceController {
    
	@Autowired
    ModelMapper modelMapper;
    
	@Autowired
    ServiceService serviceService;
    
	@GetMapping("/category")
	public ResponseEntity<?> getAllServiceCategory(){
		return GlobalAcceptions.listData(serviceService.getAllServiceCategory(), "All Master Service Data"); 
	}
	
	@GetMapping
    private ResponseEntity<?> getAllService(){
        return GlobalAcceptions.listData(serviceService.getAll(), "All Service Data");
    }
    
	@GetMapping("/{id}")
    private ResponseEntity<?> getService(@PathVariable String id){
        Service service = serviceService.getByID(id);
        MasterServiceDTO masterServiceDTO = modelMapper.map(service, MasterServiceDTO.class);

        return ResponseEntity.ok().body(masterServiceDTO);
    }
	
    @PostMapping
    private ResponseEntity<?> createService(@Valid @RequestBody ServiceCreateRequest requestBody, Errors errors){
        Validated.request(errors);
        return GlobalAcceptions.data(serviceService.register(requestBody), "Berhasil menambahkan layanan");
    }
    
    @PutMapping(value = "/{id}")
    private ResponseEntity<?> updateService(@PathVariable String id, @Valid @RequestBody ServiceCreateRequest requestBody, Errors errors){
    	Validated.request(errors);
    	return GlobalAcceptions.data(serviceService.update(id, requestBody), "Berhasil memperbaharui layanan");
    }
}
