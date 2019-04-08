package com.lyhua.springmvc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.lyhua.springmvc.model.Comment;

@Repository
public class CommentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ContentDao contentDao;
	
	
	/**
	 * 
	 * @param content_id 内容id
	 * @return
	 */
	public List<Comment> getCommentWithContentId(Integer content_id)
	{
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "select comment_id from comment where content_id ="+content_id;
		String sql1 = "select user_id from comment where content_id ="+content_id;
		String sql2 = "select comment from comment where content_id ="+content_id;
		String sql3 = "select comment_good from comment where content_id ="+content_id;
		String sql4 = "select comment_bad from comment where content_id ="+content_id;
		
		List<Integer> comment_id = jdbcTemplate.queryForList(sql, Integer.class);
		List<Integer> user_id = jdbcTemplate.queryForList(sql1, Integer.class);
		List<String> comment = jdbcTemplate.queryForList(sql2, String.class);
		List<Integer> comment_good = jdbcTemplate.queryForList(sql3, Integer.class);
		List<Integer> comment_bad = jdbcTemplate.queryForList(sql4, Integer.class);
		
		for(int i = 0; i < comment_id.size(); i++)
		{
			Comment myComment = new Comment(comment_id.get(i), user_id.get(i), content_id, comment.get(i), comment_good.get(i), comment_bad.get(i));
			comments.add(myComment);
		}
		
		return comments;
	}
	
	/**
	 * 
	 * @param user_id 用户id
	 * @param content_id 内容id
	 * @param comment 评论
	 * @return
	 */
	public Integer createComment(String user_id,String content_id,String comment,String date)
	{
		//如果其中这几项为空直接返回失败
		if(user_id == null || content_id == null || comment == null)
		{
			return 1024;
		}
		if(user_id.equals("") || content_id.equals("") || comment.equals(""))
		{
			return 1024;
		}
		//查询内容id
		String sql1 = "select user_id from content where content_id = " +content_id;
		Integer ownerId = null;
		try {
			ownerId = jdbcTemplate.queryForObject(sql1, Integer.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(ownerId == null)
		{
			return 1024;
		}
		//创建评论
		String sql = "insert into comment(user_id,content_id,comment,date,ownerId) values("+user_id+","+content_id+","+"'"+comment+"','"+date+"',"+String.valueOf(ownerId)+")";
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql) > 0)
		{
			//发送评论成功
			return 1023;
		}else{
			//发送评论失败
			return 1024;
		}
	}
	
	/**
	 * 
	 * @param comment_id 评论id
	 * @return 返回删除结果代码
	 */
	public Integer deleteCommetWithId(String comment_id)
	{
		String sql = "delete from comment where comment_id = " +comment_id;
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql) > 0)
		{
			//删除评论成功
			return 1025;
		}else{
			//删除评论失败
			return 1026;
		}
	}
	
	
	public List<Comment> getCommentsWithContentId(Integer comment_id,boolean newOrold,String content_id,Integer num)
	{
		List<Comment> comments = new ArrayList<Comment>();
		System.out.println("content_id"+content_id);
		//内容id为空直接返回
		if(content_id == null)
		{
			return comments;
		}
		if(content_id.equals(""))
		{
			return comments;
		}
		String sql = "select  comment_id from comment "+"where content_id ="+content_id+" order by comment_id desc LIMIT 1";
		String sql1 = "select count(*) from comment";
		Integer maxCommentID = null;
		System.out.println("comment sql "+sql);
		try {
			maxCommentID = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			// TODO: handle exception
			return comments;
		}
		
		Integer count = jdbcTemplate.queryForInt(sql1);
		
		Integer contentId = null;
		System.out.println("comment_id " +comment_id);
		System.out.println("maxCommentID :"+maxCommentID);
		
		//临时变量
		Comment c= null;
		
		//comment_id不为空
		if(comment_id != null)
		{
			//代表取 新数据
			if (newOrold)
			{
				//获取比content_id大的内容数量
				String sql2 = "select count(*) from comment where comment_id > " + comment_id;
				Integer moreContentNum = jdbcTemplate.queryForInt(sql2);
				//若该数量大于等于给定的num则返回num条数据
				if (moreContentNum >= num)
				{
					String sql3 ="select comment_id from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num; 
					String sql4 ="select user_id from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
					String sql5 ="select content_id from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
					String sql6 ="select comment from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
					String sql7 ="select comment_good from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
				    String sql8 ="select comment_bad from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
				    String sql9 ="select date from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
				    String sql10 ="select ownerId from comment where content_id="+content_id+" and comment_id >"+comment_id+" LIMIT "+num;
				    List<Integer> comment_ids = jdbcTemplate.queryForList(sql3, Integer.class);
				    List<Integer> user_ids = jdbcTemplate.queryForList(sql4, Integer.class);
				    List<Integer> content_ids = jdbcTemplate.queryForList(sql5, Integer.class);
				    List<String> commentss = jdbcTemplate.queryForList(sql6, String.class);
				    List<Integer> comment_goods = jdbcTemplate.queryForList(sql7, Integer.class);
				    List<Integer> comment_bads = jdbcTemplate.queryForList(sql8, Integer.class);
				    List<String> dates = jdbcTemplate.queryForList(sql9, String.class);
				    List<Integer> ownerIds = jdbcTemplate.queryForList(sql10, Integer.class);
				    for(int i =0; i<comment_ids.size();i++)
				    {
//				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i));
				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i),dates.get(i),ownerIds.get(i));
				    	comments.add(c);
				    	
				    }
				}else {//否则少于规定返回条数就返回剩余全部数据
					String sql3 ="select comment_id from comment where content_id="+content_id+" and comment_id >"+comment_id; 
					String sql4 ="select user_id from comment where content_id="+content_id+" and comment_id >"+comment_id; 
					String sql5 ="select content_id from comment where content_id="+content_id+" and comment_id >"+comment_id; 
					String sql6 ="select comment from comment where content_id="+content_id+" and comment_id >"+comment_id; 
					String sql7 ="select comment_good from comment where content_id="+content_id+" and comment_id >"+comment_id; 
				    String sql8 ="select comment_bad from comment where content_id="+content_id+" and comment_id >"+comment_id; 
				    String sql9 ="select date from comment where content_id="+content_id+" and comment_id >"+comment_id; 
				    String sql10 ="select ownerId from comment where content_id="+content_id+" and comment_id >"+comment_id; 
				    List<Integer> comment_ids = jdbcTemplate.queryForList(sql3, Integer.class);
				    List<Integer> user_ids = jdbcTemplate.queryForList(sql4, Integer.class);
				    List<Integer> content_ids = jdbcTemplate.queryForList(sql5, Integer.class);
				    List<String> commentss = jdbcTemplate.queryForList(sql6, String.class);
				    List<Integer> comment_goods = jdbcTemplate.queryForList(sql7, Integer.class);
				    List<Integer> comment_bads = jdbcTemplate.queryForList(sql8, Integer.class);
				    List<String> dates = jdbcTemplate.queryForList(sql9, String.class);
				    List<Integer> ownerIds = jdbcTemplate.queryForList(sql10, Integer.class);
				    for(int i =0; i<comment_ids.size();i++)
				    {
//				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i));
				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i),dates.get(i),ownerIds.get(i));
				    	comments.add(c);
				    }
				}
			}else{ //代表取旧数据
				//获取比content_id小的内容数量
				String sql2 = "select count(*) from comment where content_id="+content_id+" and comment_id < " + comment_id;
				Integer moreContentNum = null;
				try {
					moreContentNum = jdbcTemplate.queryForInt(sql2);
				} catch (Exception e) {
					// TODO: handle exception
					return comments;
				}
				//若该数量大于等于给定的num则返回num条数据
				if (moreContentNum >= num)
				{
					Integer previousId = comment_id - num;
					String sql3 ="select comment_id from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql4 ="select user_id from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql5 ="select content_id from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql6 ="select comment from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql7 ="select comment_good from comment where content_id="+content_id+" nd comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql8 ="select comment_bad from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql9 ="select date from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					String sql10 ="select ownerId from comment where content_id="+content_id+" and comment_id <"+comment_id+" AND comment_id >="+previousId;
					List<Integer> comment_ids = jdbcTemplate.queryForList(sql3, Integer.class);
				    List<Integer> user_ids = jdbcTemplate.queryForList(sql4, Integer.class);
				    List<Integer> content_ids = jdbcTemplate.queryForList(sql5, Integer.class);
				    List<String> commentss = jdbcTemplate.queryForList(sql6, String.class);
				    List<Integer> comment_goods = jdbcTemplate.queryForList(sql7, Integer.class);
				    List<Integer> comment_bads = jdbcTemplate.queryForList(sql8, Integer.class);
				    List<String> dates = jdbcTemplate.queryForList(sql9, String.class);
				    List<Integer> ownerIds = jdbcTemplate.queryForList(sql10, Integer.class);
				    for(int i =0; i<comment_ids.size();i++)
				    {
//				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i));
				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i),dates.get(i),ownerIds.get(i));
				    	comments.add(c);
				    }
				}else{//否则返回剩余全部数据
					String sql3 ="select comment_id from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql4 ="select user_id from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql5 ="select content_id from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql6 ="select comment from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql7 ="select comment_good from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql8 ="select comment_bad from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql9 ="select date from comment where content_id="+content_id+" and comment_id <"+comment_id;
					String sql10 ="select ownerId from comment where content_id="+content_id+" and comment_id <"+comment_id;
					List<Integer> comment_ids = jdbcTemplate.queryForList(sql3, Integer.class);
				    List<Integer> user_ids = jdbcTemplate.queryForList(sql4, Integer.class);
				    List<Integer> content_ids = jdbcTemplate.queryForList(sql5, Integer.class);
				    List<String> commentss = jdbcTemplate.queryForList(sql6, String.class);
				    List<Integer> comment_goods = jdbcTemplate.queryForList(sql7, Integer.class);
				    List<Integer> comment_bads = jdbcTemplate.queryForList(sql8, Integer.class);
				    List<String> dates = jdbcTemplate.queryForList(sql9, String.class);
				    List<Integer> ownerIds = jdbcTemplate.queryForList(sql10, Integer.class);
				    for(int i =0; i<comment_ids.size();i++)
				    {
//				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i));
				    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i),dates.get(i),ownerIds.get(i));
				    	comments.add(c);
				    }
				}
			}
		}else{ //comment_id为空默认取最新新num条数据
			Integer previousId = maxCommentID - num;
			String sql3 ="select comment_id from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql4 ="select user_id from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql5 ="select content_id from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql6 ="select comment from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql7 ="select comment_good from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql8 ="select comment_bad from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql9 ="select date from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			String sql10 ="select ownerId from comment where content_id="+content_id+" and comment_id <="+maxCommentID+" AND comment_id >"+previousId;
			List<Integer> comment_ids = jdbcTemplate.queryForList(sql3, Integer.class);
		    List<Integer> user_ids = jdbcTemplate.queryForList(sql4, Integer.class);
		    List<Integer> content_ids = jdbcTemplate.queryForList(sql5, Integer.class);
		    List<String> commentss = jdbcTemplate.queryForList(sql6, String.class);
		    List<Integer> comment_goods = jdbcTemplate.queryForList(sql7, Integer.class);
		    List<Integer> comment_bads = jdbcTemplate.queryForList(sql8, Integer.class);
		    List<String> dates = jdbcTemplate.queryForList(sql9, String.class);
		    List<Integer> ownerIds = jdbcTemplate.queryForList(sql10, Integer.class);
		    for(int i =0; i<comment_ids.size();i++)
		    {
//		    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i));
		    	c = new Comment(comment_ids.get(i), user_ids.get(i), content_ids.get(i), commentss.get(i), comment_goods.get(i), comment_bads.get(i),dates.get(i),ownerIds.get(i));
		    	comments.add(c);
		    }
		}
		
		return comments;
		
	}
	
}





