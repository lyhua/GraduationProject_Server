package com.lyhua.springmvc.model;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.lyhua.springmvc.other.Image;
import com.lyhua.springmvc.other.IsPhone;
import com.lyhua.springmvc.other.RandomNum;


@Repository
public class UserDao {
	private static Map<Integer,User> users = null;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private VerificationCodeDao verificationCodeDao;
	
	
	//�����û���Ϣ
	public Integer  updateUserWithUserId(String userId,String name,String age,String gender,String email)
	{
		String sql="update  user set name='"+name+"' where userId='"+userId+"'";
		String sql1="update  user set age='"+age+"' where userId='"+userId+"'";
		String sql2="update  user set gender='"+gender+"' where userId='"+userId+"'";
		String sql3="update  user set email='"+email+"' where userId='"+userId+"'";
		if(userId!=null)
		{
			if(name!=null)
			{
				if(jdbcTemplate.update(sql)<=0)
				{
					return 1050;
				}
				System.out.println("sql:"+sql);
			}
			if(age!=null)
			{
				if(jdbcTemplate.update(sql1)<=0)
				{
					return 1050;
				}
				System.out.println("sql:"+sql1);
			}
			if(gender!=null)
			{
				if(jdbcTemplate.update(sql2)<=0)
				{
					return 1050;
				}
				System.out.println("sql:"+sql2);
			}
			if(email!=null)
			{
				if(jdbcTemplate.update(sql3)<=0)
				{
					return 1050;
				}
				System.out.println("sql:"+sql3);
			}
		}else{
			//�޸�ʧ��
			return 1050;
		}
		return 1049;
	}
	
	
	public boolean IsLegalUser(String name,String password)
	{
		String sql = "SELECT count(*) FROM user WHERE name = '"+name+"' AND password = '"+password+"'";
//		String sql = "select name from user where name = ? and password = ?";
		
//		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
//		User user = jdbcTemplate.queryForObject(sql, rowMapper, name,password);
		System.out.println(sql);
//		String  a = null;
		Integer a = null;
//		a = jdbcTemplate.queryForInt(sql);
//		try {
//			a = jdbcTemplate.queryForObject(sql, String.class);
			a = jdbcTemplate.queryForInt(sql);
//		} catch (Exception e) {
//			
//		}
		
		if (a > 0)
		{
			System.out.println("a "+ a);
			return true;
		}
		
		return false;
	}
	
	
	//ģ������ݿ���ȡ������
	
	/*
	static{
		users = new HashMap<Integer,User>();
		
		users.put(1, new User(1, "Tom", "123456", 10, 1,"13543828090"));
		users.put(2, new User(2, "Mike", "123456", 11, 1,"13543828091"));
		users.put(3, new User(3, "Jerry", "123456", 12, 1,"13543828092"));
		users.put(4, new User(4, "Ben", "123456", 13, 0,"13543828093"));
		users.put(5, new User(5, "Bob", "123456", 14, 0,"13543828094"));
	}
	*/
	//�����ݿ���ȡ����Ӧid���û� (jdbcTemplate ��ѯint�ж���������)
	public User getUserWithId(Integer userId)
	{
		//��ѯsql���
		String sql1 = "select name from user where userId="+userId;
		String sql2 = "select age from user where userId="+userId;
		String sql3 = "select gender from user where userId="+userId;
		String sql4 = "select phone from user where userId="+userId;
		String sql5 = "select headImage from user where userId="+userId;
		String sql6 = "select email from user where userId="+userId;
		
		//ȡ����Ӧ������
		String name = jdbcTemplate.queryForObject(sql1, String.class);
		Integer age = jdbcTemplate.queryForObject(sql2, Integer.class);
		Integer gender = jdbcTemplate.queryForObject(sql3, Integer.class);
		String phone = jdbcTemplate.queryForObject(sql4, String.class);
		String headImage = jdbcTemplate.queryForObject(sql5, String.class);
		String email = jdbcTemplate.queryForObject(sql6, String.class);
		//����User����
		User user = new User(userId, name, null, age, gender, phone, headImage, email);
		
		return user;
	}
	
	

	
	
	//�û�����ע��
	public int Register(String phone,String verificationCode,String password)
	{
		//�ж���֤���Ƿ�һ��
		String verificationCode1 = verificationCodeDao.getCodeWithPhone(phone);
		if(!verificationCode.equals(verificationCode1))
		{
			//��֤�벻һ�·��ش������
			return 1014;
		}
		
		//����������ֵ�����ʼ�û���
		RandomNum random = new RandomNum();
		String name = random.CreateRandomNum(9);
		
		Map<String,Object> map = new HashMap<String,Object>();
		String sql1 = "select count(0) from user where phone = '"+phone+"'";
		String sql2 = "insert into user(name,phone,password) values("+name+","+phone+",'"+password+"')";
		System.out.println(sql2);
		//�ж��ֻ������Ƿ�Ϊ�Ϸ��ֻ�����
		if(!IsPhone.isMobileNO(phone))
		{
			return 1007;
		}
		
		//�ж��Ƿ����Ѿ��ù��ĵ绰����
		int isUsedPhone = 0;
		isUsedPhone = jdbcTemplate.queryForInt(sql1);
		if(isUsedPhone > 0)
		{
			//�����ֻ������Ѿ�ע����Ϣ�������
			return 1001;
		}
		if(jdbcTemplate.update(sql2) < 0)
		{
			//ע��ʧ��
			return 1016;
		}
		
		//ע��ɹ�����
		return 0;
		
	}
	
	
	/*
	 * code == 2001 �����޸�����
	 * code == 2002�����޸��Ա�
	 * code == 2003�����޸��û���
	 */
	//�޸��û���Ϣ
	public int AlterUserImformation(Map<String,Object> map)
	{
		String userId = (String)map.get("userId");
		String password = (String)map.get("password");
		String gender = (String)map.get("gender");
		String name = (String)map.get("name");
		int code = Integer.valueOf((String)map.get("code"));
		
		String sql = "update user set password = '"+password+"'"+" where userId = " + userId;
		String sql1 = "update user set gender = "+gender+" where userId = " + userId;
		String sql2 = "update user set name ='"+name+"'"+" where userId = " + userId;
		String sql3 = "select count(*) from user where name ='"+name+"'";
		//TODO
		String sql4 = "update ";
		
		
//		System.out.println("+++sql   "+sql2);
		//�޸�����
		if(code == 2001)
		{
			if(jdbcTemplate.update(sql) < 0)
			{
				//�޸�����ʧ��
				return 1003;
			}
		}
		//�޸��Ա�
		if(code == 2002)
		{
			if(jdbcTemplate.update(sql1) < 0)
			{
				//�޸��Ա�ʧ��
				return 1004;
			}
		}
		//�޸��û���
		if(code == 2003)
		{
			//��ѯ�Ƿ�����ͬ���û���
			if(jdbcTemplate.queryForInt(sql3) > 0)
			{
				return 1005;
			}
			if(jdbcTemplate.update(sql2) < 0)
			{
				//�޸��û���ʧ��
				return 1005;
			}
		}
		//��ʾ�޸ĳɹ�
		return 0;
	}
	
	/**
	 * 
	 * @param userHeadImage �û�ͷ�������
	 * @param id �û���id
	 * @return ���ز����� 1011 �����޸�ʧ�ܣ�0�����޸ĳɹ���
	 */
	public int alterUserHeadImage(String userHeadImage,Integer userId)
	{
		String sql = "update user set headImage ='"+userHeadImage+"'"+" where userId="+String.valueOf(userId);
		if(userHeadImage != null && userId != null )
		{
			if(jdbcTemplate.update(sql) < 0)
			{
				//�޸��û�ͷ��ʧ��
				return 1011;
			}
		}else{
			return 1011;
		}
		//�޸ĳɹ�
		return 0;
	}
	
	
	/**
	 * 
	 * @param phone �û��ĵ绰����
	 * @param password �û���������
	 * @param code �û��������֤��
	 * @return 1013����֤��Ϊ�յĴ������   1014:��֤�벻һ�´������  1015:�޸��û�����ʧ��
	 */
	public int alterUserPasswordWithPhone(String phone,String password,String verificationCode)
	{
		String sql ="update user set password='"+password+"'"+"where phone='"+phone+"'";
		String verificationCode1 = verificationCodeDao.getCodeWithPhone(phone);
		//��֤��Ϊ��
		if(verificationCode1 == null)
		{
			//��֤��Ϊ�յĴ������
			return 1013;
		}else{
			//���������֤�벻һ��������֤�벻һ�´������
			if(!verificationCode.equals(verificationCode1))
			{
				return 1014;
			}else{
				//��֤ͨ�����Խ����޸�����
				if(jdbcTemplate.update(sql) < 0)
				{
					//�޸��û�����ʧ��
					return 1015;
				}
			}
		}
		//����֤���Ƴ�
		verificationCodeDao.RemoveCode(phone);
		//�޸�����ɹ�
		return 0;
	}
	
	
	
	
	
	//�û���¼
	public int Login(String name,String password,String phone)
	{
		String sql = "select count(*) from user where name ='"+name+"' and password='"+password+"'";
		String sql1 = "select count(*) from user where phone='"+phone+"' and password='"+password+"'";
//		System.out.println("+++sql   "+sql);
		if(name != null && phone == null)
		{
			if(jdbcTemplate.queryForInt(sql) > 0)
			{
				//��¼�ɹ�
				return 0;
			}
		}
		if(name == null && phone != null)
		{
			if(jdbcTemplate.queryForInt(sql1) > 0)
			{
				return 0;
			}
		}
		
		//��¼ʧ��
		return 1006;
	}
	
	/**
	 * 
	 * @param phone �û��ֻ�����
	 * @return �����û������ݿ���id
	 */
	public Integer getUserIdWithPhone(String phone)
	{
		String sql = "select userId from user where phone ='"+phone+"'";
		Integer user_id = jdbcTemplate.queryForInt(sql);
		if(user_id != null)
		{
			return user_id;
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @param phone �û����ֻ�����
	 * @return ����һ���û�����
	 */
	public User getUserWithPhone(String phone)
	{ 
		String sql4 = "select name from user where phone ='" + phone + "'";
		String sql5 = "select userId from user where phone ='" + phone +"'";
		String sql6 = "select age from user where phone ='" + phone +"'";
		String sql7 = "select gender from user where phone ='" + phone +"'";
		String sql8 = "select email from user where phone ='" + phone +"'";
		String sql9 = "select headImage from user where phone ='" + phone +"'";
		//�����ݿ���ȡ����Ӧid��Ӧ�����ݲ��������û�����
//		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		String name = jdbcTemplate.queryForObject(sql4, String.class);
		Integer userId = jdbcTemplate.queryForObject(sql5, Integer.class);
		Integer age = jdbcTemplate.queryForObject(sql6, Integer.class);
		Integer gender = jdbcTemplate.queryForObject(sql7, Integer.class);
		String email = jdbcTemplate.queryForObject(sql8, String.class);
		String headImage = jdbcTemplate.queryForObject(sql9, String.class);
		System.out.println("name :" + name);
		System.out.println("id :" + userId);
		System.out.println("age :" + age);
		System.out.println("gender :" + gender);
		System.out.println("email :" + email);
		System.out.println("headImage :" + headImage);
		if(userId==null)
		{
			return null;
		}
		
		User user = new User(userId, name, null, age, gender, phone, headImage, email);
		return user;
	}
	
	public Integer[] getUserHeadimageSizeWithId(Integer id,HttpServletRequest request)
	{
		String realPath = request.getServletContext().getRealPath("/headImage");
		//TODO ��Ӧ����ͼƬ
		String userHeadimage = realPath +"//" + id +".jpg";
		System.out.println(userHeadimage);
		Integer[] size = Image.imageSize(userHeadimage);
		return size;
	}
	
	
	
	
	
	
	
	
	public void save(User user)
	{
		//TODO�����û��Ƿ�Ψһ
		if (user.getUserId() != null)
		{
			users.put(user.getUserId(), user);
		}
	}
	
	public Collection<User>getAlluser()
	{
		return users.values();
	}
	
	public User getUser(Integer id)
	{
		return users.get(id);
	}
	
	public void deleteUser(Integer id)
	{
		users.remove(id);
	}
	
	
	
}
