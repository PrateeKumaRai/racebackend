package com.race.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author praterai
 *
 */

@Entity
@DynamicUpdate
@Table(name = "user_tab")
@Data
@NoArgsConstructor
@ToString
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

}