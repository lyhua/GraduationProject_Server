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
	
	
	//�����û�id�Ż��û��ķ�������
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
	
	
	
	//�û�������������ӵ����ݿ�
	@ResponseBody
	@RequestMapping("/createContent")
	public HashMap<String,Object>createContent(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
	{
		//TODO ��ȡ��Ȩ��
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
		//����ͼƬ��ͼ
		String contentBImage = "";
		
		//����ͼƬСͼ
		String contentSImage = "";
		
		
		//��ȡ��ǰ��������·��
		String realPath = request.getServletContext().getRealPath("/contentImage");
		//��ȡ����СͼƬ��·��
		String smallRealPath = request.getServletContext().getRealPath("/contentSmallImage");
		System.out.println("realPath"+realPath);
		System.out.println("smallRealPath"+smallRealPath);
		//����ǰ�����ĳ�ʼ����  CommonsMutipartResolver ���ಿ�ֽ�������
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //���form���Ƿ���enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
        	 //��request��ɶಿ��request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //��ȡmultiRequest �����е��ļ���
            Iterator iter=multiRequest.getFileNames();
            
            
            
            while(iter.hasNext())
            {
            	
            	//���ݵ�ǰ��ʱ����+�������ͼƬ������
                String imageName = Image.dateToMyFormat();
            	
            	//һ�α��������ļ�
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	//TODO ����Ҫ����ͼƬ���ࣨ������ͷ��Ҳ���������ӵ�ͼƬ��
                	//����ļ��ĺ�׺��
                	String contentType = file.getContentType();              	
                	String suffix = Image.imageSuffix(contentType);
                	System.out.println(suffix);
                	
                	//ƴ���ļ���ȫ����  ����  +�����
                	String path=realPath+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    String contentImageString = imageName + "." + suffix;
                    //ƴ�Ӵ�ͼ����
                    contentBImage += (contentImageString +"#");
                    
                  //�ϴ�ͼƬ��������
                    System.out.println("path:"+path);
                    file.transferTo(new File(path));
                  //���ͼƬ��size(width height)��λΪ����
                    Integer[] size = Image.imageSize(path);
                    System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
                 //����СͼƬ
                    String savePath = smallRealPath + "\\"+ "s" +imageName+"."+suffix;
                    ScaleImageUtils.saveSmallPictures(path, savePath, 0.25);
                    String sImageNmaeString = "s" +imageName+"."+suffix;
                    //ƴ��Сͼ
                    contentSImage += (sImageNmaeString + "#");
                }
            }
        }
     // ��ͼƬ�����ִ浽����ģ�͵����ݿ���
        int code = contentDao.createContent(content, contentBImage, mood1, userid,date,contentSImage,mission_id);
        //�û�������ݳɹ�
        if(code == 1018)
        {
        	map.put("msg", 1018);
        	return map;
        }
        //�û��������ʧ��
        map.put("error", 1017);
        return map;
	}
	
	
	//ɾ���û�����������
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
			//ɾ���û��������ݳɹ�
			map.put("msg", 1019);
			return map;
		}
		//ɾ���û���������ʧ��
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
		//��ȡͼƬ�ĳߴ�
		List<ImageSize> imagesSize  = imageSizeDao.getImagesSizeWithContents(contents,request);
		map.put("imagesSize", imagesSize);	
		return map;
	}
	
	
	
	
}
