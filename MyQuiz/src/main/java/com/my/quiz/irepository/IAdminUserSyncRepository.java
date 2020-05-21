package com.my.quiz.irepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.quiz.domain.AdminUserSync;

@Repository
public interface IAdminUserSyncRepository extends CrudRepository<AdminUserSync, Long>{

	Optional<AdminUserSync> findByQuizId(Long quizId);
	
	@Transactional
	void deleteByQuizId(Long quizId);
}
