package com.indekos.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indekos.common.helper.exception.ResourceNotFoundException;
import com.indekos.model.MainBoardingHouse;
import com.indekos.repository.BoardingHouseRepository;
import com.indekos.utils.Constant;

@Service
public class BoardingHouseService {
	
	@Autowired
	private BoardingHouseRepository boardingHouseRepository;
	
	@PostConstruct
	private void initializeBoardingHouse() {
		
		MainBoardingHouse boardingHouse = new MainBoardingHouse();
		
		boardingHouse.setId((long) 1);
		boardingHouse.setName("Kos Masbro");
		boardingHouse.setAddress("Jalan Sehat No. 1");
		boardingHouse.setRT(002);
		boardingHouse.setRW(003);
		boardingHouse.setSubdistrict("Pasar Lama");
		boardingHouse.setDistrict("Sungai Utara");
		boardingHouse.setCityOrRegency("Kota Baru");
		boardingHouse.setProvince("Jawa Barat");
		boardingHouse.setCountry("Indonesia");
		boardingHouse.setPostalCode(123456);
		
		boardingHouse.setRentPaymentType(Constant.PRABAYAR);
		boardingHouse.setRentPaymentTypePeriod(Constant.BULANAN);
		boardingHouse.setDueDatePerPeriod(1);
		boardingHouse.setToleranceOverduePaymentInDays(3);
		boardingHouse.setPenaltyFeePerOverduePaymentInDays(50000);
		
		boardingHouseRepository.save(boardingHouse);
	}
	
	public MainBoardingHouse getBoardingHouseData() {
		MainBoardingHouse boardingHouse = boardingHouseRepository.findById((long) 1)
				.orElseThrow(() -> new ResourceNotFoundException("Tidak ada data indekos"));
		
		return boardingHouse;
	}
}