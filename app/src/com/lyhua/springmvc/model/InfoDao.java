package com.lyhua.springmvc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lyhua.springmvc.other.MyDateFormat;

@Repository
public class InfoDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDao userDao;
	
	
	/**������Ϣ
	 * 
	 * @param info_content ��Ϣ����
	 * @param send_id ������id
	 * @param receive_id ������id
	 * @param send_date ��������
	 * @param receive_date ��������
	 * @return
	 */
	public Integer createInfo(String info_content,String send_id,String receive_id,String send_date,String receive_date)
	{
		String sql = "insert into info(info_content,send_id,receive_id,send_date,receive_date) values('"+info_content+"','"+send_id+"','"+receive_id+"','"+send_date+"','"+receive_date+"')";
		System.out.println("sql:"+sql);
		if(jdbcTemplate.update(sql)>0)
		{
			//������Ϣ�ɹ�
			return 1044;
		}else{
			//������Ϣʧ��
			return 1045;
		}
	}
	
	
	/**�����û��͵����ʱ�䷵����Ϣ
	 * 
	 * @param user_id �û�id
	 * @return
	 */
	public List<Info> getInfoWithUserId(String user_id)
	{
		List<Info> infos = new ArrayList<Info>();
		String dateString = MyDateFormat.mysqlDateFormat();
		String sql = "select info_id from info where receive_id='"+user_id+"' and receive_date='"+dateString+"'";
		String sql1 = "select info_content from info where receive_id='"+user_id+"' and receive_date='"+dateString+"'";
		String sql2 = "select send_id from info where receive_id='"+user_id+"' and receive_date='"+dateString+"'";
		String sql3 = "select receive_id from info where receive_id='"+user_id+"' and receive_date='"+dateString+"'";
		String sql4 = "select send_date from info where receive_id='"+user_id+"' and receive_date='"+dateString+"'";
		String sql5 = "select receive_date from info where receive_id='"+user_id+"' and receive_date='"+dateString+"'";
		
		List<Integer> info_id = jdbcTemplate.queryForList(sql, Integer.class);
		List<String> info_content =jdbcTemplate.queryForList(sql1, String.class);
		List<Integer> send_id =jdbcTemplate.queryForList(sql2, Integer.class);
		List<Integer> receive_id =jdbcTemplate.queryForList(sql3, Integer.class);
		List<String> send_date =jdbcTemplate.queryForList(sql4, String.class);
		List<String> receive_date =jdbcTemplate.queryForList(sql5, String.class);
		
		for(int i=0;i<info_id.size();i++)
		{
			User user = userDao.getUserWithId(send_id.get(i));
			//Info myInfo = new Info(info_id.get(i),info_content.get(i),send_id.get(i),receive_id.get(i),send_date.get(i),receive_date.get(i));
			Info myInfo = new Info(info_id.get(i),info_content.get(i),send_id.get(i),receive_id.get(i),send_date.get(i),receive_date.get(i),user.getHeadImage(),user.getName());
			
			infos.add(myInfo);
		}
		return infos;
	}

}
