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
public class Answer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long answerId;
	
    @ManyToOne
	@JoinColumn(name="questionId",nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
	private Question questn;
	
    @NotNull(message = "quizId should not be null or empty")
	private Long quizId;
	
	@NotEmpty(message = "description should not be null or empty")
	private String description;
	
	@NotEmpty(message = "isTrueAnswr should not be null or empty")
	private String isTrueAnswr;

}
