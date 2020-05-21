package com.cg.restModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDetails {
	
	@JsonProperty("emp_id")
	private Long empId;

	@JsonProperty("emp_name")
	private String EmpName;

	@JsonProperty("employeeDetails")
	private CubicaLDetails cubicalDetails;

	@Override
	public String toString() {
		return "EmployeeDetails [empId=" + empId + ", EmpName=" + EmpName + ", cubicalDetails=" + cubicalDetails + "]";
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return EmpName;
	}

	public void setEmpName(String empName) {
		EmpName = empName;
	}

	public CubicaLDetails getCubicalDetails() {
		return cubicalDetails;
	}

	public void setCubicalDetails(CubicaLDetails cubicalDetails) {
		this.cubicalDetails = cubicalDetails;
	}

}
