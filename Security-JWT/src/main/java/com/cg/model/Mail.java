package com.cg.model;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author praterai
 *
 */
@Component
public class Mail {
	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	private Map<String, Object> model;
}
