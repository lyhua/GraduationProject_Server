package com.lyhua.springmvc.model;

public class Info {
	private Integer info_id;//��Ϣid
	private String info_content;//��Ϣ����
	private Integer send_id;//���ͷ�id
	private Integer receive_id;//���ܷ�id
	private String send_date;//��������
	private String receive_date;//��������
	private String senderHeadImage;//������ͷ��
	private String senderName;//����������
	
	public Integer getInfo_id() {
		return info_id;
	}
	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
	}
	public String getInfo_content() {
		return info_content;
	}
	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}
	public Integer getSend_id() {
		return send_id;
	}
	public void setSend_id(Integer send_id) {
		this.send_id = send_id;
	}
	public Integer getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(Integer receive_id) {
		this.receive_id = receive_id;
	}
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getReceive_date() {
		return receive_date;
	}
	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}
	
	
	public String getSenderHeadImage() {
		return senderHeadImage;
	}
	public void setSenderHeadImage(String senderHeadImage) {
		this.senderHeadImage = senderHeadImage;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	//Ĭ�Ϲ��췽��
	public Info() {
		super();
	}
	
	//���������췽��
	public Info(Integer info_id, String info_content, Integer send_id,
			Integer receive_id, String send_date, String receive_date) {
		super();
		this.info_id = info_id;
		this.info_content = info_content;
		this.send_id = send_id;
		this.receive_id = receive_id;
		this.send_date = send_date;
		this.receive_date = receive_date;
	}
	//���������췽����
	public Info(Integer info_id, String info_content, Integer send_id,
			Integer receive_id, String send_date, String receive_date,
			String senderHeadImage, String senderName) {
		super();
		this.info_id = info_id;
		this.info_content = info_content;
		this.send_id = send_id;
		this.receive_id = receive_id;
		this.send_date = send_date;
		this.receive_date = receive_date;
		this.senderHeadImage = senderHeadImage;
		this.senderName = senderName;
	}
	
	
	
	
	
	
	

}
