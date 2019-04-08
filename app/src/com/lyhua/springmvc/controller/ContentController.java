package com.lyhua.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lyhua.springmvc.model.Content;
import com.lyhua.springmvc.model.ContentDao;
import com.lyhua.springmvc.model.HeadImageDao;
import com.lyhua.springmvc.model.ImageSize;
import com.lyhua.springmvc.model.ImageSizeDao;
import com.lyhua.springmvc.model.UserDao;
import com.lyhua.springmvc.other.Image;
import com.lyhua.springmvc.other.ScaleImageUtils;

@RequestMapping("/content")
@Controller
public class ContentController {
	
	@Autowired
	private ContentDao contentDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ImageSizeDao imageSizeDao;
	
	
	//根据用户id放回用户的发布内容
	@ResponseBody
	@RequestMapping("/getContentWithUserID")
	public HashMap<String,Object>getContentWithUserID(HttpServletRequest request,HttpServletResponse response)
	{
		String user_id = request.getParameter("user_id");
		Integer id = Integer.valueOf(user_id);
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Content> content = contentDao.getContentWithUser_ID(id);
		map.put("content", content);
		return map;
	}
	
	
	
	//用户发布的内容添加到数据库
	@ResponseBody
	@RequestMapping("/createContent")
	public HashMap<String,Object>createContent(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
	{
		//TODO 获取授权码
		String user_id = request.getParameter("user_id");
		String content = request.getParameter("content");
		String mood = request.getParameter("mood");
		System.out.println("user_id"+user_id);
		System.out.println("content"+content);
		System.out.println("mood"+mood);
		Integer userid = Integer.valueOf(user_id);
		Integer mood1 = Integer.valueOf(mood);
		String date = request.getParameter("date");
		System.out.println("date"+date);
		String mission_id = request.getParameter("mission_id");
		System.out.println("mission_id"+mission_id);
		HashMap<String,Object> map = new HashMap<String,Object>();
		//内容图片大图
		String contentBImage = "";
		
		//内容图片小图
		String contentSImage = "";
		
		
		//获取当前服务器的路径
		String realPath = request.getServletContext().getRealPath("/contentImage");
		//获取保存小图片的路径
		String smallRealPath = request.getServletContext().getRealPath("/contentSmallImage");
		System.out.println("realPath"+realPath);
		System.out.println("smallRealPath"+smallRealPath);
		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
        	 //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            
            
            
            while(iter.hasNext())
            {
            	
            	//根据当前的时间生+随机数成图片的名字
                String imageName = Image.dateToMyFormat();
            	
            	//一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	//TODO 这里要区分图片种类（可能是头像，也可能是帖子的图片）
                	//获得文件的后缀名
                	String contentType = file.getContentType();              	
                	String suffix = Image.imageSuffix(contentType);
                	System.out.println(suffix);
                	
                	//拼接文件的全名称  日期  +随机数
                	String path=realPath+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    String contentImageString = imageName + "." + suffix;
                    //拼接大图内容
                    contentBImage += (contentImageString +"#");
                    
                  //上传图片到服务器
                    System.out.println("path:"+path);
                    file.transferTo(new File(path));
                  //获得图片的size(width height)单位为像素
                    Integer[] size = Image.imageSize(path);
                    System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
                 //保存小图片
                    String savePath = smallRealPath + "\\"+ "s" +imageName+"."+suffix;
                    ScaleImageUtils.saveSmallPictures(path, savePath, 0.25);
                    String sImageNmaeString = "s" +imageName+"."+suffix;
                    //拼接小图
                    contentSImage += (sImageNmaeString + "#");
                }
            }
        }
     // 将图片的名字存到内容模型的数据库中
        int code = contentDao.createContent(content, contentBImage, mood1, userid,date,contentSImage,mission_id);
        //用户添加内容成功
        if(code == 1018)
        {
        	map.put("msg", 1018);
        	return map;
        }
        //用户添加内容失败
        map.put("error", 1017);
        return map;
	}
	
	
	//删除用户发布的内容
	@ResponseBody
	@RequestMapping("/deleteContentWithContentID")
	public HashMap<String,Object>deleteContentWithContentID(HttpServletRequest request,HttpServletResponse response)
	{
		String content_id = request.getParameter("content_id");
		Integer contentId = Integer.valueOf(content_id);
		HashMap<String,Object> map = new HashMap<String,Object>();
		int code = contentDao.deleteContentWithContentID(contentId, request);
		if (code == 1019)
		{
			//删除用户发布内容成功
			map.put("msg", 1019);
			return map;
		}
		//删除用户发布内容失败
		map.put("error", 1020);
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/getContentsWithContentId")
	public HashMap<String,Object>getContentsWithContentId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String content_id = request.getParameter("content_id");
		Integer contentId ;
		boolean newOrOld;
		if(content_id != null)
		{
			 contentId = (content_id.equals("")) ? null : Integer.valueOf(content_id);
		}else{
			 contentId = null;
		}
		String NewOROld = request.getParameter("newOrOld");
		if(NewOROld != null)
		{
			newOrOld = (NewOROld.equals("1")) ? true : false;
		}else{
			newOrOld = true;
		}
		System.out.println("newOrOld :"+newOrOld);
		List<Content> contents = contentDao.getContentsWithContentId(contentId, newOrOld, 10);
		map.put("contents", contents);
		//获取图片的尺寸
		List<ImageSize> imagesSize  = imageSizeDao.getImagesSizeWithContents(contents,request);
		map.put("imagesSize", imagesSize);	
		return map;
	}
	
	
	
	
}
