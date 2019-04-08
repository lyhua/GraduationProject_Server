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

import com.lyhua.springmvc.model.Reward;
import com.lyhua.springmvc.model.RewardDao;
import com.lyhua.springmvc.model.RewardUserDao;
import com.lyhua.springmvc.model.User;
import com.lyhua.springmvc.other.Image;
import com.lyhua.springmvc.other.MyDateFormat;

@RequestMapping("/reward")
@Controller
public class RewardController {
	
	@Autowired
	private RewardDao rewardDao;
	
	@Autowired
	private RewardUserDao rewardUserDao;
	
	
	//创建奖励（要上传图片）
	@ResponseBody
	@RequestMapping("/createReward")
	public HashMap<String,Object>createReward(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String reward_name=request.getParameter("reward_name");
		String mission_id=request.getParameter("mission_id");
		
		//获取奖励图片
		//获取当前服务器的路径
		String realPath = request.getServletContext().getRealPath("/missionImage");
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
            
            //测试根据mission和日期生成图片
            String imageName = mission_id+MyDateFormat.dateToMyFormat1();
             
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
                	
                	//拼接文件的全名称  日期+missionid
                	String path=realPath+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    String userHeadImage = imageName + "." + suffix;
                    //上传
                    System.out.println("path:"+path);
                    file.transferTo(new File(path));
                    //获得图片的size(width height)单位为像素
                    Integer[] size = Image.imageSize(path);
                    System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
                    
                    // 将图片的名字存到mission模型的数据库中
                    int code = rewardDao.createReward(reward_name, userHeadImage, mission_id);
                    if (code == 1039)
                    {
                    	//创建奖励成功
                    	map.put("error", 1039);
                    }
                    else{
                    	//创建奖励失败
                    	map.put("msg", 1038);
                    }
                }
            }
        }
		return map;
	}
	
	//创建用户与奖励之间的联系
	@ResponseBody
	@RequestMapping("/createRewardUser")
	public HashMap<String,Object>createRewardUser(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String reward_id= request.getParameter("reward_id");
		String user_id = request.getParameter("user_id");
		int code = rewardUserDao.createRewardUser(reward_id, user_id);
		if(code==1040)
		{
			//
			//创建用户与奖励关联成功
			map.put("msg", 1040);
		}else{
			//创建用户与奖励关联失败
			map.put("error", 1041);
		}
		return map;
	}
	
	
	//根据用户id返回用户 的奖励
	@ResponseBody
	@RequestMapping("/getRewardWithUserId")
	public HashMap<String,Object>getRewardWithUserId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String user_id=request.getParameter("user_id");
		List<Reward> rewards = rewardUserDao.getRewardWithUserId(user_id);
		map.put("rewards", rewards);
		return map;
	}
	
	//用于手机端创建用户与奖励之间联系
	@ResponseBody
	@RequestMapping("/createRewardUserWithMissionId")
	public HashMap<String,Object> createRewardUserWithUserId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String mission_id= request.getParameter("mission_id");
		String user_id = request.getParameter("user_id");
		Integer reward_id1 = rewardDao.getRewardWithMissionId(mission_id);
		String reward_id = String.valueOf(reward_id1);
		boolean flag = rewardUserDao.isExistRewardUser(reward_id, user_id);
		//用户奖励已经存在
		if(mission_id == null)
		{
			//创建用户与奖励关联失败
			map.put("error", 1041);
			return map;
		}
		if(flag)
		{
			//创建用户与奖励关联成功
			map.put("msg", 1040);
			return map;
		}
		if(reward_id != null)
		{
			int code = rewardUserDao.createRewardUser(reward_id, user_id);
			if(code==1040)
			{
				//
				//创建用户与奖励关联成功
				map.put("msg", 1040);
			}else{
				//创建用户与奖励关联失败
				map.put("error", 1041);
			}
		}else{
			//创建用户与奖励关联失败
			map.put("error", 1041);
		}
		return map;
	}
	

}
