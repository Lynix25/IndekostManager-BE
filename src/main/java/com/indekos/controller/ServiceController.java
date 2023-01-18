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
    @GetMapping
    private ResponseEntity getAllService(){
        return GlobalAcceptions.listData(serviceService.getAll(), "All Master Service Data");
    }
    @GetMapping("/{id}")
    private ResponseEntity getService(@PathVariable String id){
        Service service = serviceService.getByID(id);

        MasterServiceDTO masterServiceDTO = modelMapper.map(service, MasterServiceDTO.class);

        return ResponseEntity.ok().body(masterServiceDTO);
    }
    @PostMapping
    private ResponseEntity createService(@Valid @RequestBody ServiceCreateRequest requestBody, Errors errors){
        Validated.request(errors);

        serviceService.register(requestBody);

        return new ResponseEntity(new Response("Success", "Service Registered"), HttpStatus.OK);
    }
    @PutMapping(value = "{id}")
    private ResponseEntity updateService(@PathVariable String id, @Valid @RequestBody ServiceCreateRequest requestBody, Errors errors){
        Validated.request(errors);

        serviceService.update(id, requestBody);

        return new ResponseEntity(new Response("Success", "Service Updated"), HttpStatus.OK);
    }


}
