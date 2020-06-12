package com.race.travel.Dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.race.travel.dbconstant.DatabaseConstant;
import com.race.travel.model.TravelDetail;

/**
 * <h1>Travel Repository!</h1>
 * <p>
 * The Travel Repository Interface help us to perform DAO operations using JPA
 * repository and also contains some customized query for DAO operation.
 * 
 * 
 * @author Sunil
 * @version 1.0
 * @since 2020-01-21
 */

@Repository
public interface TravelRepository extends JpaRepository<TravelDetail, Integer> {

	@Query(value = DatabaseConstant.DATE_AND_LOCATION, nativeQuery = true)
	Optional<List<TravelDetail>> findAllByDateAndLocation(Date start_date1, Date start_date12, String SourceLocation,
			String destLocation, String srcCountry, String destCountry);

	@Query(value =DatabaseConstant.FIND_BY_EMPLOYEEID , nativeQuery = true)
	Optional<List<TravelDetail>> findByEmployeeID(Integer empID);

	@Query(value = DatabaseConstant.FIND_BY_EMPLOYEEID_AND_TRAVELID, nativeQuery = true)
	Optional<TravelDetail> findByEmployeeIdAndTravelId(int empID, int travelId);

	@Query(value = DatabaseConstant.FIND_BY_DATE, nativeQuery = true)
	Optional<List<TravelDetail>> findAllByDate(Date dateBefore, Date dateAfter);

	@Query(value = DatabaseConstant.FIND_BY_LOCATION, nativeQuery = true)
	Optional<List<TravelDetail>> findAllByLocation(String sourceLocation, String destLocation, String srcCountry,
			String destCountry);

}
