package com.race.travel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class TraveldetailsReq implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer travel_id;

	@NotNull(message = "empid should not be null or empty")
	private Integer emp_id;

	@NotNull(message = "startdate should not be null or empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date start_date;

	@NotNull(message = "returndate should not be null or empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date return_date;

	@NotNull(message = "source country should not be null or empty")
	private String src_country;

	@NotNull(message = "destination country should not be null or empty")
	private String dest_country;

	@NotNull(message = "source location should not be null or empty")
	private String src_location;

	@NotNull(message = "destination location should not be null or empty")
	private String dest_location;

	private Boolean status;

	private String travel_type;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date created_at;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;

	private String fileName;

	private String fileType;

	@NotNull(message = "Mobile Number should not be null or empty")
	private Long mob_num;

}
