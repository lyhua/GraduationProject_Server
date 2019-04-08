package com.lyhua.springmvc.model;

public class Friend {
	private Integer friend_id;
	private Integer user_id;
	private Integer myFriend_id;
	public Integer getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(Integer friend_id) {
		this.friend_id = friend_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getMyFriend_id() {
		return myFriend_id;
	}
	public void setMyFriend_id(Integer myFriend_id) {
		this.myFriend_id = myFriend_id;
	}
	
	
	//带参数的构造方法
	public Friend(Integer friend_id, Integer user_id, Integer myFriend_id) {
		super();
		this.friend_id = friend_id;
		this.user_id = user_id;
		this.myFriend_id = myFriend_id;
	}
	//不带参数的构造方法
	public Friend() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "Friend [friend_id=" + friend_id + ", user_id=" + user_id
				+ ", myFriend_id=" + myFriend_id + "]";
	}
	
	
	
	
	
}
