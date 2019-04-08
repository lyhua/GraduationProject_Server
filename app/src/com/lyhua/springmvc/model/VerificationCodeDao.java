package com.lyhua.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class VerificationCodeDao {
	
	private static VerificationCode verificationCode = new VerificationCode();
	
	
	//���ֻ�����֤��浽һ��
	public void PutCode(String phone,String code)
	{
		verificationCode.PutCodeWithPhone(phone,code);
	}
	
	//���ֻ�����֤���Ƴ�
	public void RemoveCode(String phone)
	{
		//TODO ʲôʱ��Űѳ�ʱ����ɾ��
		verificationCode.RemoveCodeWithPhone(phone);
	}
	
	
	//����Ӧ�ĵ绰����ȡ����֤��
	public String getCodeWithPhone(String phone)
	{
		return verificationCode.getCodeWithPhone(phone);
	}
	
	
}
