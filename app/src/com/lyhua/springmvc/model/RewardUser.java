package com.lyhua.springmvc.model;

public class RewardUser {

	private Integer reward_user_id;//���id
	private Integer reward_id;//����id
	private Integer user_id;//�û�id
	
	
	
	////�޲����Ĺ��췽��
	public RewardUser() {
		super();
	}
	////���������췽��
	public RewardUser(Integer reward_user_id, Integer reward_id, Integer user_id) {
		super();
		this.reward_user_id = reward_user_id;
		this.reward_id = reward_id;
		this.user_id = user_id;
	}
	public Integer getReward_user_id() {
		return reward_user_id;
	}
	public void setReward_user_id(Integer reward_user_id) {
		this.reward_user_id = reward_user_id;
	}
	public Integer getReward_id() {
		return reward_id;
	}
	public void setReward_id(Integer reward_id) {
		this.reward_id = reward_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	
	
	
}
