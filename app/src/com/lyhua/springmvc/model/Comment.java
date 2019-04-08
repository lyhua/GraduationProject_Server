package com.lyhua.springmvc.model;

import java.util.Date;

public class Comment {
	private Integer comment_id;//评论id
	private Integer user_id;//评论人用户id
	private Integer content_id;//内容id
	private String comment;//评论内容
	private Integer comment_good;//评论点赞数
	private Integer comment_bad;//评论踩
	private String date;//评论日期
	private Integer ownerId;//评论所属内容的用户Id
	public Integer getComment_id() {
		return comment_id;
	}
	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getContent_id() {
		return content_id;
	}
	public void setContent_id(Integer content_id) {
		this.content_id = content_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getComment_good() {
		return comment_good;
	}
	public void setComment_good(Integer comment_good) {
		this.comment_good = comment_good;
	}
	public Integer getComment_bad() {
		return comment_bad;
	}
	public void setComment_bad(Integer comment_bad) {
		this.comment_bad = comment_bad;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	
	
	
	public Comment(Integer comment_id, Integer user_id, Integer content_id,
			String comment, Integer comment_good, Integer comment_bad) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.content_id = content_id;
		this.comment = comment;
		this.comment_good = comment_good;
		this.comment_bad = comment_bad;
	}
	
	
	//带参数构造方法
	public Comment(Integer comment_id, Integer user_id, Integer content_id,
			String comment, Integer comment_good, Integer comment_bad,
			String date, Integer ownerId) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.content_id = content_id;
		this.comment = comment;
		this.comment_good = comment_good;
		this.comment_bad = comment_bad;
		this.date = date;
		this.ownerId = ownerId;
	}
	//无参数的构造方法
	public Comment() {
		super();
	}
	
	
	
	
	
	
	
}
