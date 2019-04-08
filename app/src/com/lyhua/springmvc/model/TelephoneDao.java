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
		//TODO 这里要进行原子性操作，即如果不能保存全部联系人就要把之前保存的联系人取消
		String sql;
		for (int i = 0; i < phoneNumbers.length; i++) {
			sql = "insert into telephone(phonenumber,contactname,user_id) values('"+phoneNumbers[i]+"','"+contactNames[i]+"',"+user_id+")";
			if(jdbcTemplate.update(sql) < 0)
			{
				//保存联系人失败，并且要回滚事务
				return 1028;
			}
		}
		//保存联系人成功
		return 1027;
	}
	
}
