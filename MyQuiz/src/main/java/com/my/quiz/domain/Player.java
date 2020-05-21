package com.my.quiz.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long playerId;
	
	@NotNull(message = "quizId should not be null or empty")
	private Long quizId;
	
	@NotEmpty(message = "nickName should not be null or empty")
	private String nickName;
	
	@OneToMany(mappedBy = "player")
	private List<Playquiz> playquiz;

}
