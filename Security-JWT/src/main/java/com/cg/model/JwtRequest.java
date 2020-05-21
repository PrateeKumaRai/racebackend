package com.cg.model;

import java.io.Serializable;

/**
 * @author praterai
 *
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String emailId;
	private String password;
	private String token;

	public JwtRequest() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}