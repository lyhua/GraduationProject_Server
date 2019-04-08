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
	 * @param user_id �û�ID
	 * @return �Ż����ݶ���
	 */
	public List<Content> getContentWithUser_ID(Integer user_id)
	{
		//TODO �û������������������
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
		//���������û��ķ�������
		for (int i=0;i<content1.size();i++)
		{
			Content content2 = new Content(content_id.get(i), content1.get(i), contentImage.get(i), mood.get(i), user_id);
			content.add(content2);
		}
		return content;
	}
	
	/**
	 * 
	 * @param content �û��������ݵ�����
	 * @param contentimage �û����������ݵ�ͼƬ����
	 * @param mood �û��������ݵ�����
	 * @param user_id �û���ID
	 * @return 0������ӳɹ�   1017�������ʧ��
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
				//���û�������������ӵ����ݿ��гɹ�
				return 1018;
			}else{
				//���ʧ��
				return 1017;
			}
		}else{
			if(jdbcTemplate.update(sql) > 0)
			{
				//���û�������������ӵ����ݿ��гɹ�
				return 1018;
			}else{
				//���ʧ��
				return 1017;
			}
		}
		
	}
	
	
	/**
	 * 
	 * @param content_id ��Ҫɾ���û������������ݵ�ID
	 * @param request �������������
	 * @return 1019 :ɾ���û���Ҫɾ�����ݳɹ�   1020:ɾ���û���Ҫɾ������ʧ��
	 */
	public Integer deleteContentWithContentID(Integer content_id,HttpServletRequest request)
	{
		//TODO ���û��������ݵ�����ͼƬ��ɾ����
		String sql = "delete from content where content_id = "+content_id;
		String sql1 = "select contentImage from content where content_id = "+content_id;
		//��ȡ�û���Ҫɾ�������ݵ�ͼƬ
		List<String> images = jdbcTemplate.queryForList(sql1, String.class);
		//��ȡ��ǰ��������·��
		String realPath = request.getServletContext().getRealPath("/contentImage");
		//ɾ���û��������ݵ������ļ�
		for(int i=0;i<images.size();i++)
		{
			//ƴ��ȫ·��
			String contentImage = realPath+"\\"+images.get(i);
			System.out.println("contentImage :"+contentImage);
			//ɾ���û���������ͼƬ
			File file = new File(contentImage);
			if (file.exists() && file.isFile()) 
			{
				 file.delete();
			}
		}
		if(jdbcTemplate.update(sql)>0)
		{
			//ɾ���û���Ҫɾ�����ݳɹ�
			return 1019;
		}
		//ɾ���û���Ҫɾ������ʧ��
		return 1020;
	}
	
	/**
	 * 
	 * @param content_id �������ݵ�ID
	 * @param newOrold true�����ȡ��content_id�µ�����(Ĭ�ϲ�����10��) false�����ȡ��content_idС������(Ĭ�ϲ�����10��)
	 * @param num Ҫ��ȡ��С������(Ĭ��10��)
	 * @return ����Ĭ�������ķ�������
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
		//��ʱ����
		User user = null;
		Content c = null;
		
		//content_id��Ϊ��
		if (content_id != null)
		{
			//����ȡ ������
			if (newOrold)
			{
				//��ȡ��content_id�����������
				String sql2 = "select count(*) from content where content_id > " + content_id;
				Integer moreContentNum = jdbcTemplate.queryForInt(sql2);
				//�����������ڵ��ڸ�����num�򷵻�num������
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
				else //���򷵻�ʣ��ȫ������
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
			else //����ȡ������
			{
				//��ȡ��content_idС����������
				String sql2 = "select count(*) from content where content_id < " + content_id;
				Integer moreContentNum = jdbcTemplate.queryForInt(sql2);
				//�����������ڵ��ڸ�����num�򷵻�num������
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
				}else//���򷵻�ʣ��ȫ������
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
		}else //content_idΪ��Ĭ��ȡ������num������
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
