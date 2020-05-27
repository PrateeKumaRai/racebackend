package com.cg.model;

import java.io.Serializable;

/**
 * @author praterai
 *
 */
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String token;
	private int statusCode;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}