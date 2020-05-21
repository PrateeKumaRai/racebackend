package com.capgemini.race.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.race.asset.model.AssetTypeDetails;

public interface AssetTypeRepository extends JpaRepository<AssetTypeDetails, Integer>{

}
