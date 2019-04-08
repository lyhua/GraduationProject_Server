package com.lyhua.springmvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.lyhua.springmvc.other.Image;


@Repository
public class ImageSizeDao {

	/**
	 * 
	 * @param content
	 * @param request
	 * @return ��һ��ͼƬ�ĳߴ�
	 */
	public ImageSize getImageSizeWithContent(Content content,HttpServletRequest request)
	{
		//��ȡ��ǰ��������·��
		String path = request.getServletContext().getRealPath("/contentSmallImage");
		//��ȡ����ͼƬ����
		String images = content.getContentSmallImage();
		//��ȡ��һ��1ͼƬ������
		String[] images1 = images.split("#");
		String name = null;
		if(images1.length > 0)
		{
			name = images1[0];
		}
		if(name != null)
		{
			String realPath = path + "/" + images1[0];
			System.out.println("path"+path);
			Integer[] size = Image.imageSize(realPath);
			ImageSize imageSize = new ImageSize(size[0].doubleValue(), size[1].doubleValue());
			return imageSize;
		}
		return new ImageSize(0, 0);
	}
	
	
	
	/**
	 * 
	 * @param contents
	 * @param request
	 * @return ����������ÿ���ĵ�һ��ͼƬ
	 */
	public List<ImageSize> getImagesSizeWithContents(List<Content> contents,HttpServletRequest request)
	{
		List<ImageSize> imagesSize = new ArrayList<ImageSize>();
		for (int i = 0; i < contents.size(); i++) {
			imagesSize.add(getImageSizeWithContent(contents.get(i),request));
		}
		return imagesSize;
	}
}
