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
	 * @return 第一张图片的尺寸
	 */
	public ImageSize getImageSizeWithContent(Content content,HttpServletRequest request)
	{
		//获取当前服务器的路径
		String path = request.getServletContext().getRealPath("/contentSmallImage");
		//获取所有图片名称
		String images = content.getContentSmallImage();
		//获取第一张1图片的名称
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
	 * @return 内容数组中每条的第一张图片
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
