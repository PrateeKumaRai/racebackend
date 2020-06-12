package com.race.travel.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * <h1>ErrorHandelModel class!</h1> This class contains properties details to
 * contain all type of error message and error code and status code which is
 * send as response to the client.
 * </p>
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-22
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorHandelModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;
	private Integer statusCode;

}
