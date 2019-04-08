package com.lyhua.springmvc.other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.lyhua.springmvc.model.User;

/**
 * 
 * @author Administrator
 * ͼƬ������ʽ����
 */
public class Image {
	
	
	/**
	 * 
	 * @param user �û�����
	 * @return �û�ͷ���ʽ      �û�id+��ǰ������
	 */
	public static String userHeadimage(User user)
	{
		int id = user.getUserId();
		String userHeadImageString = String.valueOf(id);
		return userHeadImageString;
	}
	
	
	/**
	 * 
	 * @param id �û�id
	 * @return �û�ͷ���ʽ       �û�id
	 */
	public static String userHeadimageWithId(int id)
	{
		User user = new User();
		user.setUserId(id);
		return Image.userHeadimage(user);
	}
	
	
	/**
	 * ���ַ������� yyyyMMddHHmmss �����ĸ�ʽ
	 * @return �������������ʽ���ַ��ܲ��Ҽ�6λ�����
	 */
	public static String dateToMyFormat()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateToString = formatter.format(date);
		return dateToString+RandomNum.CreateRandomNum(6);
	}
	
	
	
	/**
	 * 
	 * @param contentType ͼƬ������
	 * @return ͼƬ�ĺ�׺��
	 */
	public static String imageSuffix(String contentType)
	{
		String suffix = null;
		if (contentType.equals("image/jpeg"))
		{
			suffix = "jpg";
		}else if(contentType.equals("image/png"))
		{
			suffix = "png";
		}
		return suffix;
	}
	
	
	
	
	public static Integer[] imageSize(String fileName)
	{
		Integer width = null;
		Integer height = null;
		File file = new File(fileName);
		Integer[] size = new Integer[2];
		//�ж�����Ƿ����ļ����ļ��Ƿ����
		if(file.exists() && file.isFile())
		{
			BufferedImage bi = null;
			
			try {
				bi = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(bi != null)
			{
				width = bi.getWidth();
				height = bi.getHeight();
				size[0] = width;
				size[1] = height;
			}
		}
		return size;
	}
	
	public static void main(String[] args) {
//		User user = new User();
//		user.setId(1234);
//		String test = userHeadimage(user);
//		System.out.println(test);
//		System.out.println(dateToMyFormat());
		Integer[] size = Image.imageSize("F:\\javaEEwork\\app2\\WebContent\\headImage\\1.jpg");
		System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
	}

}
