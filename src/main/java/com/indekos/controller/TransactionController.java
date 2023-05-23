package com.indekos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.SnapAPI;
import com.indekos.dto.TransactionDetailsDTO;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.dto.response.CheckTransactionResponse;
import com.indekos.dto.response.MidtransCheckTransactionResponse;
import com.indekos.model.Rent;
import com.indekos.model.Task;
import com.indekos.model.Transaction;
import com.indekos.model.User;
import com.indekos.services.*;
import com.indekos.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping
    public ResponseEntity<?> getAllTransaction(@RequestParam String requestor){
        List<Transaction> transactions;
        if(requestor == ""){
            transactions = transactionService.getAll();
        }
        else {
            User user = userService.getById(requestor).getUser();
            transactions = transactionService.getAllByUser(user);
        }
        List<TransactionDetailsDTO> transactionDetailsDTOS = new ArrayList<>();

        for(Transaction transaction: transactions){
            transactionDetailsDTOS.add(transactionService.getPaymentDetails(transaction));
        }

        return GlobalAcceptions.listData(transactionDetailsDTOS, "All Transaction");
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId){
        TransactionDetailsDTO transactionDetailsDTO = transactionService.getPaymentDetails(transactionService.getByID(transactionId));
        return GlobalAcceptions.data(transactionDetailsDTO, "Transaction Data");
    }

    @GetMapping("/unpaid/{userId}")
    public ResponseEntity<?> getUnpaidTransaction(@PathVariable String userId){
        List<Task> tasks = taskService.getAllCharged(userId);
        List<Rent> rents = rentService.getAllUnpaid(userId);
        Long maxDueDate = -1L;
        Long unpaidTotal = 0L;
        for (Rent rent: rents) {
            unpaidTotal += rent.getPrice();
            maxDueDate =  Math.max(maxDueDate, rent.getDueDate());
        }
        for (Task task: tasks) {
            unpaidTotal += task.getCharge();
            maxDueDate =  Math.max(maxDueDate, task.getCreatedDate() + task.getService().getDueDate() * Constant.DAYS_IN_MILLIS);
        }

        CheckTransactionResponse checkTransactionResponse = new CheckTransactionResponse(tasks,rents,unpaidTotal,maxDueDate);

        return new ResponseEntity<>(checkTransactionResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionCreateRequest request){
        Transaction transaction = transactionService.create(request);

        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @GetMapping("check/{transactionId}")
    public ResponseEntity<?> check(@PathVariable String transactionId) throws JsonProcessingException {
        MidtransCheckTransactionResponse res = SnapAPI.checkTransaction(transactionId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
