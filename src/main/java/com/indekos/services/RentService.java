package com.indekos.services;

import com.indekos.model.Rent;
import com.indekos.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private void save(Rent rent){
        try {
            rentRepository.save(rent);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
