package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.model.Room;
import com.indekos.model.RoomDetail;
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

    public RoomDetail addRoomDetail(String roomId, RoomPriceCreateRequest request){
        Room room = roomService.getById(roomId);
        RoomDetail newRoomDetail = modelMapper.map(request, RoomDetail.class);
//        newRoomDetail.setRoom(room);
        room.getDetails().add(newRoomDetail);

//        roomDetailRepository.save(newRoomDetail);
        roomService.save(room);

        return newRoomDetail;
    }

    public RoomDetail getByID(String id){
        try {
            return roomDetailRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
}
