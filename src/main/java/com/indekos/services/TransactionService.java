package com.indekos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.indekos.common.helper.SnapAPI;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.TransactionDetailsDTO;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.dto.response.MidtransCheckTransactionResponse;
import com.indekos.model.Rent;
import com.indekos.model.Task;
import com.indekos.model.Transaction;
import com.indekos.model.User;
import com.indekos.repository.TransactionRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ServiceService serviceService;

    @Autowired
    TaskService taskService;

    @Autowired
    RentService rentService;

    @Autowired
    UserService userService;

    public Transaction getByID(String id){
        try {
            return transactionRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Transaction ID : " + id);
        }
    }

    public List<Transaction> getAllByUser(User user){
        List<Transaction> transactions = transactionRepository.findAllByUser(user);
        return transactions;
    }

    public Transaction getByPaymentId(String paymentId){
        try {
        return transactionRepository.findByPaymentId(paymentId).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestException("Invalid Payment ID : " + paymentId);
        }
    }

    public Transaction create(TransactionCreateRequest request){
        User user = userService.getById(request.getRequesterId()).getUser();

        Transaction transaction = new Transaction();

        if(request.getTaskItemIds() != null){
            transaction.setTaskItems(taskService.getManyById(request.getTaskItemIds()));
            for (Task task : transaction.getTaskItems()){
                task.setTransaction(transaction);
            }
        }
        if(request.getRentItemIds() != null) {
            transaction.setRentItems(rentService.getManyById(request.getRentItemIds()));
            for (Rent rent : transaction.getRentItems()){
                rent.setTransaction(transaction);
            }
        }
        transaction.create(request.getRequesterId());
        transaction.setPenaltyFee(0L);
        transaction.setUser(user);
        transaction.setPaymentId(Utils.UUID4());
        String transactionToken = SnapAPI.createTransaction(transaction.getPaymentId(), getTotalPayment(transaction));
        transaction.setToken(transactionToken);

        save(request.getRequesterId(),transaction);
        return transaction;
    }

    public Transaction pay(String id){
        Transaction transaction = getByID(id);

        return transaction;
    }

    public TransactionDetailsDTO getPaymentDetails(Transaction transaction){
        MidtransCheckTransactionResponse checkTransactionResponse = SnapAPI.checkTransaction(transaction.getPaymentId());
        modelMapper.typeMap(Transaction.class, TransactionDetailsDTO.class).addMappings(mapper -> {
            mapper.map(src -> checkTransactionResponse, TransactionDetailsDTO::setPayment);
            mapper.map(Transaction::getTotalItem, TransactionDetailsDTO::setTotalItem);
            mapper.map(Transaction::getTotalPrice, TransactionDetailsDTO::setTotalPrice);
        });
        TransactionDetailsDTO transactionDetailsDTO = modelMapper.map(transaction, TransactionDetailsDTO.class);

        return transactionDetailsDTO;
    }

    private Transaction save(String modifierId, Transaction transaction){
        try {
            transaction.update(modifierId);
            return transactionRepository.save(transaction);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
        return null;
    }

    public Integer getTotalPayment(Transaction transaction){
        Integer totalAmount = 0;
        for (Task task: transaction.getTaskItems()){
            totalAmount += task.getCharge();
        }

        for (Rent rent: transaction.getRentItems()){
            totalAmount += rent.getPrice();
        }
        return totalAmount;
    }
    
    public Long calculateFee(Transaction transaction){
        Long feeCount = 0L;
        for (Rent rent: transaction.getRentItems()) {
            feeCount += (long) (Utils.dayDiv(rent.getDueDate(), System.currentTimeMillis()) * 5000L);
        }

        transactionRepository.save(transaction);
        return feeCount;
    }

    public Long calculateUnpaid(Transaction transaction){
        Long unpaidTotal = 0L;
        for (Rent rent: transaction.getRentItems()) {
            unpaidTotal += rent.getPrice();
        }
        for (Task task: transaction.getTaskItems()) {
            unpaidTotal += task.getCharge();
        }

        return unpaidTotal;
    }
}
