package com.cg.restModel;

import java.util.Date;

public class UpadteSeatStatus {
	
	private String emailId;
	
	private String loginMail;

	private String cubicalStatus;
	
	private String cubicalNumber;
	
	private Date startDate;
	
	private Date returnDate;
	
	
	

	
	public String getLoginMail() {
		return loginMail;
	}

	public void setLoginMail(String loginMail) {
		this.loginMail = loginMail;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCubicalStatus() {
		return cubicalStatus;
	}

	public void setCubicalStatus(String cubicalStatus) {
		this.cubicalStatus = cubicalStatus;
	}

	public String getCubicalNumber() {
		return cubicalNumber;
	}

	public void setCubicalNumber(String cubicalNumber) {
		this.cubicalNumber = cubicalNumber;
	}
}
