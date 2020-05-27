package com.cg.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.ConfirmationToken;

/**
 * @author praterai
 *
 */
@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

	ConfirmationToken findByConfirmationToken(String confirmationToken);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM confirmation_token td WHERE td.email_id = ?1 ", nativeQuery = true)
	public void deleteByEmailId(String email);
}