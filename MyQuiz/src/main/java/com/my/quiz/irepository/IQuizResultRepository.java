package com.my.quiz.irepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.my.quiz.domain.QuizResultThroughJoins;

@Repository
public interface IQuizResultRepository extends CrudRepository<QuizResultThroughJoins, Long>{
	
	@Query(nativeQuery = true, name = "findByQuizId")
	List<QuizResultThroughJoins> findByQuizId(@Param("quizId") Long quizId);

}
