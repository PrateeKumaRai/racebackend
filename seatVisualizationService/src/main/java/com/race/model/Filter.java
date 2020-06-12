package com.race.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author praterai
 *
 */
@Data
@NoArgsConstructor
@ToString
public class Filter {
	
	private String country;
	private String city;
	private String office;
	
}
