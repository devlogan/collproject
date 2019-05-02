package com.collbackend.daos;

import java.util.List;

import com.collbackend.models.Blog;

public interface BlogDao {

	public boolean addBlog(Blog blog);
	public boolean updateBlog(Blog blog);
	public boolean deleteBlog(Blog blog);
	public boolean approveBlog(Blog blog);
	public boolean rejectBlog(Blog blog);
	public Blog getBlog(int blogId);
	public List<Blog> getAllBlogs();
	List<Blog> getBlogsApproved();
	List<Blog> getBlogsRejected();
	List<Blog> getBlogsWaitingForApproval();
	List<Blog> getBlogsByEmail(String email);

}
