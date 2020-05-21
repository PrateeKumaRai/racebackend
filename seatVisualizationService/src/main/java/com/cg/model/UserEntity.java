package com.cg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @author praterai
 *
 */
@Entity
@DynamicUpdate
@Table(name = "user_tab")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "user_name", unique = true)
	private String username;

	@Column(name = "emp_name")
	private String empName;

	@Column(name = "password")
	private String password;

	@Column(name = "emp_id", unique = true)
	private Long empId;

	@Column(name = "email_id", unique = true)
	private String emailId;

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@Column(name = "otp")
	private Integer otp;

	@Column(name = "mobile")
	private Long mobile;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "zip")
	private Long zip;
	
	@Column(name = "house_no")
	private String houseNo;
	
	@Column(name = "area")
	private String area;

	

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public Long getZip() {
		return zip;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public String getArea() {
		return area;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZip(Long zip) {
		this.zip = zip;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmpName() {
		return empName;
	}

	public String getPassword() {
		return password;
	}

	public Long getEmpId() {
		return empId;
	}

	public String getEmailId() {
		return emailId;
	}

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

}