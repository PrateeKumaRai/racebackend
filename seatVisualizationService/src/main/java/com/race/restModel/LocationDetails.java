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
}
