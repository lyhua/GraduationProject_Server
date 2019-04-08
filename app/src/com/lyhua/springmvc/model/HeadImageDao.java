package com.lyhua.springmvc.model;

import org.springframework.stereotype.Repository;



//TODO 这个类没有必要存在

@Repository
public class HeadImageDao {
	

	public Integer[] getImageSize(String headImage)
	{
		//从数据库中查询用户头像的大小
		String sql = "select * from HeadImage where headImage ='"+headImage+"'";
		Integer[] size = new Integer[2];
		
		return size;
	}
	
	
	
	public void setUserHeadImage(String headimage)
	{
		//把图片
	}
}
