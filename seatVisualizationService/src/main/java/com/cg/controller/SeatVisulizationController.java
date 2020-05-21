package com.cg.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.CubicalDetailsResponse;
import com.cg.restModel.UpadteSeatStatus;
import com.cg.service.SeatService;

@RestController
@CrossOrigin("*")
@RequestMapping("/seatVisulization")
public class SeatVisulizationController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SeatVisulizationController.class);

	@Autowired
	private SeatService seatService;
	

	@GetMapping("/getUpdatedSeat")
	public List<CubicalDetailsResponse> getUpdatedSeat() {
		LOGGER.info("Inside Controller getUpdatedSeat() method");
		return seatService.getUpdatedSeat();

	}
	
	@PostMapping(path="/filter",  consumes = "application/json", produces = "application/json")
	public List<CubicalDetailsResponse> filter(@RequestBody CubicalDetailsResponse cubicalDetailsResponse) {
		LOGGER.info("Inside Controller filter() method");
		List<CubicalDetailsResponse> response=null;
		if(cubicalDetailsResponse.getCountry()==null || cubicalDetailsResponse.getCity()==null || cubicalDetailsResponse.getCubicalLocation()==null) {
			return response;
		}else {
			response=seatService.filter(cubicalDetailsResponse.getCountry(),cubicalDetailsResponse.getCity(),cubicalDetailsResponse.getCubicalLocation());
		}
		return response;
	}

	@PostMapping("/updatedSeatStatus")
	public Long updateSeatStatus(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller updateSeatStatus() method");
		Long response=null;
		String cubical = upadteSeatStatus.getCubicalStatus();
		if (cubical.equals("a") || cubical.equals("b") || (cubical.equals("c"))) {
			Boolean seatStatus = true;
			Date startDate=upadteSeatStatus.getStartDate();
			Date returnDate=upadteSeatStatus.getReturnDate();
		 response=	seatService.updateSeatStatus(upadteSeatStatus.getEmailId(),startDate,returnDate, seatStatus);
		}
		return response;
	}

	@PostMapping("/updatedSeatStatusAfterBooking")
	public Long updateSeatStatusAfterBooking(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller updateSeatStatusAfterBooking() method");
		Long response=null;
		String loginEmail=upadteSeatStatus.getLoginMail();
		String emailId=upadteSeatStatus.getEmailId();
		String seatNo=upadteSeatStatus.getCubicalNumber();
		if ( loginEmail !=null & emailId !=null & seatNo!=null) {
			 response=seatService.updateSeatStatusAfterBooking(emailId,loginEmail,seatNo);
		}
		return response;
	
	}
	
	@PostMapping("/cancleSeatAfterBooking")
	public Long cancleSeatAfterBooking(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller updateSeatStatusAfterBooking() method");
		Long response=null;
		String emailId=upadteSeatStatus.getEmailId();
		String loginMail=upadteSeatStatus.getLoginMail();
		if (  emailId !=null && loginMail !=null) {
			 response=seatService.cancleSeatAfterBooking(emailId,loginMail);
		}
		return response;
	
	}
	
	@PostMapping("/getUpdatedSeatBymail")
	public CubicalDetailsResponse getSeatByMail(@RequestBody UpadteSeatStatus upadteSeatStatus) {
		LOGGER.info("Inside Controller getUpdatedSeat() method");
		CubicalDetailsResponse response=null;
		String mail=upadteSeatStatus.getEmailId();
		if(mail!=null) {
			 response=seatService.getUpdatedSeatByMail(mail);
		}
		return response;

	}
	

	@GetMapping("/resetStatus")
	public Boolean jobToResetStatus() {
		LOGGER.info("Inside Controller getUpdatedSeat() method");
		return seatService.resetStatus();

	}

}
