package com.my.quiz.irepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.quiz.domain.Player;

@Repository
public interface IPlayerRepository  extends CrudRepository<Player, Long>{
	
	List<Player> findByQuizIdAndPlayerId(Long quizId,Long playerId);
	
	List<Player> findByQuizId(Long quizId);
	
	Optional<Player> findByNickNameAndQuizId(String nickName,Long quizId);
	
	@Transactional
	void deleteByQuizId(Long quizId);

}
