package com.lyhua.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyhua.springmvc.model.Mission;
import com.lyhua.springmvc.model.MissionDao;
import com.lyhua.springmvc.model.MissionUserDao;

@RequestMapping("/mission")
@Controller
public class MissionController {
	
	@Autowired
	private MissionDao missionDao;
	
	@Autowired
	private MissionUserDao missionUserDao;
	
	
	//���ڴ������ӻ�������
	@ResponseBody
	@RequestMapping("/createMission")
	public HashMap<String,Object>createMission(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String mission_content = request.getParameter("mission_content");
		String mission_starttime = request.getParameter("mission_starttime");
		String mission_endtime = request.getParameter("mission_endtime");
		String Content=null;
		System.out.println("mission_content"+mission_content);
		try {
			Content = new String(mission_content.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after change:"+Content);
		//TODO ��������д���
		int code = missionDao.createMission(Content, mission_starttime, mission_endtime);
		if(code==1029)
		{
			//��������ɹ�
			map.put("msg", 1029);
		}else{
			//��������ʧ��
			map.put("error",1030);
		}
		return map;
	}
	
	//����ɾ�����ӻ�������
	@ResponseBody
	@RequestMapping("/deleteMissionWithId")
	public HashMap<String,Object>deleteMissionWithId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String mission_id = request.getParameter("mission_id");
		int code = missionDao.deleteMissionWithId(mission_id);
		if(code==1031)
		{
			//ɾ������ɹ�
			map.put("msg", 1031);
		}else{
			//ɾ������ʧ��
			map.put("error",1032);
		}
		return map;
	}
	
	//���ڷ�����������
	@ResponseBody
	@RequestMapping("/getMission")
	public HashMap<String,Object>getMission(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Mission> missions = missionDao.getMission();
		map.put("missions", missions);
		return map;
	}
	
	//�������������Ƿ���Ч
	@ResponseBody
	@RequestMapping("/updateMissionFlag")
	public HashMap<String,Object>updateMissionFlag(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String flag=request.getParameter("flag");
		String mission_id=request.getParameter("mission_id");
		int code = missionDao.updateMissionFlag(flag, mission_id);
		if(code==1042)
		{
			//������������flag(�Ƿ���Ч)�ɹ�
			map.put("msg", 1042);
		}else{
			//������������flag(�Ƿ���Ч)ʧ��
			map.put("error",1043);
		}
		return map;
	}
	
	//�����û����������ϵ
	@ResponseBody
	@RequestMapping("/createMissionUser")
	public HashMap<String,Object>createMissionUser(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String user_id=request.getParameter("user_id");
		String mission_id=request.getParameter("mission_id");
		int code = missionUserDao.createMissionUser(user_id, mission_id);
		if(code==1034)
		{
			//���������û������ݳɹ�
			map.put("msg", 1034);
		}else{
			//���������û�������ʧ��
			map.put("error",1035);
		}
		return map;
	}
	
	
	//���������Ƿ����
	@ResponseBody
	@RequestMapping("/updateMissionUserFlag")
	public HashMap<String,Object>updateMissionUserFlag(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String flag=request.getParameter("flag");
		String mission_user_id=request.getParameter("mission_user_id");
		int code =missionUserDao.updateMissionUserFlag(flag, mission_user_id);
		if(code==1036)
		{
			//
			//���������û���flag(�Ƿ����)�ɹ�
			map.put("msg", 1036);
		}else{
			//���������û���flag(�Ƿ����)ʧ��
			map.put("error",1037);
		}
		
		return map;
	}
	
	
	

}
