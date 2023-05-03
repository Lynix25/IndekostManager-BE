package com.indekos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indekos.common.helper.SnapAPI;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.dto.response.CheckTransactionResponse;
import com.indekos.model.Rent;
import com.indekos.model.Service;
import com.indekos.model.Transaction;
import com.indekos.services.RentService;
import com.indekos.services.ServiceService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
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
        List<Service> services = serviceService.getAll();
        List<Rent> rents = rentService.getAllUnpaid(userId);
        Long maxDueDate = -1L;
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
    public ResponseEntity<?> create(@RequestBody TransactionCreateRequest request){
        Transaction transaction = transactionService.create(request);

        return new ResponseEntity<>(transaction.getToken(),HttpStatus.OK);
    }

    @Autowired
    ModelMapper modelMapper;
    @GetMapping("check/{orderId}")
    public ResponseEntity<?> check(@PathVariable String orderId) throws JsonProcessingException {
        JSONObject res = SnapAPI.checkTransaction(orderId);
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }
}
