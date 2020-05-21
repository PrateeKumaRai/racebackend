package com.my.quiz.irepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.my.quiz.domain.Quiz;

@Repository
public interface IQuizRepository extends CrudRepository<Quiz, Long>{
	
	Optional<Quiz> findByIsPublishAndPin(String isPublish, String pin);
	
	List<Quiz> findAll();
	
}
