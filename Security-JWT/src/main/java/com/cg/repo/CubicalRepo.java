package com.cg.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.model.CubicalDetails;

@Repository
public interface CubicalRepo extends JpaRepository<CubicalDetails, Long> {

	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_tab td WHERE td.email_id = ?1 ", nativeQuery = true)
	public void deletebyEmpId(Long empId);
	
	
	@Query(value = "SELECT * FROM cubical_tab td WHERE td.email_id = ?1 ", nativeQuery = true)
	Optional<CubicalDetails> getbyEmailId(String email);
	
	@Modifying
	@Transactional
	@Query(value="update cubical_tab  set cubical_number = ?1 where email_id = ?2 ",nativeQuery = true)
	void updateSeat(String cubicalNumber, String email);

	@Modifying
	@Transactional
	@Query(value="update cubical_tab  set cubical_number = ?1, cubical_location=?2, city=?3,conutry=?4 , zip=?5 , floor=?6 ,cubical_number_temp=?7 where email_id = ?8",nativeQuery = true)
	public void updateSeatFromProfile(String cubicalNumber, String cubicalLocation, String city, String country, Long zip,
			String floor, Boolean temp, String email);

}
