package com.my.quiz.utility;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.my.quiz.response.Response;

@Component
public class Utility {
	
	public ResponseEntity<Response> validateRequest(Errors errors, HttpStatus httpstatus, Response resp) {
		// get all errors 
		resp.setDescription(errors.getAllErrors()
			.stream()
			.map(x -> x.getDefaultMessage())
			.collect(Collectors.joining(",")));
		resp.setHttpstatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Response>(resp, httpstatus);
	}

}
