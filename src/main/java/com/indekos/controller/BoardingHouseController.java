package com.indekos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indekos.model.MainBoardingHouse;
import com.indekos.services.BoardingHouseService;

@RestController
@RequestMapping(value = "/main")
public class BoardingHouseController {

	@Autowired
	private BoardingHouseService boardingHouseService;
	
	@GetMapping
	public MainBoardingHouse getBoardingHouse() {
		return boardingHouseService.getBoardingHouseData();
	}
}