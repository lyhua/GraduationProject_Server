package com.lyhua.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class VerificationCodeDao {
	
	private static VerificationCode verificationCode = new VerificationCode();
	
	
	//把手机和验证码存到一起
	public void PutCode(String phone,String code)
	{
		verificationCode.PutCodeWithPhone(phone,code);
	}
	
	//把手机和验证码移除
	public void RemoveCode(String phone)
	{
		//TODO 什么时候才把超时短信删除
		verificationCode.RemoveCodeWithPhone(phone);
	}
	
	
	//从相应的电话号码取出验证码
	public String getCodeWithPhone(String phone)
	{
		return verificationCode.getCodeWithPhone(phone);
	}
	
	
}
