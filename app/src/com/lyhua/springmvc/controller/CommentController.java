package com.lyhua.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyhua.springmvc.model.Comment;
import com.lyhua.springmvc.model.CommentDao;
import com.lyhua.springmvc.model.User;
import com.lyhua.springmvc.model.UserDao;


@RequestMapping("/comment")
@Controller
public class CommentController {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private UserDao userDao;
	
	//根据内容id获取用户评论(单条评论)
	@ResponseBody
	@RequestMapping("/getCommentWithContentId")
	public HashMap<String,Object>getCommentWithContentId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String content_id = request.getParameter("content_id");
		Integer contentId = Integer.valueOf(content_id);
		List<Comment> commtents = commentDao.getCommentWithContentId(contentId);
		//TODO 这里有问题
		return map;
	}
	
	//用户发布评论
	@ResponseBody
	@RequestMapping("/createComment")
	public HashMap<String,Object> createComment(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String user_id = request.getParameter("user_id");
		String content_id = request.getParameter("content_id");
		String comment = request.getParameter("comment");
		String date = request.getParameter("date");
		String Comment =null;
		System.out.println("comment" + comment);
		try {
			Comment = new String(comment.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("a"+Comment);
		int code = commentDao.createComment(user_id, content_id, Comment,date);
		if(code == 1023)
		{
			//发送评论成功
			map.put("msg", 1023);
		}else {
			//发送评论失败
			map.put("error", 1024);
		}
		
		return map;
	}
	
	
	//删除评论
	@ResponseBody
	@RequestMapping("/deleteCommentWithCommentID")
	public HashMap<String,Object> deleteCommentWithCommentID(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String comment_id = request.getParameter("comment_id");
		int code = commentDao.deleteCommetWithId(comment_id);
		if(code == 1025)
		{
			map.put("msg", 1025);
		}else{
			map.put("error", 1026);
		}
		return map;
	}
	
	//获取新评论或获取就评论
	@ResponseBody
	@RequestMapping("/getCommentsWithContentId")
	public HashMap<String,Object> getCommentsWithContentId(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String comment_id = request.getParameter("comment_id");
		String content_id = request.getParameter("content_id");
		Integer commentId;
		boolean newOrOld;
		if(comment_id != null)
		{
			commentId = (comment_id.equals("")) ? null : Integer.valueOf(comment_id);
		}else{
			commentId = null;
		}
		String NewOROld = request.getParameter("newOrOld");
		if(NewOROld != null)
		{
			newOrOld = (NewOROld.equals("1")) ? true : false;
		}else{
			newOrOld = true;
		}
		System.out.println("newOrOld :"+newOrOld);
		List<Comment> comments = commentDao.getCommentsWithContentId(commentId, newOrOld,content_id,10);
		map.put("comments", comments);
		List<User> users = new ArrayList<User>();
		User user = null;
		Integer userId;
		for(int i =0; i < comments.size();i++)
		{
			userId = comments.get(i).getUser_id();
			user = userDao.getUserWithId(userId);
			users.add(user);
		}
		map.put("users", users);
		return map;
	}
	
	
}
