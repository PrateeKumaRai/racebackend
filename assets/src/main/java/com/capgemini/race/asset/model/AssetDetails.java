package com.capgemini.race.asset.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // used to mark the class as a persistent Java class
@Table(name = "asset") // used to provide the details of the table that this entity will be mapped to
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true) // is a Jackson annotation.
//Spring Boot uses Jackson for Serializing and Deserializing Java objects to and from JSON
public class AssetDetails {

	
	
	
	//@Id // annotation is used to define the primary key
	//@GeneratedValue(strategy = GenerationType.AUTO) // used to define the primary key generation strategy
	
	
	
	@Id // annotation is used to define the primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // used to define the primary key generation strategy
	@Column(name = "asset_id")
	private Long assetId;

	@OneToOne
	@JoinColumn(name = "asset_type_id")
	private AssetTypeDetails assetTypeDetails;

	@Column(name = "asset_number")
	private String assetNumber;

	@Column(name = "ci_desc")
	private String ciDesc;

	@Column(name = "asset_model")
	private String assetModel;

	@Column(name = "emp_id")
	private String empId;
	
	@Column(name = "status")
	private String status;
	


	@CreationTimestamp
	@Column(name = "asset_created")
	private Date assetCreated;

	@UpdateTimestamp
	@Column(name = "asset_updated")
	private Date assetUpdated;

	@Column(name = "asset_owner")
	private String assetOwner;

	/*@Column(name = "vdi_id")
	private String vdiId;

	@Column(name = "mobile_no")
	private Long mobileNo;

	@Column(name = "imei_no")
	private Long imeiNo;*/

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public AssetTypeDetails getAssetTypeDetails() {
		return assetTypeDetails;
	}

	public void setAssetTypeDetails(AssetTypeDetails assetTypeDetails) {
		this.assetTypeDetails = assetTypeDetails;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getCiDesc() {
		return ciDesc;
	}

	public void setCiDesc(String ciDesc) {
		this.ciDesc = ciDesc;
	}

	public String getAssetModel() {
		return assetModel;
	}

	public void setAssetModel(String assetModel) {
		this.assetModel = assetModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*public String getVdiId() {
		return vdiId;
	}

	public void setVdiId(String vdiId) {
		this.vdiId = vdiId;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(Long imeiNo) {
		this.imeiNo = imeiNo;
	}*/

	public Date getAssetUpdated() {
		return assetUpdated;
	}

	public void setAssetUpdated(Date assetUpdated) {
		this.assetUpdated = assetUpdated;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public String getAssetOwner() {
		return assetOwner;
	}

	public void setAssetOwner(String assetOwner) {
		this.assetOwner = assetOwner;
	}

	public Date getAssetCreated() {
		return assetCreated;
	}

	public void setAssetCreated(Date assetCreated) {
		this.assetCreated = assetCreated;
	}

}
