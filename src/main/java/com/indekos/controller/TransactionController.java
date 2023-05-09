package com.indekos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indekos.common.helper.SnapAPI;
import com.indekos.dto.TaskDetailDTO;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.dto.response.CheckTransactionResponse;
import com.indekos.dto.response.TransactionCreateResponse;
import com.indekos.model.Rent;
import com.indekos.model.Transaction;
import com.indekos.services.RentService;
import com.indekos.services.ServiceService;
import com.indekos.services.TaskService;

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
	ModelMapper modelMapper;
	
	@Autowired
    TransactionService transactionService;
    
	@Autowired
    TaskService taskService;
    
	@Autowired
    ServiceService serviceService;
    
	@Autowired
    RentService rentService;

    @GetMapping("/unpaid/{userId}")
    public ResponseEntity<?> getUnpaidTransaction(@PathVariable String userId){
        List<TaskDetailDTO> tasks = taskService.getAllCharged(userId);
        List<Rent> rents = rentService.getAllUnpaid(userId);
        Long maxDueDate = -1L;
        Long unpaidTotal = 0L;
        for (Rent rent: rents) {
            unpaidTotal += rent.getPrice();
            maxDueDate =  Math.max(maxDueDate, rent.getDueDate());
        }
        for (TaskDetailDTO task: tasks) {
            unpaidTotal += (task.getTask().getCharge() + task.getTask().getAdditionalCharge());
        }

        CheckTransactionResponse checkTransactionResponse = new CheckTransactionResponse(tasks,rents,unpaidTotal,maxDueDate);

        return new ResponseEntity<>(checkTransactionResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionCreateRequest request){
        Transaction transaction = transactionService.create(request);

        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @GetMapping("check/{orderId}")
    public ResponseEntity<?> check(@PathVariable String orderId) throws JsonProcessingException {
        JSONObject res = SnapAPI.checkTransaction(orderId);
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }

    @PutMapping("token/{id}")
    public ResponseEntity<?> saveToken(@PathVariable String id, @RequestParam String token){
        transactionService.saveToken(id,token);
        return new ResponseEntity<>("Succes to save token", HttpStatus.OK);
    }
}
