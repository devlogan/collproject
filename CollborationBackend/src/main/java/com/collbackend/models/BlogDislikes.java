package com.collbackend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlogDislikes {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int dislikeId;

	private int blogId;

	private String userEmail;

	private String userName;



	public int getDislikeId() {
		return dislikeId;
	}

	public void setDislikeId(int dislikeId) {
		this.dislikeId = dislikeId;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



}
