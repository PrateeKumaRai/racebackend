package com.capgemini.travel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TraveldetailsReq implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer travel_id;
	
	private Integer employee_id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date start_date;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date return_date;

	private String src_country;
	
	private String dest_country;
	
	private String source_location;
	
	
	private String dest_location;
	
	
	private Boolean status;
	
	
	private String travel_type;
	
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date created_at;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;
	
	//private File file;
	private String fileName;
	
	private String fileType;
	
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getMob_num() {
		return mob_num;
	}

	public void setMob_num(Long mob_num) {
		this.mob_num = mob_num;
	}
	
	

}
