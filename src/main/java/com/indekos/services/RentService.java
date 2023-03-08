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

    public Rent create(){
        Rent rent = new Rent("0a71db18-6995-411b-8fe6-4b51b0c4ce22",1500000,"Jan",1676879105844L,0);
        rent.create("98e6e4b5-9597-4a61-8499-8fd1567af7ff");
        save(rent);
        return rent;
    }

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
