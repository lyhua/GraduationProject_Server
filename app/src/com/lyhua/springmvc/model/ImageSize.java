package com.lyhua.springmvc.model;

/**
 * 
 * @author Administrator
 * ר�����ڷ���ͼƬ�ĳߴ�
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
	
	//���������췽��
	public ImageSize(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	//�޲����Ĺ��췽��
	public ImageSize() {
		super();
	}
	
	
	
	
	
}
