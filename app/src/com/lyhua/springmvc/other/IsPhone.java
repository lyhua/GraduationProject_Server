package com.lyhua.springmvc.other;
import java.io.IOException;
import java.util.regex.Matcher;  
import java.util.regex.Pattern; 

//�ж��ֻ������Ƿ���ȷ
public class IsPhone {
	public static boolean isMobileNO(String mobiles)
	{
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles); 
		return m.matches();  
	}
	
	//�����ֻ�����
	public static void main(String[] args) throws IOException 
    {
    	System.out.println(IsPhone.isMobileNO("13543828098"));  
    }

}
