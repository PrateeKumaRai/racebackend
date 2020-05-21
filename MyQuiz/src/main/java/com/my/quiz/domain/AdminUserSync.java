package com.my.quiz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AdminUserSync {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long syncId;
	
    @NotNull(message = "quizId should not be null or empty")
	private Long quizId;
    
    @NotNull(message = "questionIndex should not be null or empty")
    private Long questionIndex;
    
    @NotNull(message = "isTimerOn should not be null or empty")
    private String isTimerOn;

}
