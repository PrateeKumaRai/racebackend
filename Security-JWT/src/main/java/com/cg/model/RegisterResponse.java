package com.cg.model;

import java.io.Serializable;

/**
 * @author praterai
 *
 */
public class RegisterResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final Object response;

	public RegisterResponse(Object response) {
		this.response = response;
	}

	public Object getResponse() {
		return response;
	}

}