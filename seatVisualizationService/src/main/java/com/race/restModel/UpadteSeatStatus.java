package com.race.restModel;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author praterai
 *
 */
@Data
@NoArgsConstructor
@ToString
public class UpadteSeatStatus {
	
	private String emailId;
	
	private String loginMail;

	private String cubicalStatus;
	
	private String cubicalNumber;
	
	private Date startDate;
	
	private Date returnDate;
	
}
