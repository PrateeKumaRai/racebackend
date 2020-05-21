package com.cg.model;

/**
 * @author praterai
 *
 */
public class UserModel {
	private String username;
	private String password;
	private long id;
	private String empName;
	private Long empId;
	private String emailId;
	private Integer otp;
	private String cubical;
	private Long mobile;
	private String cubicalLocation;
	private String city;
	private String country;
	private Long zip;
	private String houseNo;
	private String area;
	private String floor;
	
	public String getHouseNo() {
		return houseNo;
	}

	public String getArea() {
		return area;
	}

	public String getFloor() {
		return floor;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setFloor(String floor) {
		this.floor = floor;
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

	public Long getZip() {
		return zip;
	}

	public void setZip(Long zip) {
		this.zip = zip;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCubical() {
		return cubical;
	}

	public void setCubical(String cubical) {
		this.cubical = cubical;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

}