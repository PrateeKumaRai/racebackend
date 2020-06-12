package com.race.travel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * <h1>Travel Detail Entity class!</h1> This Entity class contains properties
 * details of Travel i.e required for performing some DAO operation.
 * </p>
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-22
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "travel_details")

@EntityListeners(AuditingEntityListener.class)
public class TravelDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "travelid_generator", sequenceName = "travelid_seq", initialValue = 100, allocationSize = 200)
	@GeneratedValue(generator = "travelid_generator")

	@Column(name = "travel_id", updatable = false, nullable = false)
	private Integer travel_id;
	@Column(name = "emp_id")
	@NotNull(message = "empid should not be null or empty")
	private Integer emp_id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "startdate should not be null or empty")
	private Date start_date;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "return_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "returndate should not be null or empty")
	private Date return_date;

	@Column(name = "src_country", length = 60)
	@NotNull(message = "source country should not be null or empty")
	private String src_country;

	@Column(name = "dest_country", length = 60)
	@NotNull(message = "destination country should not be null or empty")
	private String dest_country;

	@Column(name = "src_location", length = 60)
	@NotNull(message = "source location should not be null or empty")
	private String src_location;

	@NotNull(message = "destination location should not be null or empty")
	@Column(name = "dest_location", length = 60)
	private String dest_location;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "travel_type", length = 60)
	private String travel_type;

	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "created_at")
	private Date created_at;

	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updated_at;

	@Column(name = "imgName", length = 50)
	private String imgName;

	@Column(name = "imgType", length = 20)
	private String imgType;

	@Lob
	@Column(name = "imgPic")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] imgPic;

	@NotNull(message = "Mobile Number should not be null or empty")
	@Column(name = "mob_num")
	private Long mob_num;

}
