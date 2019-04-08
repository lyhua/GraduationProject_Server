package com.lyhua.springmvc.model;

public class MissionUser {
	private Integer mission_user_id;//表的id
	private Integer user_id;//用户id
	private Integer mission_id;//任务id
	private Integer flag;//任务是否完成
	public Integer getMission_user_id() {
		return mission_user_id;
	}
	public void setMission_user_id(Integer mission_user_id) {
		this.mission_user_id = mission_user_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getMission_id() {
		return mission_id;
	}
	public void setMission_id(Integer mission_id) {
		this.mission_id = mission_id;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	//带参数构造方法
	public MissionUser(Integer mission_user_id, Integer user_id,
			Integer mission_id, Integer flag) {
		super();
		this.mission_user_id = mission_user_id;
		this.user_id = user_id;
		this.mission_id = mission_id;
		this.flag = flag;
	}
	//无参数的构造方法
	public MissionUser() {
		super();
	}
	
	
	
	
}
