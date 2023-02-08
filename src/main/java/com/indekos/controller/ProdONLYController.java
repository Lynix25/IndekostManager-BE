package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.RoomDetailCreateRequest;
import com.indekos.model.RoomDetail;
import com.indekos.services.RoomDetailService;
import com.indekos.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ProdONLYController {

    @Autowired
    ServiceService serviceService;

    @Autowired
    RoomDetailService roomDetailService;

    @GetMapping("/unpaid/{id}")
    public ResponseEntity getAllPayableItem(@PathVariable String id){


        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/roomdetail/{id}")
    public ResponseEntity getRoomDetail(@PathVariable String id){
        RoomDetail roomDetail = roomDetailService.getByID(id);

        return GlobalAcceptions.data(roomDetail, "Room Detail Data");
    }
}
