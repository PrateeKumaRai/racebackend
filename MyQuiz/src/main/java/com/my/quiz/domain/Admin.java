package com.my.quiz.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Admin {
	
	@NotEmpty(message = "username should not be null or empty")
	private String username;
	
	@NotEmpty(message = "password should not be null or empty")
	private String password;
}
