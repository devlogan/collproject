package com.collbackend.daos;

import java.util.List;

import com.collbackend.models.BlogComment;

public interface BlogCommentDao {
	
	void addBlogComment(BlogComment blogComment);
	List<BlogComment> getAllBlogComments(int blogId);
	void deleteBlogComment(BlogComment blogComment);

}
