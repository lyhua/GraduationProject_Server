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
	
	/** �����������û���ϵ��
	 * 
	 * @param user_id �û�id
	 * @param mission_id ����id
	 * @return
	 */
	public Integer createMissionUser(String user_id,String mission_id)
	{
		//TODO Ҫ�ж��û��Ƿ���ں������Ƿ����
		String sql="insert into mission_user(user_id,mission_id) values('"+user_id+"','"+mission_id+"')";
		System.out.println("sql" +sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//���������û������ݳɹ�
			return 1034;
		}else{
			//���������û�������ʧ��
			return 1035;
		}
	}
	
	
	/**���������Ƿ����
	 * 
	 * @param flag �����Ƿ����
	 * @param mission_user_id ���id
	 * @return
	 */
	public Integer updateMissionUserFlag(String flag,String mission_user_id)
	{
		String sql="update mission_user set flag='"+flag+"' where mission_user_id='"+mission_user_id+"'";
		System.out.println("sql" +sql);
		//TODO �������������
		if(jdbcTemplate.update(sql)>0)
		{
			//���������û���flag(�Ƿ����)�ɹ�
			return 1036;
		}else{
			//���������û���flag(�Ƿ����)ʧ��
			return 1037;
		}
	}
	

}
