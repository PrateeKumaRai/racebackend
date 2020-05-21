package com.capgemini.travel.model;

import java.io.Serializable;

public class DeleteModel implements Serializable {
	private static final long serialVersionUID = 1L;
   private Integer statusCode;
   private String Message;
	
	public Integer getStatusCode() {
	return statusCode;
}

public void setStatusCode(Integer statusCode) {
	this.statusCode = statusCode;
}

public String getMessage() {
	return Message;
}

public void setMessage(String message) {
	Message = message;
}


}
