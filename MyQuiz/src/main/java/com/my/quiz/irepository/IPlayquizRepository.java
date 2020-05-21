package com.my.quiz.irepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.quiz.domain.Playquiz;

@Repository
public interface IPlayquizRepository   extends CrudRepository<Playquiz, Long>{
	
	Optional<Playquiz> findByQuizIdAndQuestionIdAndPlayerPlayerId(Long quizId,Long questionId,Long playerId);

	List<Playquiz> findByQuizId(Long quizId);
	
	@Transactional
	void deleteByQuizId(Long quizId);
}
