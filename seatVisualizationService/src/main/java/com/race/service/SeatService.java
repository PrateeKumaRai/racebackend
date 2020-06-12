package com.race.service;

import java.util.Date;
import java.util.List;

import com.race.model.CubicalResponse;

/**
 * Manage seat for employee
 * 
 * @author praterai
 *
 */
public interface SeatService {

	/**
	 * Get updated seat
	 * 
	 * @param empty { @link empty}
	 * @return { @link CubicalResponse}
	 */
	public List<CubicalResponse> getUpdatedSeat();

	/**
	 * Updated seat status
	 * 
	 * @param emailId,startDate,returnDate,seatStatus { @link String emailId, Date
	 *                                                startDate, Date returnDate,
	 *                                                Boolean seatStatus}
	 * @return { @link Long}
	 */
	public Long updateSeatStatus(String emailId, Date startDate, Date returnDate, Boolean seatStatus);

	/**
	 * Updated seat status after seat booking
	 * 
	 * @param emailId,loginEmail,seatNo { @link String emailId,String loginEmail,
	 *                                  String seatNo}
	 * @return { @link Long }
	 */
	public Long updateSeatStatusAfterBooking(String emailId, String loginEmail, String seatNo);

	/**
	 * Reset seat status
	 * 
	 * @param empty { @link empty }
	 * @return { @link Boolean }
	 */
	public Boolean resetStatus();

	/**
	 * Cancel seat after booking
	 * 
	 * @param emailId,loginEmail { @link String emailId, String loginMail }
	 * @return {@link Long}
	 */
	public Long cancleSeatAfterBooking(String emailId, String loginMail);

	/**
	 * Get updated seat by email
	 * 
	 * @param emailId { @link String emailId }
	 * @return { @link CubicalResponse }
	 */
	public CubicalResponse getUpdatedSeatByMail(String mail);

	/**
	 * Filter seat based on county city and office location
	 * 
	 * @param country, city, office { @link String country, String city, String
	 *                 office) }
	 * @return { @link CubicalResponse }
	 */
	public List<CubicalResponse> filterSeat(String country, String city, String office);

}
