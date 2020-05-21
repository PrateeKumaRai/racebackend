package com.my.quiz.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quiz {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long quizId;
	
	@NotEmpty(message = "title should not be null or empty")
	private String title;
	
	@NotEmpty(message = "description should not be null or empty")
	private String description;
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> listQuestn;
	
	private String isPublish;
	
	private String pin;

}
