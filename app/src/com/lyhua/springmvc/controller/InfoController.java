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

import com.lyhua.springmvc.model.Info;
import com.lyhua.springmvc.model.InfoDao;

@RequestMapping("/info")
@Controller
public class InfoController {
	
	@Autowired
	private InfoDao infoDao;
	
	//创建消息
	@ResponseBody
	@RequestMapping("/createInfo")
	public HashMap<String,Object>createInfo(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String info_content = request.getParameter("info_content");
		String send_id = request.getParameter("send_id");
		String receive_id = request.getParameter("receive_id");
		String send_date = request.getParameter("send_date");
		String receive_date = request.getParameter("receive_date");
		
		String content = null;
		
		try {
			content = new String(info_content.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int code = infoDao.createInfo(content, send_id, receive_id, send_date, receive_date);
		if(code==1044)
		{
			//创建任务成功
			map.put("msg", 1044);
		}else{
			//创建任务失败
			map.put("error",1045);
		}
		return map;
	}
	
	//根据用户和当天的时间返回消息
	@ResponseBody
	@RequestMapping("/getInfoWithUserId")
	public HashMap<String,Object>getInfoWithUserId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String user_id = request.getParameter("user_id");
		List<Info> infos = infoDao.getInfoWithUserId(user_id);
		map.put("infos", infos);
		return map;
	}
	

}
