package com.lyhua.springmvc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RewardUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 
	 * @param reward_id 奖励id
	 * @param user_id 用户id
	 * @return
	 */
	public Integer createRewardUser(String reward_id,String user_id)
	{
		String sql="insert into reward_user(reward_id,user_id) values('"+reward_id+"','"+user_id+"');";
		System.out.println("sql" +sql);
		//创建用户与奖励关联
		if(jdbcTemplate.update(sql)>0)
		{
			//创建用户与奖励关联成功
			return 1040;
		}else{
			//创建用户与奖励关联失败
			return 1041;
		}
	}
	
	//根据用户id返回用户 的奖励
	public List<Reward> getRewardWithUserId(String user_id)
	{
		List<Reward> rewards = new ArrayList<Reward>();
		String sql="select b.reward_id from reward_user a,reward b where a.user_id='"+user_id+"' and a.reward_id=b.reward_id";
		String sql1="select b.reward_name from reward_user a,reward b where a.user_id='"+user_id+"' and a.reward_id=b.reward_id";
		String sql2="select b.reward_pic from reward_user a,reward b where a.user_id='"+user_id+"' and a.reward_id=b.reward_id";
		String sql3="select b.mission_id from reward_user a,reward b where a.user_id='"+user_id+"' and a.reward_id=b.reward_id";
		
		List<Integer> reward_id=jdbcTemplate.queryForList(sql, Integer.class);
		List<String> reward_name=jdbcTemplate.queryForList(sql1, String.class);
		List<String> reward_pic=jdbcTemplate.queryForList(sql2, String.class);
		List<Integer> mission_id=jdbcTemplate.queryForList(sql3, Integer.class);
		
		for(int i=0;i<reward_id.size();i++)
		{
			Reward myReward = new Reward(reward_id.get(i), reward_name.get(i), reward_pic.get(i), mission_id.get(i));
			rewards.add(myReward);
		}
		//返回相应用户获得的奖励
		return rewards;
	}
	
	//判断用户与奖励是否存在联系
	public boolean isExistRewardUser(String reward_id,String user_id)
	{
		String sql ="select count(*) from reward_user where reward_id='"+reward_id+"' and user_id='"+user_id+"'";
		int num =0;
		num = jdbcTemplate.queryForInt(sql);
		if(num == 1)
		{
			return true;
		}else{
			return false;
		}
	}
	
}
