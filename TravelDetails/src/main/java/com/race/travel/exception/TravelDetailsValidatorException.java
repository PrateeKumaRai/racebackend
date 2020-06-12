package com.race.travel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * <h1>TravelDetailsException class!</h1> This is user defined exception class
 * which is used for give end user understandable error message,if some
 * exception occurred.
 * </P>
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-22
 */
@Data
@ToString
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TravelDetailsValidatorException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
}
