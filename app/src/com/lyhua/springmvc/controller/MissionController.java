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
	
	
	//用于创建亲子互动任务
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
		//TODO 这里可能有错误
		int code = missionDao.createMission(Content, mission_starttime, mission_endtime);
		if(code==1029)
		{
			//创建任务成功
			map.put("msg", 1029);
		}else{
			//创建任务失败
			map.put("error",1030);
		}
		return map;
	}
	
	//用于删除亲子互动任务
	@ResponseBody
	@RequestMapping("/deleteMissionWithId")
	public HashMap<String,Object>deleteMissionWithId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String mission_id = request.getParameter("mission_id");
		int code = missionDao.deleteMissionWithId(mission_id);
		if(code==1031)
		{
			//删除任务成功
			map.put("msg", 1031);
		}else{
			//删除任务失败
			map.put("error",1032);
		}
		return map;
	}
	
	//用于返回所有任务
	@ResponseBody
	@RequestMapping("/getMission")
	public HashMap<String,Object>getMission(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Mission> missions = missionDao.getMission();
		map.put("missions", missions);
		return map;
	}
	
	//更新亲子任务是否有效
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
			//更新亲子任务flag(是否有效)成功
			map.put("msg", 1042);
		}else{
			//更新亲子任务flag(是否有效)失败
			map.put("error",1043);
		}
		return map;
	}
	
	//创建用户与任务的联系
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
			//插入任务用户表数据成功
			map.put("msg", 1034);
		}else{
			//插入任务用户表数据失败
			map.put("error",1035);
		}
		return map;
	}
	
	
	//设置任务是否完成
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
			//更新任务用户表flag(是否完成)成功
			map.put("msg", 1036);
		}else{
			//更新任务用户表flag(是否完成)失败
			map.put("error",1037);
		}
		
		return map;
	}
	
	
	

}
