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
	 * @param myExsitFriendList �û��Ѿ������û��б�
	 * @param user_id �û���ID
	 * @return �����û������ں����б�
	 */
	public ArrayList<User> getMyFriendsList(Integer[] myExsitFriendList,Integer user_id)
	{
		//TODO �������bug�������б��б��������������ʱ������
		String sql = "select myFriend_id from friend where user_id = "+ user_id;
		List<Integer> myfriends = jdbcTemplate.queryForList(sql, Integer.class);
		//�Ƴ��ͻ����Ѿ����ڵĺ����б�
		if(myExsitFriendList != null)
		{
			for(int i= 0;i<myExsitFriendList.length;i++)
			{
				myfriends.remove(myExsitFriendList[i]);
			}
			
		}
		//�ѿͻ���û�еĺ��ѷ��ظ��ͻ���
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0;i<myfriends.size();i++)
		{
			users.add(userDao.getUserWithId(myfriends.get(i)));
		}
		return users;
	}
	
	
	/**
	 * 
	 * @param user_id �û�ID
	 * @return �����û����еĺ����б�
	 */
	public ArrayList<User> getUserAllFirendsWithUserId(Integer user_id)
	{
		String sql = "select myFriend_id from friend where user_id = "+ user_id;
		List<Integer> myfriends = jdbcTemplate.queryForList(sql, Integer.class);
		//�ѿͻ���û�еĺ��ѷ��ظ��ͻ���
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0;i<myfriends.size();i++)
		{
			users.add(userDao.getUserWithId(myfriends.get(i)));
		}
		return users;
	}
	
	
	/**
	 * 
	 * @param user_id ������ID
	 * @param myFriend_id ��������ID
	 */
	public Integer appplyForFriend(Integer user_id,Integer myFriend_id)
	{
		//����������������ѵ��û�Id�ͱ�������ѵ�id��¼����
		//Friend friend = new Friend(null, user_id, myFriend_id);
		//String key = String.valueOf(user_id)+":"+String.valueOf(myFriend_id);
		//friendMap.put(key, friend);
	    //TODO ��������ѷ������� ���Ҫ�첽���׽���
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
	 * @param user_id ��������ID
	 * @param myFriend_id ������ID
	 * @return 1021:����ɹ�  1022:����ʧ��
	 */
	public Integer responseApplyForFriend(Integer user_id,Integer myFriend_id)
	{
		String key = String.valueOf(myFriend_id)+":"+String.valueOf(user_id);
		String sql = "insert into friend(user_id,myFriend_id) values("+user_id+","+myFriend_id+")";
		String sql1 = "insert into friend(user_id,myFriend_id) values("+myFriend_id+","+user_id+")";
		String sql2 = "delete from apply where userId2='"+String.valueOf(user_id)+"' and userId1='"+String.valueOf(myFriend_id)+"'";
		if(jdbcTemplate.update(sql) > 0 && jdbcTemplate.update(sql1) > 0)
		{
			//ɾ������
			jdbcTemplate.update(sql2);
			return 1021;
		}else{
			//�������ʧ��
			return 1022;
		}
		
		//��friendMap������Ӧ�����б�
		/*
		if(friendMap.get(key) != null)
		{
			//������ѳɹ�
			if(jdbcTemplate.update(sql) > 0 && jdbcTemplate.update(sql1) > 0)
			{
				//�Ƴ�friendMap������Ӧ�����б�
				friendMap.remove(key);
				return 1021;
			}
		}
		//�������ʧ��
		return 1022;
		*/
	}
	
	
	//��ȡ��������б�
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
