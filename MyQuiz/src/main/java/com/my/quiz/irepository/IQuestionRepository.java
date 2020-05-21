package com.my.quiz.irepository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.quiz.domain.Question;

@Repository
public interface IQuestionRepository extends CrudRepository<Question, Long>{
	
	List<Question> findByQuizQuizId(Long quizId);
	
	List<Question> findAll();
	
	@Transactional
	void deleteByQuizQuizId(Long quizId);

}
