package com.lyhua.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyhua.springmvc.model.TelephoneDao;

@RequestMapping("/telephone")
@Controller
public class TelephoneController {
	
	@Autowired
	private TelephoneDao telephoneDao;
	
	
	@ResponseBody
	@RequestMapping("/saveContacts")
	public HashMap<String,Object>saveContacts(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String[] phoneNumbers = null;
		String[] contactNames = null;
		String[] temp1 = null;
		String[] temp2 = null;
		String phoneNumber1 = null;
		String contactName1 = null;
	    temp1 = request.getParameterValues("phoneNumbers");
	    temp2 = request.getParameterValues("contactNames");
		String phoneNumber = request.getParameter("phoneNumber");
		String contactName = request.getParameter("contactName");
		System.out.println("phoneNumber----------------"+phoneNumber);
		System.out.println("contactName----------------"+contactName);
		if (temp1 != null) {
			phoneNumbers = temp1;
		}
		if (temp2 != null) {
			contactNames = temp2;
		}
		if (phoneNumber != null) {
			try {
				phoneNumber1 = new String(phoneNumber.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("phoneNumber1"+phoneNumber1);
			phoneNumbers = phoneNumber1.split(",");
		}
		if (contactName != null) {
			try {
				contactName1 = new String(contactName.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("contactName1"+contactName1);
			contactNames = contactName1.split(",");
		}
		String user_id = request.getParameter("user_id");
		Integer userId = Integer.valueOf(user_id);
		Integer code = telephoneDao.saveContacts(phoneNumbers, contactNames, userId);
		if (code == 1027) {
			map.put("msg", 1027);
		}else{
			map.put("error", 1028);
		}
		
		return map;
	}
}
