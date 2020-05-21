package com.my.quiz.irepository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.quiz.domain.Answer;

@Repository
public interface IAnswerRepository extends CrudRepository<Answer, Long>{
	
	List<Answer> findByQuizIdAndQuestnQuestionId(Long quizId,Long questionId);
	
	@Transactional
	void deleteByQuizId(Long quizId);
	
	List<Answer> findByQuizId(Long quizId);

}
