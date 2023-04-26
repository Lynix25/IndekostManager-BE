package com.indekos.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.MainBoardingHouse;

@Repository
public interface BoardingHouseRepository extends JpaRepository<MainBoardingHouse, Long> {
	
}
