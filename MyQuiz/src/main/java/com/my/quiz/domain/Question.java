package com.my.quiz.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long questionId;
	
	@ManyToOne
	@JoinColumn(name="quizId",nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Quiz quiz;
	
	@NotEmpty(message = "questnDescp should not be null or empty")
	private String questnDescp;
	
	@OneToMany(mappedBy = "questn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Answer> answr;

}
