<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lyhua.springmvc.other.MD5"  %> 
<%@page import="com.lyhua.springmvc.other.MyDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h4>APP Interface</h4>
  <br/>
  <h4>获取用户名</h4>
  <form action="IsUser">
    用户名<input type="text" name="name">
    <br/>
    密码<input type="password" name="password"/>
    <br/>
    <input type="submit" value="提交" >
  </form>
  <a href="IsUser?name=Tom1&password=123456">IsUser</a>
  <br/>
  <!-- 测试用id去对应的用户 -->
  <a href="getUserWithId?userId=1">getUserWithId</a>
  <br/>
  <!-- 测试注册 -->
  <%
  String password = MD5.string2MD5("1234567");
  %>
  <a href="Register?phone=13543828082&password=<%=password %>">测试注册有MD5</a>
  <br/>
  <!-- 测试修改密码 -->
  <a href="AlterUserImformation?password=123456&userId=5&code=2001">测试修改密码</a>
  <br/>
  <!-- 测试修改用户名 -->
  <a href="AlterUserImformation?name=Ant&userId=5&code=2003">测试修改用户名</a>
  <br/>
  <!-- 测试修改性别 -->
  <a href="AlterUserImformation?gender=1&userId=5&code=2002">测试修改性别</a>
  <br/>
  <!-- 测试用户登录 -->
  <a href="Login?name=Ant&password=123456">测试用户登录</a>
  <br/>
  <a href="Login?phone=13543828098&password=<%=password %>">测试用户登录1</a>
  <br/>
  <a href="Login?name=Ant&password=1234567">测试用户登录2</a>
  <br/>
  <!-- 测试短信验证 -->
  <a href="sendCode?phone=13543828098">测试短信验证</a>
  <br/>
  <!-- 测试图片的上传 -->
  <form action="springUpload" enctype="multipart/form-data" method="post">
    <input type="file" name="photos"/>
    <input type="submit" name="submit" value="上传图片">
  </form>
  <br/>
  <!-- 测试test -->
  <form action="test"  method="post">
    <input type="text" name="test" value="">
    <input type="submit" name="submit" value="提交">
  </form>
  
  
  <!-- 测试用户上传头像 -->
  <form action="alterUserHeadImage" method="post" enctype="multipart/form-data">
     <input type="text" name="userId" value="1" />
     <input type="file" name="photos"/>
     <input type="submit" name="submit" value="上传图片">
  </form>
  
  <br/>
  <!-- 测试用户注册没有MD5 -->
  测试用户注册没有MD5
  <br/>
  <form action="Register">
    <input type="text" name="phone" value="13543828098"/>
    <input type="text" name="password" value="123456"/>
    <input type="text" name="verificationCode" value=""/>
    <input type="submit" name="submit" value="注册">
  </form>
  <br/>
  测试用户修改密码(没有MD5加密)
  <br/>
  <form action="alterUserPasswordWithPhone">
    <input type="text" name="phone" value="13543828098"/>
    <input type="text" name="password" value="654321"/>
    <input type="text" name="verificationCode" value=""/>
    <input type="submit" name="submit" value="修改密码">
  </form>
  <br/>
  <br/>
  
  <br/>
  测试用户登录
  <form action="Login">
   <input type="text" name="phone" value="13543828098">
   <input type="password" name="password" value="654321">
   <input type="submit" name="submit" value="提交">
  </form>
  <br/>
   <br/>
  <a href="Login?phone=13543828098&password=654321">测试用户登录</a>
  <br/>
  <br/>
  <a href="test">test</a>
  <br/>
  <a href="content/getContentWithUserID?user_id=1">测试返回用户发布的内容</a>
  <br/>
  测试用户上传内容
  <form action="content/createContent" method="post" enctype="multipart/form-data" >
    <input type="text" name="content" value="我就是个大傻逼"/>
    <br/>
    <input type="text" name="mood" value="0"/>
    <br/>
    <input type="text" name="user_id" value="1"/>
    <br/>
    <input type="text" name="date" value="<%=MyDateFormat.dateToMyFormat() %>"/>
    <br/>
    <input type="file" name="contentImage"/>
    <input type="file" name="contentImage1"/>
    <input type="submit" name="submit" value="发布"/>
  </form>
  <br/>
  测试删除用户发布的内容
  <form action="content/deleteContentWithContentID">
    <input type="text" name="content_id" value="7">
    <input type="submit" name="submit" value="删除用户发布内容">
  </form>
  <br/>
  测试获取内容数据
  <form action="content/getContentsWithContentId">
    内容ID<input type="text" name="content_id" value="">
    新OR旧<input type="text" name="newOrOld" value="">
    <input type="submit" name="submit" value="获取用户数据">
  </form>
  <br/>
  测试申请好友
  <br/>
  <form action="friend/applyForFriend">
     <input type="text" name="user_id" />
     <input type="text" name="myFriend_id" />
     <input type="submit" name="submit" value="申请好友">
  </form>
  <br/>
  测试申请好友响应
  <br/>
  <form action="friend/responseApplyForFriend">
     <input type="text" name="user_id" />
     <input type="text" name="myFriend_id" />
     <input type="submit" name="submit" value="回复申请好友">
  </form>
  <br/>
  测试获取全部好友列表
  <br/>
  <form action="friend/getUserAllFirendsWithUserId">
    <input type="text" name="user_id" />
     <input type="submit" name="submit" value="获取全部好友列表">
  </form>
  <br/>
  测试获取部分好友列表
  <form action="friend/getMyFriendsList">
    <input type="text" name="user_id" />
    <input type="text" name="myExsitFriendList" />
    <input type=text name="myExsitFriendList">
    <input type="submit" name="submit" value="获取不存在好友列表">
  </form>
  <br/>
  测试获取评论数据
  <form action="comment/getCommentsWithContentId">
           内容ID<input type="text" name="content_id" value="">
           评论ID<input type="text" name="comment_id" value="">
           新OR旧<input type="text" name="newOrOld" value="">
    <input type="submit" name="submit" value="获取评论数据">
  </form>
  <br/>
  创建评论
  <form action="comment/createComment">
    评论<input type="text" name="comment" value=""/>
    用户id<input type="text" name="user_id" value=""/>
    内容id<input type="text" name="content_id" value=""/>
    日期<input type="text" name="date" value=""/>
  <input type="submit" name="submit" value="创建评论"/>
  </form>
 
  <br/>
  <br/>
  删除评论
  <form action="comment/deleteCommentWithCommentID">
    评论id<input type="text" name="comment_id" value=""/>
  <input type="submit" name="submit" value="删除评论"/>
  </form>
  
  <br/>
  <br/>
  
  保存联系人
  <form action="telephone/saveContacts">
  联系人电话号码<input type="text" name="phoneNumbers" value=""/>
  <br/>
  联系人名字<input type="text" name="contactNames" value=""/>
  <br>
   联系人电话号码<input type="text" name="phoneNumbers" value=""/>
  <br/>
  联系人名字<input type="text" name="contactNames" value=""/>
  <br>
   联系人电话号码<input type="text" name="phoneNumbers" value=""/>
  <br/>
  联系人名字<input type="text" name="contactNames" value=""/>
  <br>
  用户id<input type="text" name="user_id" value="1"/>
  <input type="submit" name="submit" value="保存联系人"/>
  </form>
  
  <br/>
  <br/>
  
  <br>
  
  <form action="mission/createMission">
   任务类容<input type="text" name="mission_content" value=""/>
  <br>
   开始日期<input type="text" name="mission_starttime" value="2018-03-30"/>
  <br>
   结束日期<input type="text" name="mission_endtime" value="2018-03-31"/>
  <br>
  <input type="submit" name="submit" value="创建任务"/>
  </form>
  
  <br>
  
  <br>
  
  <form action="mission/deleteMissionWithId">
   任务id<input type="text" name="mission_id" value=""/>
  <br>
  <input type="submit" name="submit" value="删除"/>
  </form>
  
  <br>
  
  <br>
  
  <form action="mission/getMission">
  <br>
  获取任务列表
  <input type="submit" name="submit" value="获取任务列表"/>
  </form>
  
  <br>
  
  
   <br>
  
  <form action="mission/updateMissionFlag">
   更新flag<input type="text" name="flag" value=""/>
  <br>
   任务id<input type="text" name="mission_id" value=""/>
  <br>
  <input type="submit" name="submit" value="更新亲子任务是否有效"/>
  </form>
  
  <br>
  
  <br>
  
  <form action="mission/createMissionUser">
   用户id<input type="text" name="user_id" value=""/>
  <br>
   任务id<input type="text" name="mission_id" value=""/>
  <br>
  <input type="submit" name="submit" value="创建用户与任务的联系"/>
  </form>
  
  <br>
  
  <br>
  
  <form action="mission/updateMissionUserFlag">
   用户id<input type="text" name="user_id" value=""/>
  <br>
   任务id<input type="text" name="mission_id" value=""/>
  <br>
  <input type="submit" name="submit" value="设置任务是否完成"/>
  </form>
  
  <br>
  
  <form action="reward/createReward" enctype="multipart/form-data" method="post">
   奖励名字<input type="text" name="reward_name" value=""/>
  <br>
   任务id<input type="text" name="mission_id" value=""/>
  <br>
  图片<input type="file" name="rewardImage"/>
  
  <input type="submit" name="submit" value="创建奖励"/>
  </form>
  
  <br>
  
  <br>
  
  <form action="reward/createRewardUser"  method="post">
   奖励id<input type="text" name="reward_id" value=""/>
  <br>
   用户id<input type="text" name="user_id" value=""/>
  <br>
  <input type="submit" name="submit" value="创建用户与奖励之间的联系"/>
  </form>
  
  <br>
  
  <br>
  
  <form action="reward/getRewardWithUserId"  method="post">

   用户id<input type="text" name="user_id" value=""/>
  <br>
  <input type="submit" name="submit" value="根据用户id返回用户 的奖励"/>
  </form>
  
  <br>
  
  <form action="info/createInfo">
  发送者id<input type="text" name="send_id" value=""/>
  <br/>
  接收者id<input type="text" name="receive_id" value=""/>
  <br/>
  发送日期<input type="text" name="send_date" value=""/>
  <br/>
  接受日期<input type="text" name="receive_date" value=""/>
  <br/>
 内容<input type="text" name="info_content" value=""/>
 <br/>
  <input type="submit" name="submit" value="发送悄悄话"/>
  </form>
  
  <br/>
  
  <form action="info/getInfoWithUserId">
  用户id<input type="text" name="user_id" value=""/>
  <br/>
  <input type="submit" name="submit" value="获取悄悄话信息"/>
  
  </form>
  
  <br/>
  <form action="friend/getApplyForFriend">
   用户id<input type="text" name="userId" value="11"/>
  <br/>
  <input type="submit" name="submit" value="获取申请好友列表"/>
  
  </form>
  <br/>
  
  <br/>
  申请好友
  <br/>
  <form action="friend/applyForFriend">
   用户id<input type="text" name="user_id" value=""/>
  <br/>
  好友用户id<input type="text" name="myFriend_id" value=""/>
  <br/>
  <input type="submit" name="submit" value="申请好友"/>
  
  </form>
  <br/>
  
  <br/>
  响应申请好友
  <br/>
  <form action="friend/responseApplyForFriend">
   用户id<input type="text" name="user_id" value=""/>
  <br/>
  好友用户id<input type="text" name="myFriend_id" value=""/>
  <br/>
  <input type="submit" name="submit" value="响应申请好友"/>
  
  </form>
  <br/>
  
  
  
  
  
</body>
</html>