package com.cg.restModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDetails {
	
	@JsonProperty("location_Id")
	private Long locationId;

	@JsonProperty("location_Name")
	private String locationName;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("conutry")
	private String country;

	@JsonProperty("cubical_number")
	private CubicaLDetails cubicalDetails;

	@Override
	public String toString() {
		return "LocationDetails [locationId=" + locationId + ", locationName=" + locationName + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", cubicalDetails=" + cubicalDetails + "]";
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public CubicaLDetails getCubicalDetails() {
		return cubicalDetails;
	}

	public void setCubicalDetails(CubicaLDetails cubicalDetails) {
		this.cubicalDetails = cubicalDetails;
	}

	
}
