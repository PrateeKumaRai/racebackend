package com.capgemini.race.asset.dto;



public class AssetInActiveDto {

	private Long assetId;

	private Integer assetTypeId;

	private String assetNumber;

	private String ciDesc;

	private String assetModel;


	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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
}
