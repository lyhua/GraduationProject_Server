package com.lyhua.springmvc.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {
	
	
	/**�������ݿ��ض��ַ���
	 * 
	 * 
	 * @return
	 */
	public static String mysqlDateFormat()
	{
		return baseMyFormat("yyyy-MM-dd");
	}
	

	/**
	 * 
	 * @return �����ض����ַ��ܸ�ʽ
	 */
	public static String dateToMyFormat()
	{
		return baseMyFormat("MM-dd HH:mm");
	}
	
	/**
	 * ���ַ������� yyyyMMddHHmmss �����ĸ�ʽ
	 * @return �������������ʽ���ַ���
	 */
	public static String dateToMyFormat1()
	{
		return baseMyFormat("yyyyMMddHHmmss");
	}
	
	
	/**
	 * 
	 * @param format ���������ַ��ܸ�ʽ
	 * @return �����ַ���
	 */
	public static String baseMyFormat(String format)
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateToString = formatter.format(date);
		return dateToString;
	}
}
