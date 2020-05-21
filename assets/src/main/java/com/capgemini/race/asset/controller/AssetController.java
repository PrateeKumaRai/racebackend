package com.capgemini.race.asset.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.capgemini.race.asset.dto.AssetDetailsDto;
import com.capgemini.race.asset.dto.AssetInActiveDto;
import com.capgemini.race.asset.service.impl.AssetServiceImpl;

@RestController
@RequestMapping("/asset")
@CrossOrigin("*")
public class AssetController {

	@Autowired
	AssetServiceImpl assetServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(AssetController.class);

	@GetMapping("/assets")
	public List<AssetDetailsDto> getAllAssets() {
		return assetServiceImpl.getAllAssests();
	}

	@GetMapping("/assetType")
	public AssetDetailsDto getAssetById(@RequestParam int asset_id) {
		return assetServiceImpl.getAssetById(asset_id);

	}

	@GetMapping("/getasset")
	public List<AssetDetailsDto> getAssetByempid(@RequestParam int emp_id) {
		return assetServiceImpl.getAssetByempid(emp_id);
	}

	@PostMapping("/assets")
	@ResponseBody
	public void createAsset(@Valid @RequestBody AssetDetailsDto assetDetailsDto) {
		assetServiceImpl.createAsset(assetDetailsDto);

	}

	@PutMapping("/assets/{assetId}")
	public String updateAsset(@PathVariable(value = "assetId") int assetId,
			@Valid @RequestBody AssetDetailsDto assetDetailsDto) {
		return assetServiceImpl.updateAsset(assetId, assetDetailsDto);
	}

	@DeleteMapping("assets/{assetId}")
	public String deleteAsset(@PathVariable(value = "assetId") int assetId) {
		return assetServiceImpl.deleteAsset(assetId);
	}
	


	@GetMapping("/asset")
	public int getAssetCount() {
		return assetServiceImpl.getAssetCount();

	}

	@GetMapping("/assetsInActive")
	public AssetInActiveDto getInActiveAssets() {
		return assetServiceImpl.getAssetInAcive();

	}

}
