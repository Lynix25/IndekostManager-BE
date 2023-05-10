package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.SimpleUserDTO;
import com.indekos.dto.TaskDTO;
import com.indekos.model.*;
import com.indekos.repository.RentRepository;

import com.indekos.repository.RoomRepository;
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

    @Autowired
    RoomService roomService;

    @Autowired
    UserService userService;

    @Autowired
    BoardingHouseService boardingHouseService;

    public List<Rent> getAllUnpaid(String userId){
        return rentRepository.findAllByUser(userId);
    }

    public void generateAllRent(){
        MainBoardingHouse mainBoardingHouse = boardingHouseService.getBoardingHouseData();
        List<Room> rooms = roomService.getAll2();

        for(Room room : rooms){
            List<User> users = userService.getAllByRoom(room);
            for(User user : users){
                Rent rent = new Rent(roomService.getPriceDetailsByRoom(room.getId()).get(users.size()-1).getPrice(),"Jan", System.currentTimeMillis() + (86400000 * mainBoardingHouse.getToleranceOverduePaymentInDays()), 0, room, user,null);
                rent.create("System");
                save(rent);
            }
        }

    }

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
