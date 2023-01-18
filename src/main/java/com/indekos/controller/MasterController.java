package com.indekos.controller;


import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.CreateMasterCategory;
import com.indekos.dto.response.Response;
import com.indekos.dto.MasterServiceDTO;
import com.indekos.model.MasterService;
import com.indekos.services.MasterServiceService;
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
    MasterServiceService masterServiceService;
    @GetMapping
    private ResponseEntity getAllService(){
        return GlobalAcceptions.listData(masterServiceService.getAll(), "All Master Service Data");
    }

    @GetMapping("/{id}")
    private ResponseEntity getMasterService(@PathVariable String id){
        MasterService masterService = masterServiceService.getByID(id);

        MasterServiceDTO masterServiceDTO = modelMapper.map(masterService, MasterServiceDTO.class);
        return ResponseEntity.ok().body(masterServiceDTO);
    }

    @PostMapping
    private ResponseEntity createMasterService(@Valid @RequestBody CreateMasterCategory requestBody, Errors errors){
        Validated.request(errors);
        masterServiceService.register(requestBody);

        return new ResponseEntity(new Response("Sukses", "this all user"), HttpStatus.OK);
    }



}
