package com.capgemini.travel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TravelDetailsException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	
	public TravelDetailsException(String errorCode ,String errorMessage) {
        
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
    

}
