package com.lyhua.springmvc.model;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Administrator
 * 短信验证
 */
public class VerificationCode {
	
	private  static Map<String,String> map = new HashMap<String,String>();
	
	
	//包验证码和手机放在一起
	public void PutCodeWithPhone(String phone,String code)
	{
		map.put(phone, code);
	}
	
	
	//把绑定验证码和手机移除
	public void RemoveCodeWithPhone(String phone)
	{
		map.remove(phone);
	}
	
	//从相应的电话号码取出验证码
	public String getCodeWithPhone(String phone)
	{
		return (String)map.get(phone);
	}
	
	
	

	public void setMap(Map<String, String> map) {
		this.map = map;
	}


	public Map<String, String> getMap() {
		return map;
	}


	@Override
	public String toString() {
		return "VerificationCode [map=" + map + "]";
	}


	public VerificationCode(Map<String, String> map) {
		super();
		this.map = map;
	}


	public VerificationCode() {
		super();
	}
	
	
	
	
	
	
	
	
}
