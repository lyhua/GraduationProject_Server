package com.lyhua.springmvc.model;

public class Reward {
	private Integer reward_id;//奖励id
	private String reward_name;//奖励名称
	private String reward_pic;//奖励徽章图片
	private Integer mission_id;//任务id
	public Integer getReward_id() {
		return reward_id;
	}
	public void setReward_id(Integer reward_id) {
		this.reward_id = reward_id;
	}
	public String getReward_name() {
		return reward_name;
	}
	public void setReward_name(String reward_name) {
		this.reward_name = reward_name;
	}
	public String getReward_pic() {
		return reward_pic;
	}
	public void setReward_pic(String reward_pic) {
		this.reward_pic = reward_pic;
	}
	public Integer getMission_id() {
		return mission_id;
	}
	public void setMission_id(Integer mission_id) {
		this.mission_id = mission_id;
	}
	
	//带参数构造方法
	public Reward(Integer reward_id, String reward_name, String reward_pic,
			Integer mission_id) {
		super();
		this.reward_id = reward_id;
		this.reward_name = reward_name;
		this.reward_pic = reward_pic;
		this.mission_id = mission_id;
	}
	////无参数的构造方法
	public Reward() {
		super();
	}
	
	
	
	
	
	
	
	

}
