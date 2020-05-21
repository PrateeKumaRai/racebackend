package com.cg.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.model.UserEntity;

/**
 * @author praterai
 *
 */
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	UserEntity findByEmailIdIgnoreCase(String emailId);

	@Query(value = "SELECT * FROM user_tab td WHERE td.email_id = ?1 ", nativeQuery = true)
	Optional<UserEntity> getbyUserByMail(String emailId);

	void deleteById(Long id);
	
	@Modifying
	@Transactional
	@Query(value="update user_tab  set user_name = ?1, emp_id = ?2, mobile= ?3, house_no=?4, area=?5, city=?6, country=?7, zip=?8 where email_id = ?9 ",nativeQuery = true)
	void updateUser(String username, Long empId, Long mobile, String houseNo, String area, String city, String country, Long zip, String emailId);

}