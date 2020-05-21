package com.my.quiz.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sumeet tiwari<OPEN SOURCE TECHNICAL BLOG>
 *
 */
@Getter
@Setter
@ToString
public class Response {

	private HttpStatus httpstatus;
	
	private String description;
	
	private Object obj;
}
