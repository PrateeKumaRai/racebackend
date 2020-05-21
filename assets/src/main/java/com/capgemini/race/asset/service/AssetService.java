package com.capgemini.race.asset.service;

import java.util.List;

import com.capgemini.race.asset.dto.AssetDetailsDto;
import com.capgemini.race.asset.dto.AssetInActiveDto;

public interface AssetService {

	public List<AssetDetailsDto> getAllAssests();

	public AssetDetailsDto getAssetById(int assetId);
	
	public List<AssetDetailsDto> getAssetByempid(int emp_id);
	
	public void createAsset(AssetDetailsDto assetDetailsDto);

	public String updateAsset(int assetId, AssetDetailsDto assetDetailsDto);

	public String deleteAsset(int assetId);

	public AssetInActiveDto getAssetInAcive();

	
}
