package com.indekos.controller;

import com.indekos.dto.response.CheckTransactionResponse;
import com.indekos.model.Rent;
import com.indekos.model.Service;
import com.indekos.services.RentService;
import com.indekos.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indekos.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    ServiceService serviceService;
    @Autowired
    RentService rentService;

    @GetMapping("/unpaid/{userId}")
    public ResponseEntity<?> getUnpaidTransaction(@PathVariable String userId){
        List<Service> services = serviceService.getAllUnpaid(userId);
        List<Rent> rents = rentService.getAllUnpaid(userId);
        Long maxDueDate = 0L;
        Long unpaidTotal = 0L;
        for (Rent rent: rents) {
            unpaidTotal += rent.getPrice();
            maxDueDate =  Math.max(maxDueDate, rent.getDueDate());
        }
        for (Service service: services) {
            unpaidTotal += service.getPrice();
        }

        CheckTransactionResponse checkTransactionResponse = new CheckTransactionResponse(services,rents,unpaidTotal,maxDueDate);

        return new ResponseEntity<>(checkTransactionResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
