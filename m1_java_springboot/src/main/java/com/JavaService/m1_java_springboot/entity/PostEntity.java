package com.JavaService.m1_java_springboot.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "tb_post",schema="public")
public class PostEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="data")
	private String data;
	
	@Column(name="post_id")
	private String post_id;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="post_title")
	private String post_title;
	
	@Column(name="post_body")
	private String post_body;
	
	@Column(name="post_created_at")
	private long post_created_at;
	
	@Column(name="post_updated_at")
	private long post_updated_at;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_body() {
		return post_body;
	}

	public void setPost_body(String post_body) {
		this.post_body = post_body;
	}

	public long getPost_created_at() {
		return post_created_at;
	}

	public void setPost_created_at(long post_created_at) {
		this.post_created_at = post_created_at;
	}

	public long getPost_updated_at() {
		return post_updated_at;
	}

	public void setPost_updated_at(long post_updated_at) {
		this.post_updated_at = post_updated_at;
	}
	
	
}
