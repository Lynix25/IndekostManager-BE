package com.indekos.dto.response;

import java.util.List;

import com.indekos.model.Room;
import com.indekos.model.RoomDetail;
import com.indekos.model.RoomPriceDetail;

import lombok.Data;

@Data
public class RoomWithDetails {
	
	Room room;
	List<RoomPriceDetail> prices;
	List<RoomDetail> details;
}
