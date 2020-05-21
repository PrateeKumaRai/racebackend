package com.cg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cubical_tab")
@EntityListeners(AuditingEntityListener.class)
public class CubicalDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "cubical_gen")
	@GenericGenerator(name = "cubical_gen", strategy = "increment")
	private Long id;

	@Column(name = "cubical_number")
	private String cubicalNumber;
	
	@Column(name = "cubical_number_temp")
	private Boolean cubicalNumberTemp;

	@Column(name = "cubical_status")
	private Boolean cubicalStatus;

	@Column(name = "email_id", unique = true)
	private String emailId;

	@Column(name = "cubical_location")
	private String cubicalLocation;

	@Column(name = "city")
	private String city;

	@Column(name = "floor")
	private String floor;

	@Column(name = "conutry")
	private String country;
	
	@Column(name = "zip")
	private Long zip;
	
	@Column(name = "seat_number_temp")
	private String seatNumberTemp;
	
	@Column(name = "seat_mail_id")
	private String seatMailId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "return_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date returnDate;

	

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

	public Boolean getCubicalNumberTemp() {
		return cubicalNumberTemp;
	}

	public void setCubicalNumberTemp(Boolean cubicalNumberTemp) {
		this.cubicalNumberTemp = cubicalNumberTemp;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getCubicalLocation() {
		return cubicalLocation;
	}

	public void setCubicalLocation(String cubicalLocation) {
		this.cubicalLocation = cubicalLocation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCubicalNumber() {
		return cubicalNumber;
	}

	public void setCubicalNumber(String cubicalNumber) {
		this.cubicalNumber = cubicalNumber;
	}

	public Boolean getCubicalStatus() {
		return cubicalStatus;
	}

	public void setCubicalStatus(Boolean cubicalStatus) {
		this.cubicalStatus = cubicalStatus;
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

	public Long getZip() {
		return zip;
	}

	public void setZip(Long zip) {
		this.zip = zip;
	}

	
}
