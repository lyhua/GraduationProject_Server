package com.lyhua.springmvc.model;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Administrator
 * ������֤
 */
public class VerificationCode {
	
	private  static Map<String,String> map = new HashMap<String,String>();
	
	
	//����֤����ֻ�����һ��
	public void PutCodeWithPhone(String phone,String code)
	{
		map.put(phone, code);
	}
	
	
	//�Ѱ���֤����ֻ��Ƴ�
	public void RemoveCodeWithPhone(String phone)
	{
		map.remove(phone);
	}
	
	//����Ӧ�ĵ绰����ȡ����֤��
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
