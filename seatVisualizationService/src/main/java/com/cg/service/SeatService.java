package com.cg.service;

import java.util.Date;
import java.util.List;

import com.cg.model.CubicalDetailsResponse;

public interface SeatService {

	public List<CubicalDetailsResponse> getUpdatedSeat();

	public Long updateSeatStatus(String emailId, Date startDate, Date returnDate, Boolean seatStatus);

	public Long updateSeatStatusAfterBooking(String emailId,String loginEmail, String seatNo);

	public Boolean resetStatus();

	public Long cancleSeatAfterBooking(String emailId, String loginMail);

	public CubicalDetailsResponse getUpdatedSeatByMail(String mail);

	public List<CubicalDetailsResponse> filter(String country, String city, String office);

}
