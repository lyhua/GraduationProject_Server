package com.lyhua.springmvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MissionUserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDao userDao;
	
	/** 创建任务与用户联系集
	 * 
	 * @param user_id 用户id
	 * @param mission_id 任务id
	 * @return
	 */
	public Integer createMissionUser(String user_id,String mission_id)
	{
		//TODO 要判断用户是否存在和任务是否存在
		String sql="insert into mission_user(user_id,mission_id) values('"+user_id+"','"+mission_id+"')";
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//插入任务用户表数据成功
			return 1034;
		}else{
			//插入任务用户表数据失败
			return 1035;
		}
	}
	
	
	/**设置任务是否完成
	 * 
	 * @param flag 任务是否完成
	 * @param mission_user_id 表的id
	 * @return
	 */
	public Integer updateMissionUserFlag(String flag,String mission_user_id)
	{
		String sql="update mission_user set flag='"+flag+"' where mission_user_id='"+mission_user_id+"'";
		System.out.println("sql" +sql);
		//TODO 这里可能有问题
		if(jdbcTemplate.update(sql)>0)
		{
			//更新任务用户表flag(是否完成)成功
			return 1036;
		}else{
			//更新任务用户表flag(是否完成)失败
			return 1037;
		}
	}
	

}
