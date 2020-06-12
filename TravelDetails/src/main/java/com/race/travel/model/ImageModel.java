package com.race.travel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * <h1>ImageModel Entity class!</h1> This holds image related data
 * </p>
 *
 * @author Sunil
 * @version 2.0
 * @since 2020-05-22
 */
@Data
@ToString
@AllArgsConstructor
@Embeddable
public class ImageModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "imgName")
	private String imgName;

	@Column(name = "imgType")
	private String imgType;
	@Lob
	@Column(name = "imgPic")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] imgPic;

	public ImageModel() {
		super();
	}

}
