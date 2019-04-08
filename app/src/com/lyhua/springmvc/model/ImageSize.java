package com.lyhua.springmvc.model;

/**
 * 
 * @author Administrator
 * 专门用于返回图片的尺寸
 */
public class ImageSize {
	private double width;
	private double height;
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	//带参数构造方法
	public ImageSize(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	//无参数的构造方法
	public ImageSize() {
		super();
	}
	
	
	
	
	
}
