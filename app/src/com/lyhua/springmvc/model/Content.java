package com.lyhua.springmvc.model;

public class Content {
	private Integer content_id;
	private String content;
	private String contentImage;
	private Integer mood;
	private Integer user_id;
	private String userName;
	private String date;
	private String headImage;
	private String contentSmallImage;
	private Integer mission_id;
	
	
	public Integer getContent_id() {
		return content_id;
	}
	public void setContent_id(Integer content_id) {
		this.content_id = content_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentImage() {
		return contentImage;
	}
	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}
	public Integer getMood() {
		return mood;
	}
	public void setMood(Integer mood) {
		this.mood = mood;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	public String getContentSmallImage() {
		return contentSmallImage;
	}
	public void setContentSmallImage(String contentSmallImage) {
		this.contentSmallImage = contentSmallImage;
	}
	public Integer getMission_id() {
		return mission_id;
	}
	public void setMission_id(Integer mission_id) {
		this.mission_id = mission_id;
	}
	//带参数的构造函数
	public Content(Integer content_id, String content, String contentImage,
			Integer mood, Integer user_id) {
		super();
		this.content_id = content_id;
		this.content = content;
		this.contentImage = contentImage;
		this.mood = mood;
		this.user_id = user_id;
	}
	public Content(Integer content_id, String content, String contentImage,
			Integer mood, Integer user_id, String userName, String date,
			String headImage) {
		super();
		this.content_id = content_id;
		this.content = content;
		this.contentImage = contentImage;
		this.mood = mood;
		this.user_id = user_id;
		this.userName = userName;
		this.date = date;
		this.headImage = headImage;
	}
	public Content(Integer content_id, String content, String contentImage,
			Integer mood, Integer user_id, String userName, String date) {
		super();
		this.content_id = content_id;
		this.content = content;
		this.contentImage = contentImage;
		this.mood = mood;
		this.user_id = user_id;
		this.userName = userName;
		this.date = date;
	}
	

	public Content(Integer content_id, String content, String contentImage,
			Integer mood, Integer user_id, String userName, String date,
			String headImage, String contentSmallImage) {
		super();
		this.content_id = content_id;
		this.content = content;
		this.contentImage = contentImage;
		this.mood = mood;
		this.user_id = user_id;
		this.userName = userName;
		this.date = date;
		this.headImage = headImage;
		this.contentSmallImage = contentSmallImage;
	}
	//没有参数的构造函数
	public Content() {
		super();
	}
	//带参数的构造函数
	public Content(Integer content_id, String content, String contentImage,
			Integer mood, Integer user_id, String userName, String date,
			String headImage, String contentSmallImage, Integer mission_id) {
		super();
		this.content_id = content_id;
		this.content = content;
		this.contentImage = contentImage;
		this.mood = mood;
		this.user_id = user_id;
		this.userName = userName;
		this.date = date;
		this.headImage = headImage;
		this.contentSmallImage = contentSmallImage;
		this.mission_id = mission_id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
