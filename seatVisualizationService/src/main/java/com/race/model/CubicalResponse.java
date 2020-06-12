package com.race.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author praterai
 *
 */
@Data
@NoArgsConstructor
@ToString
public class CubicalResponse {
	private String emailId;
	private String cubicalNumber;
	private String cubicalLocation;
	private String city;
	private String country;
	private String floor;
	private String seatNumberTemp;
	private String seatMailId;

}