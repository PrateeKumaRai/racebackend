package com.capgemini.race.asset.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.race.asset.model.AssetDetails;

public interface AssetRepository extends JpaRepository<AssetDetails, Long> {
	@Query(value = "SELECT count(*) FROM asset", nativeQuery = true)
	int getAssetCount();

	@Query(value = "SELECT * FROM asset WHERE asset_id=?1", nativeQuery = true)
    AssetDetails findByGivenId(long assetId);
//	
 	//@Query(value = "SELECT * FROM asset WHERE emp_id=?1", nativeQuery = true)
    //AssetDetails findByGivenId(String emp_id);
	
	
	@Query(value = "SELECT * FROM asset WHERE emp_id=?1", nativeQuery = true)
    List<AssetDetails> findByGivenId(String emp_id);
	
//	
	@Query(value = "SELECT * FROM asset WHERE emp_id=?1", nativeQuery = true)
     AssetDetails findByGivenId1(String emp_id);
	
	@Query(value = "SELECT * FROM asset WHERE status='InActive'", nativeQuery = true)
	AssetDetails getInActiveAssetDetails();



}
