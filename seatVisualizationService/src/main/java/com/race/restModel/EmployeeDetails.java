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
public class EmployeeDetails {
	
	@JsonProperty("emp_id")
	private Long empId;

	@JsonProperty("emp_name")
	private String EmpName;

	@JsonProperty("employeeDetails")
	private CubicaLDetails cubicalDetails;

	
}
