package com.lyhua.springmvc.model;

public class Mission {
	private Integer mission_id;//����id
	private Integer flag;//�����Ƿ����
	private String mission_content;//��������
	private String mission_starttime;//���񷢲�ʱ��
	private String mission_endtime;//�������ʱ��
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
	public String getMission_content() {
		return mission_content;
	}
	public void setMission_content(String mission_content) {
		this.mission_content = mission_content;
	}
	public String getMission_starttime() {
		return mission_starttime;
	}
	public void setMission_starttime(String mission_starttime) {
		this.mission_starttime = mission_starttime;
	}
	public String getMission_endtime() {
		return mission_endtime;
	}
	public void setMission_endtime(String mission_endtime) {
		this.mission_endtime = mission_endtime;
	}
	
	//���������췽��
	public Mission(Integer mission_id, Integer flag, String mission_content,
			String mission_starttime, String mission_endtime) {
		super();
		this.mission_id = mission_id;
		this.flag = flag;
		this.mission_content = mission_content;
		this.mission_starttime = mission_starttime;
		this.mission_endtime = mission_endtime;
	}
	//�޲����Ĺ��췽��
	public Mission() {
		super();
	}
	
	
	
	
	

}
