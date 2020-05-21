package com.capgemini.race.asset.dto;


import java.util.Date;

public class AssetDetailsDto{


	private Long assetId;

	private Integer assetTypeId;

	private String assetNumber;

	private Date assetCreated;

	private String ciDesc;

	private String assetModel;

	private String assetOwner;

	private String status;

	private String empId;

	private Date assetUpdated;

	private String assetTypeName;

//	private String vdiId;

//	private Long mobileNo;

//	private Long imeiNo;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/*public Long getMobileNo() {
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
	}

	public String getVdiId() {
		return vdiId;
	}

	public void setVdiId(String vdiId) {
		this.vdiId = vdiId;
	}*/

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

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

	public Date getAssetCreated() {
		return assetCreated;
	}

	public void setAssetCreated(Date assetCreated) {
		this.assetCreated = assetCreated;
	}

	public Integer getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(Integer assetTypeId) {
		this.assetTypeId = assetTypeId;
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

	public String getAssetOwner() {
		return assetOwner;
	}

	public void setAssetOwner(String assetOwner) {
		this.assetOwner = assetOwner;
	}

}
