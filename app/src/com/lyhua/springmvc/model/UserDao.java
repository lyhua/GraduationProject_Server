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
	
	
	//更新用户信息
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
			//修改失败
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
	
	
	//模拟从数据库中取出数据
	
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
	//从数据库中取出对应id的用户 (jdbcTemplate 查询int有毒不建议用)
	public User getUserWithId(Integer userId)
	{
		//查询sql语句
		String sql1 = "select name from user where userId="+userId;
		String sql2 = "select age from user where userId="+userId;
		String sql3 = "select gender from user where userId="+userId;
		String sql4 = "select phone from user where userId="+userId;
		String sql5 = "select headImage from user where userId="+userId;
		String sql6 = "select email from user where userId="+userId;
		
		//取出相应的数据
		String name = jdbcTemplate.queryForObject(sql1, String.class);
		Integer age = jdbcTemplate.queryForObject(sql2, Integer.class);
		Integer gender = jdbcTemplate.queryForObject(sql3, Integer.class);
		String phone = jdbcTemplate.queryForObject(sql4, String.class);
		String headImage = jdbcTemplate.queryForObject(sql5, String.class);
		String email = jdbcTemplate.queryForObject(sql6, String.class);
		//生成User对象
		User user = new User(userId, name, null, age, gender, phone, headImage, email);
		
		return user;
	}
	
	

	
	
	//用户进行注册
	public int Register(String phone,String verificationCode,String password)
	{
		//判断验证码是否一致
		String verificationCode1 = verificationCodeDao.getCodeWithPhone(phone);
		if(!verificationCode.equals(verificationCode1))
		{
			//验证码不一致返回错误代码
			return 1014;
		}
		
		//产生随机数字当作初始用户名
		RandomNum random = new RandomNum();
		String name = random.CreateRandomNum(9);
		
		Map<String,Object> map = new HashMap<String,Object>();
		String sql1 = "select count(0) from user where phone = '"+phone+"'";
		String sql2 = "insert into user(name,phone,password) values("+name+","+phone+",'"+password+"')";
		System.out.println(sql2);
		//判断手机号码是否为合法手机号码
		if(!IsPhone.isMobileNO(phone))
		{
			return 1007;
		}
		
		//判断是否是已经用过的电话号码
		int isUsedPhone = 0;
		isUsedPhone = jdbcTemplate.queryForInt(sql1);
		if(isUsedPhone > 0)
		{
			//返回手机号码已经注册信息错误代码
			return 1001;
		}
		if(jdbcTemplate.update(sql2) < 0)
		{
			//注册失败
			return 1016;
		}
		
		//注册成功返回
		return 0;
		
	}
	
	
	/*
	 * code == 2001 代表修改密码
	 * code == 2002代表修改性别
	 * code == 2003代表修改用户名
	 */
	//修改用户信息
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
		//修改密码
		if(code == 2001)
		{
			if(jdbcTemplate.update(sql) < 0)
			{
				//修改密码失败
				return 1003;
			}
		}
		//修改性别
		if(code == 2002)
		{
			if(jdbcTemplate.update(sql1) < 0)
			{
				//修改性别失败
				return 1004;
			}
		}
		//修改用户名
		if(code == 2003)
		{
			//查询是否有相同的用户名
			if(jdbcTemplate.queryForInt(sql3) > 0)
			{
				return 1005;
			}
			if(jdbcTemplate.update(sql2) < 0)
			{
				//修改用户名失败
				return 1005;
			}
		}
		//表示修改成功
		return 0;
	}
	
	/**
	 * 
	 * @param userHeadImage 用户头像的名字
	 * @param id 用户的id
	 * @return 返回操作码 1011 代表修改失败，0代表修改成功过
	 */
	public int alterUserHeadImage(String userHeadImage,Integer userId)
	{
		String sql = "update user set headImage ='"+userHeadImage+"'"+" where userId="+String.valueOf(userId);
		if(userHeadImage != null && userId != null )
		{
			if(jdbcTemplate.update(sql) < 0)
			{
				//修改用户头像失败
				return 1011;
			}
		}else{
			return 1011;
		}
		//修改成功
		return 0;
	}
	
	
	/**
	 * 
	 * @param phone 用户的电话号码
	 * @param password 用户的新密码
	 * @param code 用户申请的验证码
	 * @return 1013：验证码为空的错误代码   1014:验证码不一致错误代码  1015:修改用户密码失败
	 */
	public int alterUserPasswordWithPhone(String phone,String password,String verificationCode)
	{
		String sql ="update user set password='"+password+"'"+"where phone='"+phone+"'";
		String verificationCode1 = verificationCodeDao.getCodeWithPhone(phone);
		//验证码为空
		if(verificationCode1 == null)
		{
			//验证码为空的错误代码
			return 1013;
		}else{
			//如果两个验证码不一样返回验证码不一致错误代码
			if(!verificationCode.equals(verificationCode1))
			{
				return 1014;
			}else{
				//验证通过可以进行修改密码
				if(jdbcTemplate.update(sql) < 0)
				{
					//修改用户密码失败
					return 1015;
				}
			}
		}
		//把验证码移除
		verificationCodeDao.RemoveCode(phone);
		//修改密码成功
		return 0;
	}
	
	
	
	
	
	//用户登录
	public int Login(String name,String password,String phone)
	{
		String sql = "select count(*) from user where name ='"+name+"' and password='"+password+"'";
		String sql1 = "select count(*) from user where phone='"+phone+"' and password='"+password+"'";
//		System.out.println("+++sql   "+sql);
		if(name != null && phone == null)
		{
			if(jdbcTemplate.queryForInt(sql) > 0)
			{
				//登录成功
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
		
		//登录失败
		return 1006;
	}
	
	/**
	 * 
	 * @param phone 用户手机号码
	 * @return 返回用户在数据库中id
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
	 * @param phone 用户的手机号码
	 * @return 返回一个用户对象
	 */
	public User getUserWithPhone(String phone)
	{ 
		String sql4 = "select name from user where phone ='" + phone + "'";
		String sql5 = "select userId from user where phone ='" + phone +"'";
		String sql6 = "select age from user where phone ='" + phone +"'";
		String sql7 = "select gender from user where phone ='" + phone +"'";
		String sql8 = "select email from user where phone ='" + phone +"'";
		String sql9 = "select headImage from user where phone ='" + phone +"'";
		//从数据库中取出对应id相应的数据并且生成用户返回
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
		//TODO 适应各种图片
		String userHeadimage = realPath +"//" + id +".jpg";
		System.out.println(userHeadimage);
		Integer[] size = Image.imageSize(userHeadimage);
		return size;
	}
	
	
	
	
	
	
	
	
	public void save(User user)
	{
		//TODO检验用户是否唯一
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
