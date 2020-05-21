package com.capgemini.race.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.race.asset.dto.AssetDetailsDto;
import com.capgemini.race.asset.dto.AssetInActiveDto;
import com.capgemini.race.asset.model.AssetDetails;
import com.capgemini.race.asset.model.AssetTypeDetails;
import com.capgemini.race.asset.repository.AssetRepository;
import com.capgemini.race.asset.repository.AssetTypeRepository;
import com.capgemini.race.asset.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	AssetRepository assetRepository;
	@Autowired
	AssetTypeRepository assetTypeRepository;

	@Override
	public List<AssetDetailsDto> getAllAssests() {
		List<AssetDetails> assetDetails = assetRepository.findAll();
		List<AssetDetailsDto> assetDetailsDtoList = new ArrayList();
		AssetDetailsDto assetDetailsDto = null;
		for (AssetDetails assetDetailsObj : assetDetails) {
			assetDetailsDto = new AssetDetailsDto();
			assetDetailsDto.setEmpId(assetDetailsObj.getEmpId());
			assetDetailsDto.setAssetId(assetDetailsObj.getAssetId());
			assetDetailsDto.setAssetNumber(assetDetailsObj.getAssetNumber());
			assetDetailsDto.setCiDesc(assetDetailsObj.getCiDesc());
			assetDetailsDto.setAssetModel(assetDetailsObj.getAssetModel());
			assetDetailsDto.setAssetOwner(assetDetailsObj.getAssetOwner());
			assetDetailsDto.setStatus(assetDetailsObj.getStatus());
			assetDetailsDto.setAssetCreated(assetDetailsObj.getAssetCreated());
			assetDetailsDto.setAssetUpdated(assetDetailsObj.getAssetUpdated());
			
			/*assetDetailsDto.setVdiId(assetDetailsObj.getVdiId());
			assetDetailsDto.setMobileNo(assetDetailsObj.getMobileNo());
			assetDetailsDto.setImeiNo(assetDetailsObj.getImeiNo());*/
			assetDetailsDto.setAssetTypeId(assetDetailsObj.getAssetTypeDetails().getAssetTypeId());
			assetDetailsDto.setAssetTypeName(assetDetailsObj.getAssetTypeDetails().getAssetTypeName());
			assetDetailsDtoList.add(assetDetailsDto);
		}
		return assetDetailsDtoList;
	}
	

	@Override
	public AssetDetailsDto getAssetById(int asset_id) {
		Optional<AssetDetails> assetDetails = assetRepository.findById((long) asset_id);
		List<AssetDetailsDto> assetDetailsDtoList = new ArrayList();
		AssetDetailsDto assetDetailsDto = new AssetDetailsDto();
		if (assetDetails.isPresent()) {
			assetDetailsDto.setAssetId(assetDetails.get().getAssetId());
			assetDetailsDto.setAssetNumber(assetDetails.get().getAssetNumber());
			assetDetailsDto.setCiDesc(assetDetails.get().getCiDesc());
			assetDetailsDto.setAssetModel(assetDetails.get().getAssetModel());
			assetDetailsDto.setAssetOwner(assetDetails.get().getAssetOwner());
			assetDetailsDto.setAssetCreated(assetDetails.get().getAssetCreated());
			assetDetailsDto.setAssetUpdated(assetDetails.get().getAssetUpdated());
			assetDetailsDto.setStatus(assetDetails.get().getStatus());
			assetDetailsDto.setEmpId(assetDetails.get().getEmpId());
			/*assetDetailsDto.setVdiId(assetDetails.get().getVdiId());
			assetDetailsDto.setMobileNo(assetDetails.get().getMobileNo());
			assetDetailsDto.setImeiNo(assetDetails.get().getImeiNo());*/
			assetDetailsDto.setAssetTypeId(assetDetails.get().getAssetTypeDetails().getAssetTypeId());
			assetDetailsDto.setAssetTypeName(assetDetails.get().getAssetTypeDetails().getAssetTypeName());
		} else {
			assetDetailsDto.setErrorMessage("id not present");
		}

		return assetDetailsDto;
	}
	

	
	@Override
 	public List<AssetDetailsDto> getAssetByempid(int emp_id) {
  		List<AssetDetails> assetDetails = assetRepository.findByGivenId(String.valueOf(emp_id));
 		//AssetDetailsDto assetDetailsDto = new AssetDetailsDto();
  		List<AssetDetailsDto> assetDetailsDtoList = new ArrayList();
		AssetDetailsDto assetDetailsDto = null;
		for (AssetDetails assetDetailsObj : assetDetails) {
			assetDetailsDto = new AssetDetailsDto();
			assetDetailsDto.setEmpId(assetDetailsObj.getEmpId());
			assetDetailsDto.setAssetId(assetDetailsObj.getAssetId());
			assetDetailsDto.setAssetNumber(assetDetailsObj.getAssetNumber());
			assetDetailsDto.setCiDesc(assetDetailsObj.getCiDesc());
			assetDetailsDto.setAssetModel(assetDetailsObj.getAssetModel());
			assetDetailsDto.setAssetOwner(assetDetailsObj.getAssetOwner());
			assetDetailsDto.setStatus(assetDetailsObj.getStatus());
			assetDetailsDto.setAssetCreated(assetDetailsObj.getAssetCreated());
			assetDetailsDto.setAssetUpdated(assetDetailsObj.getAssetUpdated());
			
			/*assetDetailsDto.setVdiId(assetDetailsObj.getVdiId());
			assetDetailsDto.setMobileNo(assetDetailsObj.getMobileNo());
			assetDetailsDto.setImeiNo(assetDetailsObj.getImeiNo());*/
			assetDetailsDto.setAssetTypeId(assetDetailsObj.getAssetTypeDetails().getAssetTypeId());
			assetDetailsDto.setAssetTypeName(assetDetailsObj.getAssetTypeDetails().getAssetTypeName());
			assetDetailsDtoList.add(assetDetailsDto);
		}
		return assetDetailsDtoList;
	}
	

	@Override
	public void createAsset(AssetDetailsDto assetDetailsDto) {
		AssetDetails assetDetails = new AssetDetails();
		assetDetails.setEmpId(assetDetailsDto.getEmpId());
		assetDetails.setAssetId(assetDetailsDto.getAssetId());
		assetDetails.setAssetModel(assetDetailsDto.getAssetModel());

		assetDetails.setAssetNumber(assetDetailsDto.getAssetNumber());
		assetDetails.setCiDesc(assetDetailsDto.getCiDesc());
		assetDetails.setAssetOwner(assetDetailsDto.getAssetOwner());

		assetDetails.setAssetCreated(assetDetailsDto.getAssetCreated());
		assetDetails.setAssetUpdated(assetDetailsDto.getAssetUpdated());
		assetDetails.setStatus(assetDetailsDto.getStatus());
		
		/*assetDetails.setVdiId(assetDetailsDto.getVdiId());
		assetDetails.setMobileNo(assetDetailsDto.getMobileNo());
		assetDetails.setImeiNo(assetDetailsDto.getImeiNo());*/
		AssetTypeDetails assetTypeDetails = new AssetTypeDetails();
		assetTypeDetails.setAssetTypeId(assetDetailsDto.getAssetTypeId());
		assetDetails.setAssetTypeDetails(assetTypeDetails);
		assetRepository.save(assetDetails);
		
	}
	

	@Override
	public String updateAsset(int assetId, AssetDetailsDto assetDetailsDtoParam) {
		AssetDetails assetDetailsRepo = assetRepository.findByGivenId((long) assetId);
		String errorMessage = "Success";

		if (null != assetDetailsRepo) {

			assetDetailsRepo.setAssetModel(assetDetailsDtoParam.getAssetModel());

			assetDetailsRepo.setAssetNumber(assetDetailsDtoParam.getAssetNumber());
			assetDetailsRepo.setCiDesc(assetDetailsDtoParam.getCiDesc());
			assetDetailsRepo.setAssetOwner(assetDetailsDtoParam.getAssetOwner());
			assetDetailsRepo.setAssetUpdated(assetDetailsDtoParam.getAssetUpdated());
			assetDetailsRepo.setStatus(assetDetailsDtoParam.getStatus());
			assetDetailsRepo.setAssetCreated(assetDetailsDtoParam.getAssetCreated());
			assetDetailsRepo.setEmpId(assetDetailsDtoParam.getEmpId());
			/*assetDetailsRepo.setVdiId(assetDetailsDtoParam.getVdiId());
			assetDetailsRepo.setMobileNo(assetDetailsDtoParam.getMobileNo());
			assetDetailsRepo.setImeiNo(assetDetailsDtoParam.getImeiNo());*/
			AssetTypeDetails assetTypeDetails = new AssetTypeDetails();
			assetTypeDetails.setAssetTypeId(assetDetailsDtoParam.getAssetTypeId());
			assetDetailsRepo.setAssetTypeDetails(assetTypeDetails);
			assetRepository.save(assetDetailsRepo);
			return errorMessage;
		} else {
			errorMessage = "id not present";
			return errorMessage;
		}

	}
	@Override
	public String deleteAsset(int assetId) {
		AssetDetails assetDetailsRepo = assetRepository.findByGivenId((long) assetId);
		String errorMessage = "Success";

		if (null != assetDetailsRepo) {
		assetRepository.deleteById((long) assetId);
		return errorMessage;
		}
		else {
			errorMessage = "id not present";
			return errorMessage;
		}
	}

	public int getAssetCount() {
		return assetRepository.getAssetCount();
	}

	
	
	@Override
	public AssetInActiveDto getAssetInAcive() {
		AssetInActiveDto assetInActiveDto = new AssetInActiveDto();
		AssetDetails assetDetails = assetRepository.getInActiveAssetDetails();
		assetInActiveDto.setAssetId(assetDetails.getAssetId());
		assetInActiveDto.setAssetModel(assetDetails.getAssetModel());
		assetInActiveDto.setAssetNumber(assetDetails.getAssetNumber());
		assetInActiveDto.setCiDesc(assetDetails.getCiDesc());
		assetInActiveDto.setAssetTypeId(assetDetails.getAssetTypeDetails().getAssetTypeId());
		return assetInActiveDto;
	} 

}
