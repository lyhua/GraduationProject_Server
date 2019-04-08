package com.lyhua.springmvc.other;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SqlInjectInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	   Object arg2) throws Exception 
	 {
		Enumeration<String> names = request.getParameterNames(); 
		while (names.hasMoreElements())
		{ 
			String name = names.nextElement(); 
			String[] values = request.getParameterValues(name); 
			for (String value : values) 
			{ 
				value = clearXss(value); 
				} 
			} return true;

	 }
	
	
	/** * 处理字符转义 * * @param value * @return */ 
	public static String clearXss(String value) 
	{ 
		if (value == null || "".equals(value)) 
		{ 
			return value; 
		} 
		value = value.replaceAll("<", "<").replaceAll(">", ">"); 
		value = value.replaceAll("\\(", "(").replace("\\)", ")"); 
		value = value.replaceAll("'", "'"); 
		value = value.replaceAll("eval\\((.*)\\)", ""); 
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\""); 
		value = value.replace("script", ""); 
		return value; 
	}


}
