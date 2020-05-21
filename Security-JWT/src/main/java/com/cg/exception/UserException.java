package com.cg.exception;


/**
 * @author praterai
 *
 */
public class UserException  extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	public UserException(String errorCode, String errorMessage) {
		super();
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
