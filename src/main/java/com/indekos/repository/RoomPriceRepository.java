package com.indekos.repository;

import com.indekos.model.RoomPriceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomPriceRepository extends JpaRepository<RoomPriceDetail, String> {
}
