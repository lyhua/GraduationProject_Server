package com.lyhua.springmvc.model;

public class User {
	private Integer userId;
	private String name;
	private String password;
	private Integer age;
	private Integer gender;
	private String phone;
	private String headImage;
	private String email;
	
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	public User(Integer userId, String name, String password, Integer age,
			Integer gender, String phone, String headImage, String email) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.headImage = headImage;
		this.email = email;
	}
	public User() {
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
