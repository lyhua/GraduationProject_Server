package com.lyhua.springmvc.model;

public class Telephone {
	private Integer phone_id;
	private String phonenumber;
	private String contactname;
	private Integer user_id;
	
	
	public Telephone() {
		super();
	}


	public Telephone(Integer phone_id, String phonenumber, String contactname,
			Integer user_id) {
		super();
		this.phone_id = phone_id;
		this.phonenumber = phonenumber;
		this.contactname = contactname;
		this.user_id = user_id;
	}


	public Integer getPhone_id() {
		return phone_id;
	}


	public void setPhone_id(Integer phone_id) {
		this.phone_id = phone_id;
	}


	public String getPhonenumber() {
		return phonenumber;
	}


	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}


	public String getContactname() {
		return contactname;
	}


	public void setContactname(String contactname) {
		this.contactname = contactname;
	}


	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	
	
	
	
	
}
