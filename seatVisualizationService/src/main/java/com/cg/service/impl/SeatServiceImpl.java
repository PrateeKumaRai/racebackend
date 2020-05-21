package com.cg.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.model.CubicalDetails;
import com.cg.model.CubicalDetailsResponse;
import com.cg.repo.CubicalRepo;
import com.cg.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private CubicalRepo cubicalRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);

	@Override
	public List<CubicalDetailsResponse> getUpdatedSeat() {
		LOGGER.info("Inside Service  getUpdatedSeat() method ");
		Boolean cubicalStatus = true;
		LocalDate date = LocalDate.now();
		System.out.println(date);
		List<CubicalDetailsResponse> cubicalNumberList = new ArrayList<CubicalDetailsResponse>();
		List<CubicalDetails> cubicalDetailsList = cubicalRepo.findByCubicalStatus(cubicalStatus, date);
		if (cubicalDetailsList != null && !cubicalDetailsList.isEmpty()) {
			for (CubicalDetails cubicalDetails : cubicalDetailsList) {
				CubicalDetailsResponse response = new CubicalDetailsResponse();
				response.setEmailId(cubicalDetails.getEmailId());
				response.setCubicalNumber(cubicalDetails.getCubicalNumber());
				response.setCubicalLocation(cubicalDetails.getCubicalLocation());
				response.setCity(cubicalDetails.getCity());
				response.setCountry(cubicalDetails.getCountry());
				response.setFloor(cubicalDetails.getFloor());
				cubicalNumberList.add(response);
			}
		}
		return cubicalNumberList;
	}

	@Override
	public Long updateSeatStatus(String emailId, Date startDate, Date returnDate, Boolean cubicalStatus) {
		LOGGER.info("Inside Service  updateSeatStatus() method ");
		Long response = null;
		CubicalDetails cubicalDetails = cubicalRepo.findByEmailId(emailId);
		if (cubicalDetails != null) {
			String cubicalNumber = cubicalDetails.getCubicalNumber();
			if (cubicalNumber != null) {
				cubicalRepo.updateSeatStatus(cubicalStatus, startDate, returnDate, emailId);
				response = 200l;
			} else {
				response = 202l;
			}
		} else {
			response = 201l;
		}
		return response;
	}

	@Override
	public Long updateSeatStatusAfterBooking(String emailId, String loginEmail, String seatNo) {
		LOGGER.info("Inside Service  updateSeatStatusAfterBooking() method ");
		Boolean cubicalNumberTemp = true;
		Long returnStatus = null;
		Boolean cubicalStatus = false;
		try {
			CubicalDetails cd = cubicalRepo.findByEmailId(loginEmail);
			if (cd == null) {
				CubicalDetails cubicalDetails = new CubicalDetails();
				cubicalDetails.setEmailId(loginEmail);
				cubicalDetails.setCubicalNumberTemp(cubicalNumberTemp);
				cubicalDetails.setSeatNumberTemp(seatNo);
				cubicalDetails.setSeatMailId(emailId);
				CubicalDetails cubical = cubicalRepo.save(cubicalDetails);
				if (cubical.equals(null) || cubical == null) {
					returnStatus = (long) 201;

				} else {
					cubicalRepo.updateSeatStatusAfterBookingOwner(cubicalStatus, emailId);
					returnStatus = 200l;
				}
			} else {
				if (cd.getCubicalNumberTemp() != true & cd.getCubicalNumber() == null) {
					cubicalRepo.updateSeatStatusAfterBookingTemp(cubicalNumberTemp, emailId, seatNo, loginEmail);

					cubicalRepo.updateSeatStatusAfterBookingOwner(cubicalStatus, emailId);
					returnStatus = 200l;
				} else {
					returnStatus = (long) 201;

				}
			}
		} catch (Exception e) {
			returnStatus = (long) 202;
			return returnStatus;
		}
		return returnStatus;
	}

	@Override
	public Boolean resetStatus() {
		LocalDate date = LocalDate.now();
		Boolean response = false;
		Boolean flag = true;
		Boolean tempFlag = false;
		try {
			cubicalRepo.jobResetSeatStatus(flag, date);
			cubicalRepo.jobResetTempBooking(tempFlag, flag);
			response = true;
		} catch (Exception e) {
			return response;
		}
		return response;
	}

	@Override
	public Long cancleSeatAfterBooking(String emailId, String loginMail) {
		Long returnStatus = null;
		Boolean cubicalStatus = true;
		String tempSeat=null;
		String tempSeatMail=null;
		Boolean cubicalNumberTemp=false;
		if(emailId !=null) {
			 cubicalRepo.updateSeatStatusAfterBookingOwner(cubicalStatus, emailId);
			 returnStatus=200l;
			 if(returnStatus==200) {
				   cubicalRepo.clearSeatDataAfterUnbook(tempSeatMail,tempSeat,cubicalNumberTemp,loginMail);
				   returnStatus=200l;
			 }else {
				 returnStatus=201l;
			 }
		}
		return returnStatus;
	}

	@Override
	public CubicalDetailsResponse getUpdatedSeatByMail(String mail) {
		CubicalDetailsResponse response = new CubicalDetailsResponse();
		CubicalDetails cubicalDetails = cubicalRepo.findByCubicalMail(mail);
		if (cubicalDetails != null) {
			response.setSeatMailId(cubicalDetails.getSeatMailId());
			response.setSeatNumberTemp(cubicalDetails.getSeatNumberTemp());
		}else{
			return response;
		}
		return response;
	}

	@Override
	public List<CubicalDetailsResponse> filter(String country, String city, String office) {
		LOGGER.info("Inside Service  getUpdatedSeat() method ");
		Boolean cubicalStatus = true;
		LocalDate date = LocalDate.now();
		System.out.println(date);
		List<CubicalDetailsResponse> cubicalNumberList = new ArrayList<CubicalDetailsResponse>();
		List<CubicalDetails> cubicalDetailsList = cubicalRepo.filter(country,city,office,cubicalStatus, date);
		if (cubicalDetailsList != null && !cubicalDetailsList.isEmpty()) {
			for (CubicalDetails cubicalDetails : cubicalDetailsList) {
				CubicalDetailsResponse response = new CubicalDetailsResponse();
				response.setEmailId(cubicalDetails.getEmailId());
				response.setCubicalNumber(cubicalDetails.getCubicalNumber());
				response.setCubicalLocation(cubicalDetails.getCubicalLocation());
				response.setCity(cubicalDetails.getCity());
				response.setCountry(cubicalDetails.getCountry());
				response.setFloor(cubicalDetails.getFloor());
				cubicalNumberList.add(response);
			}
		}
		return cubicalNumberList;
	}
	
	
}
