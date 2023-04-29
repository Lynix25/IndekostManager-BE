package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.TransactionCreateRequest;
import com.indekos.model.Rent;
import com.indekos.model.Transaction;
import com.indekos.repository.TransactionRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TransactionService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TransactionRepository transactionRepository;
    public Transaction getByID(String id){
        try {
            return transactionRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }

    public Transaction create(TransactionCreateRequest request){
        Transaction transaction = modelMapper.map(request, Transaction.class);
        transaction.create(request.getRequesterId());
        transaction.setPaymentStatus("Menunggu Pembayaran");
        transaction.setPenaltyFee(0L);

        return transaction;
    }

    public Transaction pay(String id){
        Transaction transaction = getByID(id);
        transaction.setPaymentType("BCA Virtual Account");

        return transaction;
    }

    public Long calculateFee(Transaction transaction){
        Long feeCount = 0L;
        for (Rent rent: transaction.getRentItem()) {
            feeCount += (long) (Utils.dayDiv(rent.getDueDate(), System.currentTimeMillis()) * 5000L);
        }

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
