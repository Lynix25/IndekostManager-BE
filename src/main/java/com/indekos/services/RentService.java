package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.SimpleUserDTO;
import com.indekos.dto.TaskDTO;
import com.indekos.model.Rent;
import com.indekos.model.Task;
import com.indekos.model.Transaction;
import com.indekos.repository.RentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RentService {
    @Autowired
    RentRepository rentRepository;

    public List<Rent> getAllUnpaid(String userId){
        return rentRepository.findUnpaidById(userId);
    }

//    public Rent create(){
//        save(rent);
//        return rent;
//    }

    public Rent getById(String id){
        try {
            Rent rent = rentRepository.findById(id).get();
            return rent;
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Rent ID : " + id);
        }
    }

    public List<Rent> getManyById(List<String> ids){
        List<Rent> rents = new ArrayList<>();

        for (String id: ids) {
            Rent task = getById(id);
            rents.add(task);
        }

        return rents;
    }

    private Rent save(Rent rent){
        try {
            return rentRepository.save(rent);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
