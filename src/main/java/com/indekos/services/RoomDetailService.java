package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.RoomDetailCreateRequest;
import com.indekos.model.Room;
import com.indekos.model.RoomPriceDetail;
import com.indekos.repository.RoomDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoomDetailService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RoomDetailRepository roomDetailRepository;
    @Autowired
    RoomService roomService;

    public RoomPriceDetail addRoomDetail(String roomId, RoomDetailCreateRequest request){
        Room room = roomService.getById(roomId);
        RoomPriceDetail newRoomPriceDetail = modelMapper.map(request, RoomPriceDetail.class);
//        newRoomDetail.setRoom(room);
        room.getDetails().add(newRoomPriceDetail);

//        roomDetailRepository.save(newRoomDetail);
        roomService.save(room);

        return newRoomPriceDetail;
    }

    public RoomPriceDetail getByID(String id){
        try {
            return roomDetailRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
}
