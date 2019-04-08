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
	
	
	//����������Ҫ�ϴ�ͼƬ��
	@ResponseBody
	@RequestMapping("/createReward")
	public HashMap<String,Object>createReward(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String reward_name=request.getParameter("reward_name");
		String mission_id=request.getParameter("mission_id");
		
		//��ȡ����ͼƬ
		//��ȡ��ǰ��������·��
		String realPath = request.getServletContext().getRealPath("/missionImage");
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
            
            //���Ը���mission����������ͼƬ
            String imageName = mission_id+MyDateFormat.dateToMyFormat1();
             
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
                	
                	//ƴ���ļ���ȫ����  ����+missionid
                	String path=realPath+"\\"+imageName+"."+suffix;
                    System.out.println(file.getOriginalFilename());
                    String userHeadImage = imageName + "." + suffix;
                    //�ϴ�
                    System.out.println("path:"+path);
                    file.transferTo(new File(path));
                    //���ͼƬ��size(width height)��λΪ����
                    Integer[] size = Image.imageSize(path);
                    System.out.println("Photo width and Height is "+size[0]+"  "+size[1]);
                    
                    // ��ͼƬ�����ִ浽missionģ�͵����ݿ���
                    int code = rewardDao.createReward(reward_name, userHeadImage, mission_id);
                    if (code == 1039)
                    {
                    	//���������ɹ�
                    	map.put("error", 1039);
                    }
                    else{
                    	//��������ʧ��
                    	map.put("msg", 1038);
                    }
                }
            }
        }
		return map;
	}
	
	//�����û��뽱��֮�����ϵ
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
			//�����û��뽱�������ɹ�
			map.put("msg", 1040);
		}else{
			//�����û��뽱������ʧ��
			map.put("error", 1041);
		}
		return map;
	}
	
	
	//�����û�id�����û� �Ľ���
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
	
	//�����ֻ��˴����û��뽱��֮����ϵ
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
		//�û������Ѿ�����
		if(mission_id == null)
		{
			//�����û��뽱������ʧ��
			map.put("error", 1041);
			return map;
		}
		if(flag)
		{
			//�����û��뽱�������ɹ�
			map.put("msg", 1040);
			return map;
		}
		if(reward_id != null)
		{
			int code = rewardUserDao.createRewardUser(reward_id, user_id);
			if(code==1040)
			{
				//
				//�����û��뽱�������ɹ�
				map.put("msg", 1040);
			}else{
				//�����û��뽱������ʧ��
				map.put("error", 1041);
			}
		}else{
			//�����û��뽱������ʧ��
			map.put("error", 1041);
		}
		return map;
	}
	

}
