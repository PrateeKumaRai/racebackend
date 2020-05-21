package com.my.quiz.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Playquiz {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long playquizId;
	
	@ManyToOne
	@JoinColumn(name="playerId",nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Player player;
	
	@NotNull(message = "quizId should not be null or empty")
	private Long quizId;
	
	@NotNull(message = "questionId should not be null or empty")
	private Long questionId;
	
	@NotEmpty(message = "playerAnswr should not be null or empty")
	private String playerAnswr;
	
	@NotEmpty(message = "actualAnswer should not be null or empty")
	private String actualAnswer;
	
	@NotEmpty(message = "state should not be null or empty")
	private String state;
	
	@NotNull(message = "answerTime should not be null or empty")
	private int answerTime;
	
	@NotNull(message = "score should not be null or empty")
	private Long score;
	
	@NotNull(message = "configureTime should not be null or empty")
	private Long configureTime;

}
