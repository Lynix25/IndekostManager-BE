package com.indekos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.SnapAPI;
import com.indekos.dto.TaskDetailDTO;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.dto.response.CheckTransactionResponse;
import com.indekos.dto.response.MidtransCheckTransactionResponse;
import com.indekos.dto.response.TransactionCreateResponse;
import com.indekos.model.Rent;
import com.indekos.model.Transaction;
import com.indekos.model.User;
import com.indekos.services.*;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<?> getAllTransaction(@PathVariable String userId){
        User user = userService.getById(userId);
        List<Transaction> transactions = transactionService.getAllByUser(user);

        return GlobalAcceptions.listData(transactions, "All Transaction");
    }

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
        MidtransCheckTransactionResponse res = SnapAPI.checkTransaction(orderId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
