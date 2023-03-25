package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.request.RoomDetailsCreateRequest;
import com.indekos.dto.request.RoomPriceCreateRequest;
import com.indekos.model.MasterRoomDetailCategory;
import com.indekos.model.Room;
import com.indekos.model.RoomDetail;
import com.indekos.model.RoomPriceDetail;
import com.indekos.repository.MasterRoomDetailCategoryRepository;
import com.indekos.repository.RoomDetailRepository;
import com.indekos.repository.RoomPriceDetailRepository;
import com.indekos.utils.Constant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.PostConstruct;

@Service
public class RoomDetailService {
	
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    MasterRoomDetailCategoryRepository masterRoomDetailCategoryRepository;
    
    @Autowired
    RoomDetailRepository roomDetailRepository;
    
    @Autowired
    RoomPriceDetailRepository roomPriceDetailRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /* ============================== ROOM CATEGORY DETAIL ============================== */
    @PostConstruct
    public void initializeMasterRoomDetailCategory() {
    	jdbcTemplate.update("INSERT IGNORE INTO master_room_detail_category (name) VALUES ('" + Constant.KAMAR_TIDUR + "')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_room_detail_category (name) VALUES ('" + Constant.KAMAR_MANDI + "')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_room_detail_category (name) VALUES ('" + Constant.FURNITURE + "')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_room_detail_category (name) VALUES ('" + Constant.ALAT_ELEKTRONIK + "')");
    	jdbcTemplate.update("INSERT IGNORE INTO master_room_detail_category (name) VALUES ('" + Constant.FASILITAS_KAMAR_LAINNYA + "')");
    }
    
    private MasterRoomDetailCategory getRoomDetailCategoryByName(String categoryName) {
    	return masterRoomDetailCategoryRepository.findByName(categoryName);
    }
    
    public List<MasterRoomDetailCategory> getRoomDetailCategory() {
    	return masterRoomDetailCategoryRepository.findAll();
    }
    
    /* ============================== ROOM DETAIL ============================== */
    void initializeDefaultRoomFacility(Room room) {
    	
    	RoomDetail facility1 = new RoomDetail("Luas Ruangan", getRoomDetailCategoryByName(Constant.KAMAR_TIDUR), room);
    	roomDetailRepository.save(facility1);
    	
    	RoomDetail facility2 = new RoomDetail("Listrik", getRoomDetailCategoryByName(Constant.KAMAR_TIDUR), room);
    	roomDetailRepository.save(facility2);
    	
    	RoomDetail facility3 = new RoomDetail("Kasur", getRoomDetailCategoryByName(Constant.KAMAR_TIDUR), room);
    	roomDetailRepository.save(facility3);
    	
    	RoomDetail facility4 = new RoomDetail("Bantal", getRoomDetailCategoryByName(Constant.KAMAR_TIDUR), room);
    	roomDetailRepository.save(facility4);
    	
    	RoomDetail facility5 = new RoomDetail("Guling", getRoomDetailCategoryByName(Constant.KAMAR_TIDUR), room);
    	roomDetailRepository.save(facility5);
    	
    	RoomDetail facility6 = new RoomDetail("Kamar Mandi Dalam", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility6);
    	
    	RoomDetail facility7 = new RoomDetail("Kloset Jongkok", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility7);
    	
    	RoomDetail facility8 = new RoomDetail("Kloset Duduk", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility8);

    	RoomDetail facility9 = new RoomDetail("Kran", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility9);

    	RoomDetail facility10 = new RoomDetail("Shower", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility10);
    	
    	RoomDetail facility11 = new RoomDetail("Air Panas", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility11);
    	
    	RoomDetail facility12 = new RoomDetail("Bak Mandi", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility12);
    	
    	RoomDetail facility13 = new RoomDetail("Wastafel", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility13);
    	
    	RoomDetail facility14 = new RoomDetail("Cermin", getRoomDetailCategoryByName(Constant.KAMAR_MANDI), room);
    	roomDetailRepository.save(facility14);
    	
    	RoomDetail facility15 = new RoomDetail("Lemari Baju", getRoomDetailCategoryByName(Constant.FURNITURE), room);
    	roomDetailRepository.save(facility15);
    	
    	RoomDetail facility16 = new RoomDetail("Meja", getRoomDetailCategoryByName(Constant.FURNITURE), room);
    	roomDetailRepository.save(facility16);
    	
    	RoomDetail facility17 = new RoomDetail("Kursi", getRoomDetailCategoryByName(Constant.FURNITURE), room);
    	roomDetailRepository.save(facility17);

    	RoomDetail facility18 = new RoomDetail("WiFi", getRoomDetailCategoryByName(Constant.ALAT_ELEKTRONIK), room);
    	roomDetailRepository.save(facility18);

    	RoomDetail facility19 = new RoomDetail("AC", getRoomDetailCategoryByName(Constant.ALAT_ELEKTRONIK), room);
    	roomDetailRepository.save(facility19);

    	RoomDetail facility20 = new RoomDetail("Kipas Angin", getRoomDetailCategoryByName(Constant.ALAT_ELEKTRONIK), room);
    	roomDetailRepository.save(facility20);
    }
    
    public RoomDetail getByID(Long id){
    	RoomDetail targetRoomDetail = roomDetailRepository.findById(id)
    			.orElseThrow(() -> new InvalidRequestIdException("Invalid Room Detail ID"));

    	return targetRoomDetail;
    }
    
    public List<RoomDetail> getDetailsByRoom(Room room) {
    	return roomDetailRepository.findByRoom(room);
    }
    
    public RoomDetail addRoomDetail(Room room, RoomDetailsCreateRequest request){
        RoomDetail newRoomDetail = new RoomDetail();
        newRoomDetail.setName(request.getName());
        newRoomDetail.setDescription(request.getDescription());
        newRoomDetail.setEnable(request.isEnable());
        newRoomDetail.setMasterRoomDetailCategory(masterRoomDetailCategoryRepository.findByName(request.getCategory()));
        newRoomDetail.setRoom(room);
        return roomDetailRepository.save(newRoomDetail);
    }
    
    public RoomDetail editRoomDetail(Long roomDetailId, RoomDetailsCreateRequest request, Room room){
        RoomDetail roomDetail = roomDetailRepository.findByRoomAndId(room, roomDetailId);
        if(roomDetail == null)
        	throw new InvalidRequestIdException("Invalid Room Detail ID");
        
        roomDetail.setName(request.getName());
        roomDetail.setDescription(request.getDescription());
        roomDetail.setEnable(request.isEnable());
        roomDetail.setMasterRoomDetailCategory(masterRoomDetailCategoryRepository.findByName(request.getCategory()));
        return roomDetailRepository.save(roomDetail);
    }
    
    public RoomDetail removeRoomDetail(Long roomDetailId, Room room) {
    	RoomDetail roomDetail = roomDetailRepository.findByRoomAndId(room, roomDetailId);
    	if(roomDetail == null)
        	throw new InvalidRequestIdException("Invalid Room Detail ID");
    	
    	RoomDetail deleted = roomDetail;
    	roomDetailRepository.deleteById(roomDetailId);
    	return deleted;
    }
    
    /* ============================== ROOM PRICE DETAIL ============================== */
    public List<RoomPriceDetail> getPriceDetailsByRoom(Room room) {
    	return roomPriceDetailRepository.findByRoom(room);
    }
    
    public RoomPriceDetail addRoomPrice(Room room, RoomPriceCreateRequest request){
        RoomPriceDetail newRoomPriceDetail = new RoomPriceDetail();
        newRoomPriceDetail.setPrice(request.getPrice());
        newRoomPriceDetail.setCapacity(request.getCapacity());
        newRoomPriceDetail.setRoom(room);
        return roomPriceDetailRepository.save(newRoomPriceDetail);
    }
    
    public RoomPriceDetail editRoomPrice(Long roomPriceDetailId, RoomPriceCreateRequest request, Room room){
        RoomPriceDetail roomPriceDetail = roomPriceDetailRepository.findByRoomAndId(room, roomPriceDetailId);
        if(roomPriceDetail == null)
        	throw new InvalidRequestIdException("Invalid Room Price Detail ID");
        
        roomPriceDetail.setPrice(request.getPrice());
        roomPriceDetail.setCapacity(request.getCapacity());
        return roomPriceDetailRepository.save(roomPriceDetail);
    }
    
    public RoomPriceDetail removeRoomPrice(Long roomPriceDetailId, Room room) {
    	RoomPriceDetail roomPriceDetail = roomPriceDetailRepository.findByRoomAndId(room, roomPriceDetailId);
    	if(roomPriceDetail == null)
        	throw new InvalidRequestIdException("Invalid Room Price Detail ID");
    	
    	RoomPriceDetail deleted = roomPriceDetail;
    	roomPriceDetailRepository.deleteById(roomPriceDetailId);
    	return deleted;
    }
}