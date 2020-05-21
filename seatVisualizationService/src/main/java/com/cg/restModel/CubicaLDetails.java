package com.cg.restModel;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CubicaLDetails {
	
	@JsonProperty("cubical_number")
	private Long cubicalNumber;

	@JsonProperty("cubical_status")
	private Boolean cubicalStatus;

	@JsonProperty("empId")
	private EmployeeDetails employeeDetails;

	@JsonProperty("cubicalDetails")
	private LocationDetails locationDetails;

	@Override
	public String toString() {
		return "CubicaLDetails [cubicalNumber=" + cubicalNumber + ", cubicalStatus=" + cubicalStatus
				+ ", employeeDetails=" + employeeDetails + ", locationDetails=" + locationDetails + "]";
	}

	public Long getCubicalNumber() {
		return cubicalNumber;
	}

	public void setCubicalNumber(Long cubicalNumber) {
		this.cubicalNumber = cubicalNumber;
	}

	public Boolean getCubicalStatus() {
		return cubicalStatus;
	}

	public void setCubicalStatus(Boolean cubicalStatus) {
		this.cubicalStatus = cubicalStatus;
	}

	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public LocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(LocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}

	
}
