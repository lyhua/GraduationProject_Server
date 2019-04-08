package com.lyhua.springmvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RewardDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**创建奖励
	 * 
	 * @param reward_name 奖励名称
	 * @param reward_pic 奖励徽章图片
	 * @param mission_id 任务id
	 * @return
	 */
	public Integer createReward(String reward_name,String reward_pic,String mission_id)
	{
		String sql="insert into reward(reward_name,reward_pic,mission_id) values('"+reward_name+"','"+reward_pic+"','"+mission_id+"')";
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//创建相应的任务奖励表成功
			return 1038;
		}else{
			//创建相应的任务奖励表失败
			return 1039;
		}
	}
	
	/**
	 * 
	 * @param mission_id 任务id
	 * @return 奖励id
	 */
	public Integer getRewardWithMissionId(String mission_id)
	{
		String sql = "select reward_id from reward where mission_id='"+mission_id+"'";
		Integer reward_id = null;
		System.out.println("sql:"+sql);
		reward_id = jdbcTemplate.queryForObject(sql, Integer.class);
		if(reward_id !=null)
		{
			System.out.println("reward_id:"+String.valueOf(reward_id));
		}else{
			System.out.println("reward_id:null");
		}
		return reward_id;
	}
	

}
