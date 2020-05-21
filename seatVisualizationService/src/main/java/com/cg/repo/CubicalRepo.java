package com.cg.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.model.CubicalDetails;

public interface CubicalRepo extends JpaRepository<CubicalDetails, Long> {

//	@Query(value = "SELECT u FROM CubicalDetails u where u.cubicalStatus = :cubicalStatus and ")
//	public List<CubicalDetails> findByCubicalStatus(@Param("cubicalStatus") Boolean cubicalStatus);

	@Transactional
	@Modifying
	@Query(value = "select * from cubical_tab where cubical_status=?1 and ?2 between start_date and return_date", nativeQuery = true)
	public List<CubicalDetails> findByCubicalStatus(Boolean cubicalStatus, LocalDate date);
	
	@Modifying
	@Transactional
	@Query(value = "update cubical_tab  set cubical_status = ?1 where email_id = ?2 ", nativeQuery = true)
	public void updateSeatStatusAfterBookingOwner( Boolean cubicalStatus,String emailId);
	
	@Modifying
	@Transactional
	@Query(value = "update cubical_tab  set cubical_number_temp = ?1 ,seat_mail_id= ?2 ,seat_number_temp= ?3 where email_id = ?4 ", nativeQuery = true)
	public void updateSeatStatusAfterBookingTemp( Boolean cubicalStatus,String emailId, String seatNo, String loginEmail);

	@Modifying
	@Transactional
	@Query(value = "update cubical_tab  set cubical_status = ?1, start_date = ?2, return_date= ?3 where email_id = ?4 ", nativeQuery = true)
	public void updateSeatStatus( Boolean cubicalStatus,Date startDate, Date returnDate,String emailId);

	@Query(value = "SELECT * FROM cubical_tab where email_id=?1", nativeQuery = true)
	CubicalDetails findByEmailId(@Param("emailId") String emailId);
	
	@Modifying
	@Transactional
	@Query(value = "update cubical_tab  set cubical_status= ?1 where  ?2 between start_date and return_date", nativeQuery = true)
	public void jobResetSeatStatus(Boolean flag, LocalDate date);

	@Modifying
	@Transactional
	@Query(value = "update cubical_tab  set cubical_number_temp = ?1 where cubical_number_temp = ?2 ", nativeQuery = true)
	public void jobResetTempBooking(Boolean tempFlag, Boolean flag);

	@Query(value = "SELECT * FROM cubical_tab where email_id=?1", nativeQuery = true)
	public CubicalDetails findByCubicalMail(String mail);

	@Modifying
	@Transactional
	@Query(value = "update cubical_tab  set seat_mail_id = ?1 ,seat_number_temp = ?2 ,cubical_number_temp= ?3  where email_id=?4 ", nativeQuery = true)
	public void clearSeatDataAfterUnbook(String tempSeatMail, String tempSeat, Boolean cubicalNumberTemp, String loginMail);

	@Transactional
	@Modifying
	@Query(value = "select * from cubical_tab where conutry = ?1 and city = ?2 and cubical_location = ?3 and cubical_status = ?4  and ?5 between start_date and return_date", nativeQuery = true)
	public List<CubicalDetails> filter(String country, String city, String office, Boolean cubicalStatus,LocalDate date);
}
