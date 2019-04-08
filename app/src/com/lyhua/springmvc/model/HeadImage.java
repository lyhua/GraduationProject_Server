package com.lyhua.springmvc.model;

public class HeadImage {
	public Integer id;
	public Integer width;
	public Integer height;
	public String headImage;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public HeadImage(Integer id, Integer width, Integer height, String headImage) {
		super();
		this.id = id;
		this.width = width;
		this.height = height;
		this.headImage = headImage;
	}
	public HeadImage() {
		super();
	}
	@Override
	public String toString() {
		return "HeadImage [id=" + id + ", width=" + width + ", height="
				+ height + ", headImage=" + headImage + "]";
	}
	
	
	
	
}
