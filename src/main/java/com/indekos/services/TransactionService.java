package com.indekos.services;

import com.indekos.common.helper.SnapAPI;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.model.Rent;
import com.indekos.model.Transaction;
import com.indekos.repository.TransactionRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TransactionService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ServiceService serviceService;
    public Transaction getByID(String id){
        try {
            return transactionRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }

    public Transaction create(TransactionCreateRequest request){
        Transaction transaction = new Transaction();

        transaction.setServiceItem(serviceService.getManyById(request.getServiceItemIds()));
        transaction.create(request.getRequesterId());
        transaction.setPenaltyFee(0L);
        save(request.getRequesterId(),transaction);

        String transactionToken = SnapAPI.createTransaction(transaction.getId(), getTotalPayment(transaction));
        transaction.setToken(transactionToken);

        save(request.getRequesterId(),transaction);
        return transaction;
    }

    public Transaction pay(String id){
        Transaction transaction = getByID(id);

        return transaction;
    }

    private void save(String modifierId, Transaction transaction){
        try {
            transaction.update(modifierId);
            transactionRepository.save(transaction);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public Integer getTotalPayment(Transaction transaction){
        Integer totalAmount = 0;
        for (com.indekos.model.Service service: transaction.getServiceItem()){
            totalAmount += service.getPrice();
        }
        return totalAmount;
    }
    public Long calculateFee(Transaction transaction){
        Long feeCount = 0L;
        for (Rent rent: transaction.getRentItem()) {
            feeCount += (long) (Utils.dayDiv(rent.getDueDate(), System.currentTimeMillis()) * 5000L);
        }

        transactionRepository.save(transaction);
        return feeCount;
    }

    public Long calculateUnpaid(Transaction transaction){
        Long unpaidTotal = 0L;
        for (Rent rent: transaction.getRentItem()) {
            unpaidTotal += rent.getPrice();
        }
        for (com.indekos.model.Service service: transaction.getServiceItem()) {
            unpaidTotal += service.getPrice();
        }

        return unpaidTotal;
    }
}
