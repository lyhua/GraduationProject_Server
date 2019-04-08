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
	
	//修改个人信息
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
			map.put("error", "用户不存在");
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
		String verificationCode = request.getParameter("verificationCode");//验证码
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
		//表示注册成功
		map.put("msg", 1);
		
		return map;
	}
	
	/*
	 * code == 2001 代表修改密码
	 * code == 2002代表修改性别
	 * code == 2003代表修改用户名
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
		//修改密码错误
		if (code1 == 1003)
		{
			map.put("error", 1003);
			return map;
		}
		//修改性别错误
		if (code1 == 1004)
		{
			map.put("error", 1004);
			return map;
		}
		//修改用户名错误
		if (code1 == 1005)
		{
			map.put("error", 1005);
			return map;
		}
		//表示操作成功
		map.put("msg", 1);
		
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/alterUserPasswordWithPhone")
	public HashMap<String,Object> alterUserPasswordWithPhone(HttpServletRequest request,
			HttpServletResponse response)
	{
		//TODO 添加由电话号码和邮箱修改密码
		HashMap<String,Object> map = new HashMap<String,Object>();
		String phone = request.getParameter("phone");
		String verificationCode = request.getParameter("verificationCode");
		String password = request.getParameter("password");
		int code = userDao.alterUserPasswordWithPhone(phone, password, verificationCode);
		//验证码为空的错误代码
		if(code == 1013)
		{
			map.put("error", 1013);
			return map;
		}else if(code == 1014)
		{
			//如果两个验证码不一样返回验证码不一致错误代码
			map.put("error", 1014);
			return map;
		}else if(code == 1015)
		{
			//修改用户密码失败 数据库问题
			map.put("error", 1015);
			return map;
		}
		//表示操作成功
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
		//代表登录成功，默认所有操作成功为1
		//TODO 返回用户信息和用户授权码
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
		String account = "M12371761";//短信验证服务器那边的用户名
		String password = "533f4a4f0382fb2aa94be5290b14e424";//短信验证那边的密码(APIKEY)
		//TODO 验证客户端发来的号码是否为合法手机号码
		
		//产生验证码
		String mobile_code = RandomNum.CreateRandomNum(4);
		System.out.println("code +++" + mobile_code);
		//把验证码与手机绑定在一起
		verificationCodeDao.PutCode(phone, mobile_code);
		//发送短信验证
		int backcode = Sendsms.senfCode(account, password, phone, mobile_code);
		
		if(backcode == 1008)
		{
			//发送成功
			map.put("msg", 1008);
			return map;
		}
		//发送失败
		map.put("error", 1008);
		return map;
	}
	
	
	//修改用户头像
	@ResponseBody
	@RequestMapping("/alterUserHeadImage")
	public HashMap<String,Object>alterUserHeadImage(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userId = request.getParameter("userId");
		System.out.println(userId);
		//获取当前服务器的路径
		String realPath = request.getServletContext().getRealPath("/headImage");
		System.out.println(realPath);
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
            
            //测试根据用户id和日期生成图片
            User user = new User();
            user.setUserId(Integer.valueOf(userId));
            String imageName = Image.userHeadimage(user)+MyDateFormat.dateToMyFormat1();
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	//TODO 这里要区分图片种类（可能是头像，也可能是帖子的图片）
                	//获得文件的后缀名
                	String contentType = file.getContentType();              	
                	String suffix = Image.imageSuffix(contentType);
                	System.out.println(suffix);
                	
                	//拼接文件的全名称  日期+用户id
                	String path=realPath+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    String userHeadImage = imageName + "." + suffix;
                    //上传
                    System.out.println("path:"+path);
                    file.transferTo(new File(path));
                    //获得图片的size(width height)单位为像素
                    Integer[] size = Image.imageSize(path);
                    System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
                    // 将图片的名字存到用户模型的数据库中
                    int code = userDao.alterUserHeadImage(userHeadImage, Integer.valueOf(userId));
                    if (code == 1011)
                    {
                    	//修改用户头像失败
                    	map.put("error", 1011);
                    }
                    else{
                    	//修改用户头像成功
                    	map.put("msg", 1012);
                    }
                }
            }
        }
		return map;
	}
	
	
	
    //TODO 测试文件的上传
	//处理客服端发来的文件(包括各种文件)
	@ResponseBody
	@RequestMapping("/springUpload")
    public void  springUpload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
    {
		//获取当前服务器的路径
		String realPath = request.getServletContext().getRealPath("/headImage");
		String path1 = request.getServletPath();
		System.out.println(realPath);
		System.out.println(path1);
		
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
            
            //测试根据用户id生成图片
            User user = new User();
            user.setUserId(1234);
            String imageName = Image.userHeadimage(user);
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	//TODO 这里要区分图片种类（可能是头像，也可能是帖子的图片）
                	//获得文件的后缀名
                	String contentType = file.getContentType();              	
                	String suffix = Image.imageSuffix(contentType);
                	System.out.println(suffix);
                	
                	//拼接文件的全名称  日期+用户id
                	String path=path1+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    //上传
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
