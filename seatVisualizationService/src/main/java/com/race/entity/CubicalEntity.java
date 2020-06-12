package com.race.entity;

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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author praterai
 *
 */

@Entity
@Table(name = "cubical_tab")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ToString
public class CubicalEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "cubical_gen")
	@GenericGenerator(name = "cubical_gen", strategy = "increment")
	private Long id;

	@Column(name = "cubical_number", length = 40)
	private String cubicalNumber;

	@Column(name = "cubical_number_temp")
	private Boolean cubicalNumberTemp;

	@Column(name = "cubical_status")
	private Boolean cubicalStatus;

	@Column(name = "email_id", unique = true, length = 50)
	private String emailId;

	@Column(name = "cubical_location", length = 40)
	private String cubicalLocation;

	@Column(name = "city", length = 50)
	private String city;

	@Column(name = "floor", length = 10)
	private String floor;

	@Column(name = "country", length = 50)
	private String country;

	@Column(name = "zip")
	private Long zip;

	@Column(name = "seat_number_temp", length = 40)
	private String seatNumberTemp;

	@Column(name = "seat_mail_id", length = 40)
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

}
