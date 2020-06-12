package com.race.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.race.constant.Response;
import com.race.entity.CubicalEntity;
import com.race.model.CubicalResponse;
import com.race.repo.CubicalRepo;
import com.race.service.SeatService;

/**
 * Manage seat for employee
 * 
 * @author praterai
 *
 */
@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private CubicalRepo cubicalRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);

	/**
	 * Get updated seat
	 * 
	 * @param empty { @link empty}
	 * @return { @link CubicalResponse}
	 */
	@Override
	public List<CubicalResponse> getUpdatedSeat() {
		LOGGER.info("Inside Service  getUpdatedSeat() method ");

		Boolean cubicalStatus = true;
		LocalDate localDate = LocalDate.now();

		List<CubicalResponse> cubicalNumberList = new ArrayList<CubicalResponse>();
		List<CubicalEntity> cubicalDetailsList = cubicalRepo.findByCubicalStatus(cubicalStatus, localDate);
		if (cubicalDetailsList != null && !cubicalDetailsList.isEmpty()) {
			for (CubicalEntity cubicalEntity : cubicalDetailsList) {
				CubicalResponse response = new CubicalResponse();
				response.setEmailId(cubicalEntity.getEmailId());
				response.setCubicalNumber(cubicalEntity.getCubicalNumber());
				response.setCubicalLocation(cubicalEntity.getCubicalLocation());
				response.setCity(cubicalEntity.getCity());
				response.setCountry(cubicalEntity.getCountry());
				response.setFloor(cubicalEntity.getFloor());
				cubicalNumberList.add(response);
				
			}
		}
		LOGGER.info("End Service  getUpdatedSeat() method ");
		return cubicalNumberList;
	}

	/**
	 * Updated seat status
	 * 
	 * @param emailId,startDate,returnDate,seatStatus { @link String emailId, Date
	 *                                                startDate, Date returnDate,
	 *                                                Boolean seatStatus}
	 * @return { @link Long}
	 */
	@Override
	public Long updateSeatStatus(String emailId, Date startDate, Date returnDate, Boolean cubicalStatus) {

		LOGGER.info("Inside Service  updateSeatStatus() method ");

		Long response = null; 

		CubicalEntity cubicalEntity = cubicalRepo.findByEmailId(emailId);
		if (cubicalEntity != null) {
			String cubicalNumber = cubicalEntity.getCubicalNumber();
			if (cubicalNumber != null) {
				cubicalRepo.updateSeatStatus(cubicalStatus, startDate, returnDate, emailId);
				response = Response.R1;
			} else {
				response = Response.R3;
			}
		} else {
			LOGGER.info("End Service  updateSeatStatus() method ");
			response = Response.R2;
		}
		return response;
	}

	/**
	 * Updated seat status after seat booking
	 * 
	 * @param emailId,loginEmail,seatNo { @link String emailId,String loginEmail,
	 *                                  String seatNo}
	 * @return { @link Long }
	 */
	@Override
	public Long updateSeatStatusAfterBooking(String emailId, String loginEmail, String seatNo) {

		LOGGER.info("Inside Service  updateSeatStatusAfterBooking() method ");

		Long returnStatus = null;
		Boolean cubicalStatus = false;
		Boolean cubicalNumberTemp = true; 

		try {
			CubicalEntity cubicalEntityResp = cubicalRepo.findByEmailId(loginEmail);
			if (cubicalEntityResp == null) {
				CubicalEntity cubicalEntity = new CubicalEntity();
				cubicalEntity.setEmailId(loginEmail);
				cubicalEntity.setCubicalNumberTemp(cubicalNumberTemp);
				cubicalEntity.setSeatNumberTemp(seatNo);
				cubicalEntity.setSeatMailId(emailId);
				CubicalEntity cubical = cubicalRepo.save(cubicalEntity);
				if (cubical.equals(null) || cubical == null) {
					returnStatus = Response.R2;

				} else {
					cubicalRepo.updateSeatStatusAfterBookingOwner(cubicalStatus, emailId);
					returnStatus = Response.R1;
				}
			} else {
				if (cubicalEntityResp.getCubicalNumberTemp() != true & cubicalEntityResp.getCubicalNumber() == null) {
					cubicalRepo.updateSeatStatusAfterBookingTemp(cubicalNumberTemp, emailId, seatNo, loginEmail);
					cubicalRepo.updateSeatStatusAfterBookingOwner(cubicalStatus, emailId);
					returnStatus = Response.R1;
				} else {
					returnStatus = Response.R2;
				}
			}
		} catch (Exception e) {
			returnStatus = Response.R3;
			return returnStatus;
		}
		LOGGER.info("Inside End  updateSeatStatusAfterBooking() method ");
		return returnStatus;
	}

	/**
	 * Cancel seat after booking
	 * 
	 * @param emailId,loginEmail { @link String emailId, String loginMail }
	 * @return {@link Long}
	 */
	@Override
	public Long cancleSeatAfterBooking(String emailId, String loginMail) {

		LOGGER.info("Inside Service  cancleSeatAfterBooking() method ");

		Long returnStatus = null;
		Boolean cubicalStatus = true;
		String tempSeat = null;
		String tempSeatMail = null;
		Boolean cubicalNumberTemp = false;

		if (emailId != null) { 
			cubicalRepo.updateSeatStatusAfterBookingOwner(cubicalStatus, emailId);
			returnStatus = Response.R1;
			if (returnStatus == Response.R1) {
				cubicalRepo.clearSeatDataAfterUnbook(tempSeatMail, tempSeat, cubicalNumberTemp, loginMail);
				returnStatus = Response.R1;
			} else {
				returnStatus = Response.R2;
			}
		}
		LOGGER.info("End Service  cancleSeatAfterBooking() method ");
		return returnStatus;
	}

	/**
	 * Get updated seat by email
	 * 
	 * @param emailId { @link String emailId }
	 * @return { @link CubicalResponse }
	 */
	@Override
	public CubicalResponse getUpdatedSeatByMail(String mail) { 

		LOGGER.info("Inside Service  getUpdatedSeatByMail() method "); 

		CubicalResponse response = new CubicalResponse();
		CubicalEntity cubicalEntity = cubicalRepo.findByEmailId(mail);
		if (cubicalEntity != null) {
			response.setSeatMailId(cubicalEntity.getSeatMailId());
			response.setSeatNumberTemp(cubicalEntity.getSeatNumberTemp());
		} else {
			return response; 
		}
		LOGGER.info("End Service  getUpdatedSeatByMail() method ");
		return response;
	}

	/**
	 * Filter seat based on county city and office location
	 * 
	 * @param country, city, office { @link String country, String city, String
	 *                 office) }
	 * @return { @link CubicalResponse }
	 */
	@Override
	public List<CubicalResponse> filterSeat(String country, String city, String office) {

		LOGGER.info("Inside Service  filterSeat() method ");

		Boolean cubicalStatus = true;
		LocalDate date = LocalDate.now();

		List<CubicalResponse> cubicalNumberList = new ArrayList<CubicalResponse>();
		List<CubicalEntity> cubicalDetailsList = cubicalRepo.filter(country, city, office, cubicalStatus, date);
		if (cubicalDetailsList != null && !cubicalDetailsList.isEmpty()) {
			for (CubicalEntity cubicalEntity : cubicalDetailsList) {
				CubicalResponse response = new CubicalResponse(); 
				response.setEmailId(cubicalEntity.getEmailId());
				response.setCubicalNumber(cubicalEntity.getCubicalNumber());
				response.setCubicalLocation(cubicalEntity.getCubicalLocation());
				response.setCity(cubicalEntity.getCity());
				response.setCountry(cubicalEntity.getCountry());
				response.setFloor(cubicalEntity.getFloor());
				cubicalNumberList.add(response);
			}
		}
		LOGGER.info("End Service  filterSeat() method ");
		return cubicalNumberList;
	}

	/**
	 * Reset seat status
	 * 
	 * @param empty { @link empty }
	 * @return { @link Boolean }
	 */
	@Override
	public Boolean resetStatus() {

		LOGGER.info("Inside Service  resetStatus() method ");

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
		LOGGER.info("End Service  resetStatus() method ");
		return response;
	}
}
