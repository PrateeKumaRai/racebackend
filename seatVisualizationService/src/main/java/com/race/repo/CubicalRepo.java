package com.race.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.race.constant.QueryConstant;
import com.race.entity.CubicalEntity;

/**
 * Fetching the Database results based on customized queries and convert into
 * entities.
 * 
 * @author praterai
 *
 */
public interface CubicalRepo extends JpaRepository<CubicalEntity, Long> {

	@Transactional
	@Modifying
	@Query(value = QueryConstant.FIND_CUBICAL_BY_STATUS, nativeQuery = true)
	public List<CubicalEntity> findByCubicalStatus(Boolean cubicalStatus, LocalDate date);

	@Modifying
	@Transactional
	@Query(value = QueryConstant.UPDATE_OWNER_CUBICAL, nativeQuery = true)
	public void updateSeatStatusAfterBookingOwner(Boolean cubicalStatus, String emailId);

	@Modifying
	@Transactional
	@Query(value = QueryConstant.UPDATE_TEMP_CUBICAL, nativeQuery = true)
	public void updateSeatStatusAfterBookingTemp(Boolean cubicalStatus, String emailId, String seatNo,
			String loginEmail);

	@Modifying
	@Transactional
	@Query(value = QueryConstant.UPDATE_SEAT_STATUS, nativeQuery = true)
	public void updateSeatStatus(Boolean cubicalStatus, Date startDate, Date returnDate, String emailId);

	@Query(value = QueryConstant.FIND_BY_MAIL, nativeQuery = true)
	CubicalEntity findByEmailId(@Param("emailId") String emailId);

	@Modifying
	@Transactional
	@Query(value = QueryConstant.RESET_CUBICAL, nativeQuery = true)
	public void jobResetSeatStatus(Boolean flag, LocalDate date);

	@Modifying
	@Transactional
	@Query(value = QueryConstant.RESET_TEMP_BOOKING, nativeQuery = true)
	public void jobResetTempBooking(Boolean tempFlag, Boolean flag);

	@Modifying
	@Transactional
	@Query(value = QueryConstant.UNBOOK_CUBICAL, nativeQuery = true)
	public void clearSeatDataAfterUnbook(String tempSeatMail, String tempSeat, Boolean cubicalNumberTemp,
			String loginMail);

	@Transactional
	@Modifying
	@Query(value = QueryConstant.FILTER_CUBICAL, nativeQuery = true)
	public List<CubicalEntity> filter(String country, String city, String office, Boolean cubicalStatus,
			LocalDate date);
}
