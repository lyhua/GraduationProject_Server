package com.lyhua.springmvc.model;

import org.springframework.stereotype.Repository;



//TODO �����û�б�Ҫ����

@Repository
public class HeadImageDao {
	

	public Integer[] getImageSize(String headImage)
	{
		//�����ݿ��в�ѯ�û�ͷ��Ĵ�С
		String sql = "select * from HeadImage where headImage ='"+headImage+"'";
		Integer[] size = new Integer[2];
		
		return size;
	}
	
	
	
	public void setUserHeadImage(String headimage)
	{
		//��ͼƬ
	}
}
