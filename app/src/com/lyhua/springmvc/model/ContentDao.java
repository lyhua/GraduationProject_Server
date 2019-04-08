package com.lyhua.springmvc.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ContentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDao userDao;
	
	
	/**
	 * 
	 * @param user_id 用户ID
	 * @return 放回内容对象
	 */
	public List<Content> getContentWithUser_ID(Integer user_id)
	{
		//TODO 用户发布的内容有许多条
		ArrayList<Content> content = new ArrayList<Content>();
		
		String sql = "select content from content where user_id ="+user_id;
		String sql1 = "select contentImage from content where user_id ="+user_id;
		String sql2 = "select mood from content where user_id ="+user_id;
		String sql3 = "select content_id from content where user_id ="+user_id;
		//TODO
		String sql4 = "select mission_id from content where user_id ="+user_id;
		
		List<String> content1 = jdbcTemplate.queryForList(sql, String.class);
		List<String> contentImage = jdbcTemplate.queryForList(sql1, String.class);
		List<Integer> mood = jdbcTemplate.queryForList(sql2, Integer.class);
		List<Integer> content_id = jdbcTemplate.queryForList(sql3, Integer.class);
		//返回所有用户的发布内容
		for (int i=0;i<content1.size();i++)
		{
			Content content2 = new Content(content_id.get(i), content1.get(i), contentImage.get(i), mood.get(i), user_id);
			content.add(content2);
		}
		return content;
	}
	
	/**
	 * 
	 * @param content 用户发布内容的文字
	 * @param contentimage 用户发布的内容的图片名字
	 * @param mood 用户发布内容的心情
	 * @param user_id 用户的ID
	 * @return 0代表添加成功   1017代表添加失败
	 */
	public Integer createContent(String content,String contentimage,Integer mood,Integer user_id,String date,String contentSmallImage,String mission_id)
	{
		String sql = "insert into content(content,contentImage,mood,user_id,date,contentSmallImage,mission_id) values('"+content+"'"+",'"+contentimage+"',"+mood+","+user_id+",'"+date+"',"+"'"+contentSmallImage+"','"+mission_id+"')";              
		String sql1 = "insert into content(content,contentImage,mood,user_id,date,contentSmallImage) values('"+content+"'"+",'"+contentimage+"',"+mood+","+user_id+",'"+date+"',"+"'"+contentSmallImage+"')";
		System.out.println("1234565");
		System.out.println("sql :"+sql);
		System.out.println("1234565");
		if(mission_id==null)
		{
			if(jdbcTemplate.update(sql1) > 0)
			{
				//把用户发布的内容添加到数据库中成功
				return 1018;
			}else{
				//添加失败
				return 1017;
			}
		}else{
			if(jdbcTemplate.update(sql) > 0)
			{
				//把用户发布的内容添加到数据库中成功
				return 1018;
			}else{
				//添加失败
				return 1017;
			}
		}
		
	}
	
	
	/**
	 * 
	 * @param content_id 搜要删除用户所发布的内容的ID
	 * @param request 服务器请求对象
	 * @return 1019 :删除用户所要删除内容成功   1020:删除用户所要删除内容失败
	 */
	public Integer deleteContentWithContentID(Integer content_id,HttpServletRequest request)
	{
		//TODO 把用户发布内容的所有图片都删除掉
		String sql = "delete from content where content_id = "+content_id;
		String sql1 = "select contentImage from content where content_id = "+content_id;
		//获取用户所要删除的内容的图片
		List<String> images = jdbcTemplate.queryForList(sql1, String.class);
		//获取当前服务器的路径
		String realPath = request.getServletContext().getRealPath("/contentImage");
		//删除用户发布内容的所有文件
		for(int i=0;i<images.size();i++)
		{
			//拼接全路径
			String contentImage = realPath+"\\"+images.get(i);
			System.out.println("contentImage :"+contentImage);
			//删除用户发布内容图片
			File file = new File(contentImage);
			if (file.exists() && file.isFile()) 
			{
				 file.delete();
			}
		}
		if(jdbcTemplate.update(sql)>0)
		{
			//删除用户所要删除内容成功
			return 1019;
		}
		//删除用户所要删除内容失败
		return 1020;
	}
	
	/**
	 * 
	 * @param content_id 发布内容的ID
	 * @param newOrold true代表获取比content_id新的内容(默认不大于10条) false代表获取比content_id小的内容(默认不大于10条)
	 * @param num 要获取多小条内容(默认10条)
	 * @return 返回默认条数的发布内容
	 */
	public List<Content> getContentsWithContentId(Integer content_id,boolean newOrold,Integer num)
	{
		String sql = "select  content_id from content order by content_id desc LIMIT 1";
		String sql1 = "select count(*) from content";
		Integer maxContentID = jdbcTemplate.queryForObject(sql, Integer.class);
		Integer count = jdbcTemplate.queryForInt(sql1);
		Integer contentId = null;
		System.out.println("content_id " +content_id);
		System.out.println("maxContentID :"+maxContentID);
		//临时变量
		User user = null;
		Content c = null;
		
		//content_id不为空
		if (content_id != null)
		{
			//代表取 新数据
			if (newOrold)
			{
				//获取比content_id大的内容数量
				String sql2 = "select count(*) from content where content_id > " + content_id;
				Integer moreContentNum = jdbcTemplate.queryForInt(sql2);
				//若该数量大于等于给定的num则返回num条数据
				if (moreContentNum >= num)
				{
					String sql3 ="select content_id from content where content_id >"+content_id+" LIMIT "+num;
					String sql4 ="select content from content where content_id >"+content_id+" LIMIT "+num;
					String sql5 ="select contentImage from content where content_id >"+content_id+" LIMIT "+num;
					String sql6 ="select mood from content where content_id >"+content_id+" LIMIT "+num;
					String sql7 ="select user_id from content where content_id >"+content_id+" LIMIT "+num;
					String sql8 = "select date from content where content_id >"+content_id+" LIMIT " + num;
					String sql9 ="select contentSmallImage from content where content_id >"+content_id+" LIMIT "+num;
					//
					String sql10 ="select mission_id from content where content_id >"+content_id+" LIMIT "+num;
					List<Integer> content_ids = jdbcTemplate.queryForList(sql3, Integer.class);
					List<String> contentss = jdbcTemplate.queryForList(sql4, String.class);
					List<String> contentImages = jdbcTemplate.queryForList(sql5, String.class);
					List<Integer> moods = jdbcTemplate.queryForList(sql6, Integer.class);
					List<Integer> user_ids = jdbcTemplate.queryForList(sql7, Integer.class);
					List<String> dates = jdbcTemplate.queryForList(sql8, String.class);
					List<Content> newContents = new ArrayList<Content>();
					List<String> contentSmallImages = jdbcTemplate.queryForList(sql9, String.class);
					List<Integer> mission_ids = jdbcTemplate.queryForList(sql10, Integer.class);
					for(int i=0;i<content_ids.size();i++)
					{
//						Content c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i));
						user = userDao.getUserWithId(user_ids.get(i));
//						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i),user.getHeadImage());
					    //c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i));
						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i),mission_ids.get(i));
						System.out.println(c);
						newContents.add(c);
					}
					return newContents;
				}
				else //否则返回剩余全部数据
				{
					String sql3 ="select content_id from content where content_id >"+content_id;
					String sql4 ="select content from content where content_id >"+content_id;
					String sql5 ="select contentImage from content where content_id >"+content_id;
					String sql6 ="select mood from content where content_id >"+content_id;
					String sql7 ="select user_id from content where content_id >"+content_id;
					String sql8 ="select date from content where content_id >"+content_id;
					String sql9 ="select contentSmallImage from content where content_id >"+content_id;
					//
					String sql10 ="select mission_id from content where content_id >"+content_id;
					List<Integer> content_ids = jdbcTemplate.queryForList(sql3, Integer.class);
					List<String> contentss = jdbcTemplate.queryForList(sql4, String.class);
					List<String> contentImages = jdbcTemplate.queryForList(sql5, String.class);
					List<Integer> moods = jdbcTemplate.queryForList(sql6, Integer.class);
					List<Integer> user_ids = jdbcTemplate.queryForList(sql7, Integer.class);
					List<String> dates = jdbcTemplate.queryForList(sql8, String.class);
					List<Content> newContents = new ArrayList<Content>();
					List<String> contentSmallImages = jdbcTemplate.queryForList(sql9, String.class);
					List<Integer> mission_ids = jdbcTemplate.queryForList(sql10, Integer.class);
					for(int i=0;i<content_ids.size();i++)
					{
						user = userDao.getUserWithId(user_ids.get(i));
//						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i),user.getHeadImage());
						//c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i));
						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i),mission_ids.get(i));
						System.out.println(c);
						newContents.add(c);
					}
					return newContents;
				}
			}
			else //代表取旧数据
			{
				//获取比content_id小的内容数量
				String sql2 = "select count(*) from content where content_id < " + content_id;
				Integer moreContentNum = jdbcTemplate.queryForInt(sql2);
				//若该数量大于等于给定的num则返回num条数据
				if (moreContentNum >= num)
				{
					Integer previousId = content_id - num;
					String sql3 ="select content_id from content where content_id <"+content_id+" AND content_id >="+previousId;                    
					String sql4 ="select content from content where content_id <"+content_id+" AND content_id >="+previousId;
					String sql5 ="select contentImage from content where content_id <"+content_id+" AND content_id >="+previousId;
					String sql6 ="select mood from content where content_id <"+content_id+" AND content_id >="+previousId;
					String sql7 ="select user_id from content where content_id <"+content_id+" AND content_id >="+previousId;
					String sql8 ="select date from content where content_id <"+content_id+" AND content_id >="+previousId;
					String sql9 ="select contentSmallImage from content where content_id <"+content_id+" AND content_id >="+previousId;
					//
					String sql10 ="select mission_id from content where content_id <"+content_id+" AND content_id >="+previousId;
					List<Integer> content_ids = jdbcTemplate.queryForList(sql3, Integer.class);
					List<String> contentss = jdbcTemplate.queryForList(sql4, String.class);
					List<String> contentImages = jdbcTemplate.queryForList(sql5, String.class);
					List<Integer> moods = jdbcTemplate.queryForList(sql6, Integer.class);
					List<Integer> user_ids = jdbcTemplate.queryForList(sql7, Integer.class);
					List<String> dates = jdbcTemplate.queryForList(sql8, String.class);
					List<Content> newContents = new ArrayList<Content>();
					List<String> contentSmallImages = jdbcTemplate.queryForList(sql9, String.class);
					List<Integer> mission_ids = jdbcTemplate.queryForList(sql10, Integer.class);
					for(int i=0;i<content_ids.size();i++)
					{
						user = userDao.getUserWithId(user_ids.get(i));
//						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i),user.getHeadImage());
						//c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i));
						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i),mission_ids.get(i));
						System.out.println(c);
						newContents.add(c);
					}
					return newContents;
				}else//否则返回剩余全部数据
				{
					String sql3 ="select content_id from content where content_id <"+content_id;                    
					String sql4 ="select content from content where content_id <"+content_id;
					String sql5 ="select contentImage from content where content_id <"+content_id;
					String sql6 ="select mood from content where content_id <"+content_id;
					String sql7 ="select user_id from content where content_id <"+content_id;
				    String sql8 ="select date from content where content_id <"+content_id; 
				    String sql9 ="select contentSmallImage from content where content_id <"+content_id;
				    //
				    String sql10 ="select mission_id from content where content_id <"+content_id;
					List<Integer> content_ids = jdbcTemplate.queryForList(sql3, Integer.class);
					List<String> contentss = jdbcTemplate.queryForList(sql4, String.class);
					List<String> contentImages = jdbcTemplate.queryForList(sql5, String.class);
					List<Integer> moods = jdbcTemplate.queryForList(sql6, Integer.class);
					List<Integer> user_ids = jdbcTemplate.queryForList(sql7, Integer.class);
					List<String> dates = jdbcTemplate.queryForList(sql8, String.class);
					List<Content> newContents = new ArrayList<Content>();
					List<String> contentSmallImages = jdbcTemplate.queryForList(sql9, String.class);
					List<Integer> mission_ids = jdbcTemplate.queryForList(sql10, Integer.class);
					for(int i=0;i<content_ids.size();i++)
					{
						user = userDao.getUserWithId(user_ids.get(i));
//						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i),user.getHeadImage());
						//c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i));
						c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i),mission_ids.get(i));
						System.out.println(c);
						newContents.add(c);
					}
					return newContents;
				}
			}
		}else //content_id为空默认取最新新num条数据
		{
			Integer previousId = maxContentID - num;
			String sql3 ="select content_id from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			String sql4 ="select content from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			String sql5 ="select contentImage from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			String sql6 ="select mood from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			String sql7 ="select user_id from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			String sql8 = "select date from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			String sql9 ="select contentSmallImage from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			//
			String sql10 ="select mission_id from content where content_id <="+maxContentID+" AND content_id >"+previousId;
			List<Integer> content_ids = jdbcTemplate.queryForList(sql3, Integer.class);
			List<String> contentss = jdbcTemplate.queryForList(sql4, String.class);
			List<String> contentImages = jdbcTemplate.queryForList(sql5, String.class);
			List<Integer> moods = jdbcTemplate.queryForList(sql6, Integer.class);
			List<Integer> user_ids = jdbcTemplate.queryForList(sql7, Integer.class);
			List<String> dates = jdbcTemplate.queryForList(sql8, String.class); 
			List<Content> newContents = new ArrayList<Content>();
			List<String> contentSmallImages = jdbcTemplate.queryForList(sql9, String.class);
			List<Integer> mission_ids = jdbcTemplate.queryForList(sql10, Integer.class);
			for(int i=0;i<content_ids.size();i++)
			{
				user = userDao.getUserWithId(user_ids.get(i));
//				c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i),user.getHeadImage());
			    //c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i));
				c = new Content(content_ids.get(i), contentss.get(i), contentImages.get(i), moods.get(i), user_ids.get(i), user.getName(), dates.get(i), user.getHeadImage(), contentSmallImages.get(i),mission_ids.get(i));
				System.out.println(c);
				newContents.add(c);
			}
			return newContents;
		}
	}

}
