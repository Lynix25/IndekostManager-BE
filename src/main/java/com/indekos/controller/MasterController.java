package com.indekos.controller;


import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.CreateMasterCategory;
import com.indekos.dto.response.Response;
import com.indekos.dto.MasterServiceDTO;
import com.indekos.model.Service;
import com.indekos.services.ServiceService;
import com.indekos.utils.Validated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/service")
public class MasterController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ServiceService serviceService;

    @PostMapping("")
    private ResponseEntity createMasterService(@Valid @RequestBody CreateMasterCategory requestBody, Errors errors){
        Validated.request(errors);
        serviceService.register(requestBody);

        return new ResponseEntity(new Response("Sukses", "this all user"), HttpStatus.OK);
    }

    @GetMapping("")
    private ResponseEntity getAllService(){
        return GlobalAcceptions.listData(serviceService.getAll(), "All Master Service Data");
    }

    @GetMapping("/{id}")
    private ResponseEntity getMasterService(@PathVariable String id){
        Service masterService = serviceService.getByID(id);

        MasterServiceDTO masterServiceDTO = modelMapper.map(masterService, MasterServiceDTO.class);

        return ResponseEntity.ok().body(masterServiceDTO);
    }

}
