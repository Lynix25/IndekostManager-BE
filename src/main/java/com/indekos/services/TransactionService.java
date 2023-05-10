package com.indekos.services;

import com.indekos.common.helper.SnapAPI;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.TransactionCreateRequest;
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
            throw new InvalidUserCredentialException("Invalid Transaction ID : " + id);
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
        User user = userService.getById(request.getRequesterId());

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
