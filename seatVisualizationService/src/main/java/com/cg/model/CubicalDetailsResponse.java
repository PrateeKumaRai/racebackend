package com.cg.model;

/**
 * @author praterai
 *
 */
public class CubicalDetailsResponse {
	private String emailId;
	private String cubicalNumber;
	private String cubicalLocation;
	private String city;
	private String country;
	private String floor;
	private String seatNumberTemp;
	private String seatMailId;
	
	
	public String getSeatNumberTemp() {
		return seatNumberTemp;
	}
	public String getSeatMailId() {
		return seatMailId;
	}
	public void setSeatNumberTemp(String seatNumberTemp) {
		this.seatNumberTemp = seatNumberTemp;
	}
	public void setSeatMailId(String seatMailId) {
		this.seatMailId = seatMailId;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCubicalNumber() {
		return cubicalNumber;
	}
	public void setCubicalNumber(String cubicalNumber) {
		this.cubicalNumber = cubicalNumber;
	}
	public String getCubicalLocation() {
		return cubicalLocation;
	}
	public void setCubicalLocation(String cubicalLocation) {
		this.cubicalLocation = cubicalLocation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

	
	
}