package com.lyhua.springmvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TelephoneDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Integer saveContacts(String[] phoneNumbers,String[] contactNames,Integer user_id)
	{
		//TODO ����Ҫ����ԭ���Բ�������������ܱ���ȫ����ϵ�˾�Ҫ��֮ǰ�������ϵ��ȡ��
		String sql;
		for (int i = 0; i < phoneNumbers.length; i++) {
			sql = "insert into telephone(phonenumber,contactname,user_id) values('"+phoneNumbers[i]+"','"+contactNames[i]+"',"+user_id+")";
			if(jdbcTemplate.update(sql) < 0)
			{
				//������ϵ��ʧ�ܣ�����Ҫ�ع�����
				return 1028;
			}
		}
		//������ϵ�˳ɹ�
		return 1027;
	}
	
}
