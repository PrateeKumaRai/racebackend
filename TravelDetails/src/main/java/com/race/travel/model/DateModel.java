package com.race.travel.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DateModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dateAfter;
	private Date dateBefore;

}
