package com.lyhua.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lyhua.springmvc.model.User;
import com.lyhua.springmvc.model.UserDao;
import com.lyhua.springmvc.model.VerificationCodeDao;
import com.lyhua.springmvc.other.Image;
import com.lyhua.springmvc.other.MyDateFormat;
import com.lyhua.springmvc.other.RandomNum;
import com.lyhua.springmvc.other.Sendsms;
import com.lyhua.springmvc.other.SqlInjectInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@Controller
public class LoginController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private VerificationCodeDao verificationCodeDao;
	
	//�޸ĸ�����Ϣ
	@ResponseBody
	@RequestMapping(value="/updateUserWithUserId")
	public HashMap<String,Object>updateUserWithUserId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userId=request.getParameter("userId");
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		String gender = request.getParameter("gender");
		String email =request.getParameter("email");
		int code = userDao.updateUserWithUserId(userId, name, age, gender, email);
		if(code==1049)
		{
			map.put("msg", 1049);
		}else{
			map.put("error", 1050);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/getalluserwithjson")
	public HashMap<String,Collection<User>> getAllUserWithJSON()
	{
		System.out.println("123456");
		HashMap<String,Collection<User>> map = new HashMap<String,Collection<User>>();
		map.put("users", userDao.getAlluser());
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/IsUser")
	public HashMap<String,String> IsUser(HttpServletRequest request,
			HttpServletResponse response)
	{
		String name = request.getParameter("name");
		String name1 = SqlInjectInterceptor.clearXss(name);
		String password = request.getParameter("password");
		String password1 = SqlInjectInterceptor.clearXss(password);
		System.out.println(password);
		System.out.println(name);
		boolean isUser = userDao.IsLegalUser(name1, password1);
		HashMap<String,String> map = new HashMap<String,String>();
		if(isUser)
		{
			map.put("msg", "legal user");
			System.out.println("legal user");
		}
		else{
			map.put("msg", "illegal user");
			System.out.println("illegal user");
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/getUserWithId")
	public HashMap<String,Object> getUserWithId(HttpServletRequest request,
			HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		int userId = Integer.valueOf(request.getParameter("userId"));
		User user = userDao.getUserWithId(userId);
		
		if (user != null)
		{
			map.put("user", user);
		}else{
			map.put("error", "�û�������");
		}
		return map;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/Register")
	public HashMap<String,Object> Register(HttpServletRequest request,
			HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String verificationCode = request.getParameter("verificationCode");//��֤��
		int code = userDao.Register(phone, verificationCode,password);
		if(code == 1001)
		{
			map.put("error", 1001);
			return map;
		} else if(code == 1007)
		{
			map.put("error", 1007);
			return map;
		}else if(code == 1014)
		{
			map.put("error", 1014);
		}else if(code == 1016)
		{
			map.put("error", 1016);
		}
		//��ʾע��ɹ�
		map.put("msg", 1);
		
		return map;
	}
	
	/*
	 * code == 2001 �����޸�����
	 * code == 2002�����޸��Ա�
	 * code == 2003�����޸��û���
	 */
	@ResponseBody
	@RequestMapping("/AlterUserImformation")
	public HashMap<String,Object>AlterUserImformation(HttpServletRequest request,
			HttpServletResponse response)
	{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String code = request.getParameter("code");
		String userId = request.getParameter("userId");
		String phone = request.getParameter("phone");
		
		map1.put("name", name);
		map1.put("password", password);
		map1.put("gender", gender);
		map1.put("code", code);
		map1.put("userId", userId);
		int code1 = userDao.AlterUserImformation(map1);
		//�޸��������
		if (code1 == 1003)
		{
			map.put("error", 1003);
			return map;
		}
		//�޸��Ա����
		if (code1 == 1004)
		{
			map.put("error", 1004);
			return map;
		}
		//�޸��û�������
		if (code1 == 1005)
		{
			map.put("error", 1005);
			return map;
		}
		//��ʾ�����ɹ�
		map.put("msg", 1);
		
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/alterUserPasswordWithPhone")
	public HashMap<String,Object> alterUserPasswordWithPhone(HttpServletRequest request,
			HttpServletResponse response)
	{
		//TODO ����ɵ绰����������޸�����
		HashMap<String,Object> map = new HashMap<String,Object>();
		String phone = request.getParameter("phone");
		String verificationCode = request.getParameter("verificationCode");
		String password = request.getParameter("password");
		int code = userDao.alterUserPasswordWithPhone(phone, password, verificationCode);
		//��֤��Ϊ�յĴ������
		if(code == 1013)
		{
			map.put("error", 1013);
			return map;
		}else if(code == 1014)
		{
			//���������֤�벻һ��������֤�벻һ�´������
			map.put("error", 1014);
			return map;
		}else if(code == 1015)
		{
			//�޸��û�����ʧ�� ���ݿ�����
			map.put("error", 1015);
			return map;
		}
		//��ʾ�����ɹ�
		map.put("msg", 1);
		return map;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/Login")
	public HashMap<String,Object>Login(HttpServletRequest request,
			HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		User user = null;
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		int code = userDao.Login(name, password, phone);
		if(code == 1006)
		{
			map.put("error", 1006);
			return map;
		}
		//�����¼�ɹ���Ĭ�����в����ɹ�Ϊ1
		//TODO �����û���Ϣ���û���Ȩ��
		user = userDao.getUserWithPhone(phone);
		Integer[] size = userDao.getUserHeadimageSizeWithId(user.getUserId(), request);
		System.out.println("size width and height :"+size[0] +" "+ size[1]);
		map.put("msg", 1);
		map.put("user", user);
		map.put("size", size);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/sendCode")
	public HashMap<String,Object> sendCode(HttpServletRequest request,
			HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String phone = request.getParameter("phone");
		String account = "M12371761";//������֤�������Ǳߵ��û���
		String password = "533f4a4f0382fb2aa94be5290b14e424";//������֤�Ǳߵ�����(APIKEY)
		//TODO ��֤�ͻ��˷����ĺ����Ƿ�Ϊ�Ϸ��ֻ�����
		
		//������֤��
		String mobile_code = RandomNum.CreateRandomNum(4);
		System.out.println("code +++" + mobile_code);
		//����֤�����ֻ�����һ��
		verificationCodeDao.PutCode(phone, mobile_code);
		//���Ͷ�����֤
		int backcode = Sendsms.senfCode(account, password, phone, mobile_code);
		
		if(backcode == 1008)
		{
			//���ͳɹ�
			map.put("msg", 1008);
			return map;
		}
		//����ʧ��
		map.put("error", 1008);
		return map;
	}
	
	
	//�޸��û�ͷ��
	@ResponseBody
	@RequestMapping("/alterUserHeadImage")
	public HashMap<String,Object>alterUserHeadImage(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userId = request.getParameter("userId");
		System.out.println(userId);
		//��ȡ��ǰ��������·��
		String realPath = request.getServletContext().getRealPath("/headImage");
		System.out.println(realPath);
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
            
            //���Ը����û�id����������ͼƬ
            User user = new User();
            user.setUserId(Integer.valueOf(userId));
            String imageName = Image.userHeadimage(user)+MyDateFormat.dateToMyFormat1();
             
            while(iter.hasNext())
            {
                //һ�α��������ļ�
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	//TODO ����Ҫ����ͼƬ���ࣨ������ͷ��Ҳ���������ӵ�ͼƬ��
                	//����ļ��ĺ�׺��
                	String contentType = file.getContentType();              	
                	String suffix = Image.imageSuffix(contentType);
                	System.out.println(suffix);
                	
                	//ƴ���ļ���ȫ����  ����+�û�id
                	String path=realPath+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    String userHeadImage = imageName + "." + suffix;
                    //�ϴ�
                    System.out.println("path:"+path);
                    file.transferTo(new File(path));
                    //���ͼƬ��size(width height)��λΪ����
                    Integer[] size = Image.imageSize(path);
                    System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
                    // ��ͼƬ�����ִ浽�û�ģ�͵����ݿ���
                    int code = userDao.alterUserHeadImage(userHeadImage, Integer.valueOf(userId));
                    if (code == 1011)
                    {
                    	//�޸��û�ͷ��ʧ��
                    	map.put("error", 1011);
                    }
                    else{
                    	//�޸��û�ͷ��ɹ�
                    	map.put("msg", 1012);
                    }
                }
            }
        }
		return map;
	}
	
	
	
    //TODO �����ļ����ϴ�
	//����ͷ��˷������ļ�(���������ļ�)
	@ResponseBody
	@RequestMapping("/springUpload")
    public void  springUpload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
    {
		//��ȡ��ǰ��������·��
		String realPath = request.getServletContext().getRealPath("/headImage");
		String path1 = request.getServletPath();
		System.out.println(realPath);
		System.out.println(path1);
		
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
            
            //���Ը����û�id����ͼƬ
            User user = new User();
            user.setUserId(1234);
            String imageName = Image.userHeadimage(user);
             
            while(iter.hasNext())
            {
                //һ�α��������ļ�
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	//TODO ����Ҫ����ͼƬ���ࣨ������ͷ��Ҳ���������ӵ�ͼƬ��
                	//����ļ��ĺ�׺��
                	String contentType = file.getContentType();              	
                	String suffix = Image.imageSuffix(contentType);
                	System.out.println(suffix);
                	
                	//ƴ���ļ���ȫ����  ����+�û�id
                	String path=path1+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    //�ϴ�
                    System.out.println(path);
                    file.transferTo(new File(path));
                }
                 
            }
           
        }
    }
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping("/getUserWithPhone")
	public HashMap<String,Object> getUserWithPhone(HttpServletRequest request,HttpServletResponse response)
	{
		String phone = request.getParameter("phone");
		HashMap<String,Object> map = new HashMap<String,Object>();
		User user = userDao.getUserWithPhone(phone);
		if(user == null)
		{
			map.put("error", 1046);
		}else{
			map.put("user", user);
		}
		return map;
	}
	
	@RequestMapping("/testtest")
	public String testtest(HttpServletRequest request,HttpServletResponse response)
	{
		return "redirect:http://192.168.1.103:8000/scansystem/theme/index.php?content=123456789";
	}
	

}
