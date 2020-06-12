package com.race.travel.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <h1>DeleteModel class!</h1> This class contains properties details of delete
 * message and status code which is send as response to the consumer.
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-22
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer statusCode;
	private String Message;

}
