package com.capgemini.travel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

@Embeddable
public class ImageModel implements Serializable {
	@Column(name = "imgName")
	private String imgName;
	
	@Column(name = "imgType")
    private String imgType;
	@Lob
	@Column(name = "imgPic")
	@Type(type="org.hibernate.type.BinaryType")
    private byte[] imgPic;
	
	public ImageModel() {
		super();
	}
	public ImageModel(String imgName, String imgType, byte[] imgPic) {
		super();
		this.imgName = imgName;
		this.imgType = imgType;
		this.imgPic = imgPic;
	}
	
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public byte[] getImgPic() {
		return imgPic;
	}
	public void setImgPic(byte[] imgPic) {
		this.imgPic = imgPic;
	}

}


