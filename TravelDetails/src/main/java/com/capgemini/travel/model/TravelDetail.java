package com.capgemini.travel.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <h1>Travel Detail Entity class!</h1> This Entity class contains properties
 * details of Travel i.e required for performing some DAO operation.
 * 
 *
 * @author Sunil
 * @version 1.0
 * @since 2020-01-21
 */

@Entity
@Table(name = "travel_details") // used to provide the employee Travel detail table that this entity will be
								// mapped to
@EntityListeners(AuditingEntityListener.class)
public class TravelDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "mySeqGen", sequenceName = "mySeq", initialValue = 100, allocationSize = 200)
	@GeneratedValue(generator = "mySeqGen")
	/*
	 * @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
	 * 
	 * @GenericGenerator(name = "increment", strategy = "increment")
	 */

	@Column(name = "travel_id", updatable = false, nullable = false)
	private Integer travel_id;
	@NotNull
	@Column(name = "employee_id")
	private Integer employee_id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date start_date;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "return_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date return_date;

	@Column(name = "src_country")
    private String src_country;
	
	@Column(name = "dest_country")
	private String dest_country;
	
	@Column(name = "source_location")
	private String source_location;

	@Column(name = "dest_location")
	private String dest_location;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "travel_type")
	private String travel_type;

	/*
	 * @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 */
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "created_at")
	private Date created_at;

	/*
	 * @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 */
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updated_at;

	@Column(name = "imgName")
	private String imgName;

	@Column(name = "imgType")
	private String imgType;

	@Lob
	@Column(name = "imgPic")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] imgPic;

	@Column(name = "mob_num")
	private Long mob_num;

	public Integer getTravel_id() {
		return travel_id;
	}

	public void setTravel_id(Integer travel_id) {
		this.travel_id = travel_id;
	}

	public Integer getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	public String getSrc_country() {
		return src_country;
	}

	public void setSrc_country(String src_country) {
		this.src_country = src_country;
	}

	public String getDest_country() {
		return dest_country;
	}

	public void setDest_country(String dest_country) {
		this.dest_country = dest_country;
	}

	public String getSource_location() {
		return source_location;
	}

	public void setSource_location(String source_location) {
		this.source_location = source_location;
	}

	public String getDest_location() {
		return dest_location;
	}

	public void setDest_location(String dest_location) {
		this.dest_location = dest_location;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTravel_type() {
		return travel_type;
	}

	public void setTravel_type(String travel_type) {
		this.travel_type = travel_type;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public byte[] getImgPic() {
		return imgPic;
	}

	public void setImgPic(byte[] imgPic) {
		this.imgPic = imgPic;
	}

	public Long getMob_num() {
		return mob_num;
	}

	public void setMob_num(Long mob_num) {
		this.mob_num = mob_num;
	}

	@Override
	public String toString() {
		return "TravelDetail [travel_id=" + travel_id + ", employee_id=" + employee_id + ", start_date=" + start_date
				+ ", return_date=" + return_date + ", src_country=" + src_country + ", dest_country=" + dest_country
				+ ", source_location=" + source_location + ", dest_location=" + dest_location + ", status=" + status
				+ ", travel_type=" + travel_type + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", imgName=" + imgName + ", imgType=" + imgType + ", imgPic=" + Arrays.toString(imgPic) + ", mob_num="
				+ mob_num + "]";
	}

	

	
}
