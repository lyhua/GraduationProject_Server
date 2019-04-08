package com.lyhua.springmvc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MissionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @param mission_content 任务发布的类容
	 * @param mission_starttime 任务发布的时间
	 * @param mission_endtime 任务结束的时间
	 * @return
	 */
	public Integer createMission(String mission_content,String mission_starttime,String mission_endtime)
	{
		String sql ="insert into mission(mission_content,mission_starttime,mission_endtime) values("+"'"+mission_content+"','"+mission_starttime+"','"+mission_endtime+"');";
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//创建亲子任务成功
			return 1029;
		}else{
			//创建亲子任务失败
			return 1030;
		}
	}
	
	
	/**
	 * 
	 * @param mission_id 被删除任务的id
	 * @return
	 */
	public Integer deleteMissionWithId(String mission_id)
	{
		String sql="delete from mission where mission_id='"+mission_id+"'";
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//删除亲子任务成功
			return 1031;
		}else{
			//删除亲子任务失败
			return 1032;
		}
	}
	
	/**
	 * 
	 * @return 返回用户可以做的任务
	 */
	public List<Mission> getMission()
	{
		List<Mission> missions = new ArrayList<Mission>();
		String sql="select mission_id from mission where flag='0'";
		String sql1="select flag from mission where flag='0'";
		String sql2="select mission_content from mission where flag='0'";
		String sql3="select mission_starttime from mission where flag='0'";
		String sql4="select mission_endtime from mission where flag='0'";
		
		List<Integer> mission_id = jdbcTemplate.queryForList(sql, Integer.class);
		List<Integer> flag = jdbcTemplate.queryForList(sql1, Integer.class);
		List<String> mission_content = jdbcTemplate.queryForList(sql2, String.class);
		List<String> mission_starttime = jdbcTemplate.queryForList(sql3, String.class);
		List<String> mission_endtime = jdbcTemplate.queryForList(sql4, String.class);
		
		for(int i=0;i<mission_id.size();i++)
		{
			Mission myMission = new	Mission(mission_id.get(i),flag.get(i),mission_content.get(i),mission_starttime.get(i),mission_endtime.get(i));
			missions.add(myMission);
		}
		return missions;
	}
	
	
	/**更新亲子任务是否有效
	 * 
	 * @param flag 是否有效
	 * @param mission_id 任务id
	 * @return  
	 */
	public Integer updateMissionFlag(String flag,String mission_id)
	{
		String sql="update mission set flag='"+flag+"' where mission_id='"+mission_id+"'";
		System.out.println("sql:"+sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//更新亲子任务flag(是否有效)成功
			return 1042;
		}else{
			//更新亲子任务flag(是否有效)失败
			return 1043;
		}
	}

}
