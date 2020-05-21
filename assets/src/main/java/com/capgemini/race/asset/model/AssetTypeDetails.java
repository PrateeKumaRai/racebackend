package com.capgemini.race.asset.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "assettype")
public class AssetTypeDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "asset_type_id")
	private int assetTypeId;

	@Column(name = "asset_type_name")
	private String assetTypeName;

	public int getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

}
