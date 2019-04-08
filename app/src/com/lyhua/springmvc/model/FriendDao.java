package com.lyhua.springmvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class FriendDao {
	
	private static Map<String,Friend> friendMap = new HashMap<String,Friend>();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * @param myExsitFriendList 用户已经存在用户列表
	 * @param user_id 用户的ID
	 * @return 返回用户不存在好友列表
	 */
	public ArrayList<User> getMyFriendsList(Integer[] myExsitFriendList,Integer user_id)
	{
		//TODO 这里存在bug当好友列表列表这个参数不存在时有问题
		String sql = "select myFriend_id from friend where user_id = "+ user_id;
		List<Integer> myfriends = jdbcTemplate.queryForList(sql, Integer.class);
		//移除客户端已经存在的好友列表
		if(myExsitFriendList != null)
		{
			for(int i= 0;i<myExsitFriendList.length;i++)
			{
				myfriends.remove(myExsitFriendList[i]);
			}
			
		}
		//把客户端没有的好友返回给客户端
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0;i<myfriends.size();i++)
		{
			users.add(userDao.getUserWithId(myfriends.get(i)));
		}
		return users;
	}
	
	
	/**
	 * 
	 * @param user_id 用户ID
	 * @return 返回用户所有的好友列表
	 */
	public ArrayList<User> getUserAllFirendsWithUserId(Integer user_id)
	{
		String sql = "select myFriend_id from friend where user_id = "+ user_id;
		List<Integer> myfriends = jdbcTemplate.queryForList(sql, Integer.class);
		//把客户端没有的好友返回给客户端
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0;i<myfriends.size();i++)
		{
			users.add(userDao.getUserWithId(myfriends.get(i)));
		}
		return users;
	}
	
	
	/**
	 * 
	 * @param user_id 申请人ID
	 * @param myFriend_id 被申请人ID
	 */
	public Integer appplyForFriend(Integer user_id,Integer myFriend_id)
	{
		//服务器把用申请好友的用户Id和被申请好友的id记录下来
		//Friend friend = new Friend(null, user_id, myFriend_id);
		//String key = String.valueOf(user_id)+":"+String.valueOf(myFriend_id);
		//friendMap.put(key, friend);
	    //TODO 向被申请好友发送请求 这个要异步用套接字
		String sql ="insert into apply(userId1,userId2) values('"+String.valueOf(user_id)+"','"+String.valueOf(myFriend_id)+"')";
		if(jdbcTemplate.update(sql)>0)
		{
			return 1047;
		}else{
			return 1048;
		}
		
	}
	
	
	/**
	 * 
	 * @param user_id 被申请人ID
	 * @param myFriend_id 申请人ID
	 * @return 1021:申请成功  1022:申请失败
	 */
	public Integer responseApplyForFriend(Integer user_id,Integer myFriend_id)
	{
		String key = String.valueOf(myFriend_id)+":"+String.valueOf(user_id);
		String sql = "insert into friend(user_id,myFriend_id) values("+user_id+","+myFriend_id+")";
		String sql1 = "insert into friend(user_id,myFriend_id) values("+myFriend_id+","+user_id+")";
		String sql2 = "delete from apply where userId2='"+String.valueOf(user_id)+"' and userId1='"+String.valueOf(myFriend_id)+"'";
		if(jdbcTemplate.update(sql) > 0 && jdbcTemplate.update(sql1) > 0)
		{
			//删除申请
			jdbcTemplate.update(sql2);
			return 1021;
		}else{
			//申请好友失败
			return 1022;
		}
		
		//从friendMap查找相应好友列表
		/*
		if(friendMap.get(key) != null)
		{
			//申请好友成功
			if(jdbcTemplate.update(sql) > 0 && jdbcTemplate.update(sql1) > 0)
			{
				//移除friendMap查找相应好友列表
				friendMap.remove(key);
				return 1021;
			}
		}
		//申请好友失败
		return 1022;
		*/
	}
	
	
	//获取申请好友列表
	public ArrayList<User>getAppplyForFriend(String userId)
	{
		String sql ="select userId1 from apply where userId2='"+userId+"'";
		System.out.println("sql:"+sql);
		List<Integer> myfriends = jdbcTemplate.queryForList(sql, Integer.class);
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0;i<myfriends.size();i++)
		{
			users.add(userDao.getUserWithId(myfriends.get(i)));
		}
		return users;
	}
	
}
