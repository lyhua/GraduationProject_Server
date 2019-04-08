package com.lyhua.springmvc.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {
	
	
	/**生成数据库特定字符窜
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
	 * @return 生成特定的字符窜格式
	 */
	public static String dateToMyFormat()
	{
		return baseMyFormat("MM-dd HH:mm");
	}
	
	/**
	 * 把字符窜生成 yyyyMMddHHmmss 这样的格式
	 * @return 返回生成上面格式的字符窜
	 */
	public static String dateToMyFormat1()
	{
		return baseMyFormat("yyyyMMddHHmmss");
	}
	
	
	/**
	 * 
	 * @param format 生成日期字符窜格式
	 * @return 日期字符窜
	 */
	public static String baseMyFormat(String format)
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateToString = formatter.format(date);
		return dateToString;
	}
}
