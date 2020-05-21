package com.capgemini.travel.Dao;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.travel.model.TravelDetail;/**
* <h1>Travel Repository!</h1>
* <p>The Travel Repository Interface help us to perform DAO operations
* using JPA repository and also contains some customized query for DAO operation.
* 
* 
* @author  Sunil 
* @version 1.0
* @since   2020-01-21 
*/

@Repository
public interface TravelRepository extends JpaRepository<TravelDetail, Integer> {

	
		
	@Query(value = "SELECT * FROM travel_details td WHERE (td.start_date >= ?1 AND td.start_date<= ?2) AND (td.source_location=?3 AND td.dest_location=?4) AND (td.src_country=?5 AND td.dest_country=?6)", nativeQuery = true)
	Optional<List<TravelDetail>> findAllByDateAndLocation(Date start_date1, Date start_date12, String SourceLocation,
			String destLocation,String srcCountry,String destCountry);
	
	
	@Query(value = "SELECT * FROM travel_details td WHERE td.employee_id = ?1 ", nativeQuery = true)
	Optional<List<TravelDetail>> findByEmployeeID(int empID);
	
	@Query(value = "SELECT * FROM travel_details td WHERE td.employee_id = ?1 AND td.travel_id = ?2", nativeQuery = true)
	Optional<TravelDetail> findByEmployeeIdAndTravelId(int empID,int travelId);

	@Query(value = "SELECT * FROM travel_details td WHERE (td.start_date >= ?1 AND td.start_date<= ?2)", nativeQuery = true)
	Optional<List<TravelDetail>> findAllByDate(Date dateBefore, Date dateAfter);

	@Query(value = "SELECT * FROM travel_details td WHERE (td.source_location = ?1 AND td.dest_location= ?2) AND (td.src_country=?3 AND td.dest_country=?4)", nativeQuery = true)
	Optional<List<TravelDetail>> findAllByLocation(String sourceLocation, String destLocation,String srcCountry,String destCountry);

}

