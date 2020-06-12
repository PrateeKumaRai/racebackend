package com.race.restModel;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author praterai
 *
 */
@Data
@NoArgsConstructor
@ToString
public class CubicaLDetails {
	
	@JsonProperty("cubical_number")
	private Long cubicalNumber;

	@JsonProperty("cubical_status")
	private Boolean cubicalStatus;

	@JsonProperty("empId")
	private EmployeeDetails employeeDetails;

	@JsonProperty("cubicalDetails")
	private LocationDetails locationDetails;

	
}
