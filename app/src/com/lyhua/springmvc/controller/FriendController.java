package com.lyhua.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyhua.springmvc.model.FriendDao;
import com.lyhua.springmvc.model.User;

@RequestMapping("/friend")
@Controller
public class FriendController {
	@Autowired
	private FriendDao friendDao;
	
	
	//�������
	@ResponseBody
	@RequestMapping("/applyForFriend")
	public HashMap<String,Object>appplyForFriend(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userId = request.getParameter("user_id");
		String myFriendId = request.getParameter("myFriend_id");
		int code = friendDao.appplyForFriend(Integer.valueOf(userId), Integer.valueOf(myFriendId));
		if(code==1047)
		{
			map.put("msg", 1047);
		}else{
			map.put("error", 1048);
		}
		return map;
	}
	
	//��ȡ���������
	@ResponseBody
	@RequestMapping("/getApplyForFriend")
	public HashMap<String,Object>getAppplyForFriend(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userId = request.getParameter("userId");
		ArrayList<User> users = friendDao.getAppplyForFriend(userId);
		map.put("users", users);
		
		return map;
	}
	
	
	//��Ӧ�������
	@ResponseBody
	@RequestMapping("/responseApplyForFriend")
	public HashMap<String,Object>responseApplyForFriend(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userId = request.getParameter("user_id");
		String myFriendId = request.getParameter("myFriend_id");
		Integer code = friendDao.responseApplyForFriend(Integer.valueOf(userId), Integer.valueOf(myFriendId));
		if(code == 1021)
		{
			//������ѳɹ�
			map.put("msg", 1021);
			return map;
		}
		//�������ʧ��
		map.put("error", 1022);
		return map;
	}
	
	//���ز��ֲ����ڿͻ��˺����б�
	@ResponseBody
	@RequestMapping("/getMyFriendsList")
	public HashMap<String,Object>getMyFriendsList(HttpServletRequest request,HttpServletResponse response)
	{
		//myExsitFriendList
		HashMap<String,Object> map = new HashMap<String,Object>();
		String[] myExsitFriendList1 = request.getParameterValues("myExsitFriendList");
		String myExsitFriendListString = request.getParameter("myExsitFriendListString");
		System.out.println("myExsitFriendListString"+myExsitFriendListString);
		String[] myExsitFriendListString1 = null;
		//�ж���ҳ�����б��Ƿ�Ϊ��
//		if(myExsitFriendList1 != null)
//		{
//			for(int i=0;i<myExsitFriendList1.length;i++)
//			{
//				System.out.println("id:"+myExsitFriendList1[i]);
//			}
//		}else{
//			System.out.println("null------------------------");
//		}
		Integer[] myExsitFriendList = null;
		if(myExsitFriendList1 != null)
		{
			myExsitFriendList = new Integer[myExsitFriendList1.length];
		}
		String userId = request.getParameter("user_id");
		//��ȡ���ں����б���ڲ��Ҳ�Ϊ�պͿ��ַ���
		if(myExsitFriendList1 != null)
		{
			for(int i=0;i<myExsitFriendList1.length;i++)
			{
				if(!myExsitFriendList1[i].equals(""))
				{
					myExsitFriendList[i] = Integer.valueOf(myExsitFriendList1[i]);
				}
			}
		}
		//�ж�IOS�ͻ��˺����б��Ƿ�Ϊ��
		if(myExsitFriendListString != null)
		{
			if(!myExsitFriendListString.equals(""))
			{
				myExsitFriendListString1 = myExsitFriendListString.split(":");
				myExsitFriendList = new Integer[myExsitFriendListString1.length];
			}
			if(myExsitFriendListString1 != null)
			{
				for(int i=0;i<myExsitFriendListString1.length;i++)
				{
					System.out.println("myExsitFriendListString1[]"+myExsitFriendListString1[i]);
					myExsitFriendList[i] = Integer.valueOf(myExsitFriendListString1[i]);
				}
			}
		}
		ArrayList<User> users = friendDao.getMyFriendsList(myExsitFriendList, Integer.valueOf(userId));
		map.put("friends", users);
		return map;
	}
	
	//�����û����к����б�
	@ResponseBody
	@RequestMapping("/getUserAllFirendsWithUserId")
	public HashMap<String,Object>getUserAllFirendsWithUserId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String user_id = request.getParameter("user_id");
		ArrayList<User> users = friendDao.getUserAllFirendsWithUserId(Integer.valueOf(user_id));
		map.put("friends", users);
		return map;
	}
	
	
	
	
	
	
}
