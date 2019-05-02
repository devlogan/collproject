package com.middleware.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collbackend.daos.BlogDao;
import com.collbackend.daos.BlogLikeDao;
import com.collbackend.daos.UserDao;
import com.collbackend.models.Blog;
import com.collbackend.models.BlogDislikes;
import com.collbackend.models.BlogLikes;
import com.collbackend.models.User;

@RestController
public class BlogController {

	@Autowired
	BlogDao blogDao;

	@Autowired
	UserDao userDao;

	@Autowired
	BlogLikeDao blogLikeDao;

	@PostMapping(value="addBlog")
	public ResponseEntity<?> addBlog(@RequestBody Blog blog, HttpSession session){

		blog.setDate(new Date());
		blog.setApproved("Pending");
		blog.setEmail((String)session.getAttribute("emailId"));
		blog.setUserName((String)session.getAttribute("userFullName"));

		if(session.getAttribute("emailId")!=null){
			blogDao.addBlog(blog);
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in adding Blog. Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	@PostMapping(value="editBlog")
	public ResponseEntity<?> editBlog(@RequestBody Blog blog){

		//blog.setDate(new Date().toString());
		boolean r=blogDao.updateBlog(blog);

		if(r){
			Blog blogObj=blogDao.getBlog(blog.getBlogsId());
			return new ResponseEntity<Blog>(blogObj,HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in adding Blog. Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
	@PostMapping(value="deleteBlog")
	public ResponseEntity<?> deleteBlog(@RequestBody Blog blog){

		//blog.setDate(new Date().toString());
		boolean r=blogDao.deleteBlog(blog);

		if(r){

			return new ResponseEntity<String>("deleted successfully",HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in deleting Blog. Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	@GetMapping(value="allBlogs")
	public ResponseEntity<?> getAllBlogs(){

		List<Blog> blogsList=blogDao.getAllBlogs();


		if(blogsList!=null){

			return new ResponseEntity<List<Blog>>(blogsList,HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in fetching BlogList. Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	@GetMapping(value="blogsApproved")
	public ResponseEntity<?> getApprovedBlogs(){

		List<Blog> blogsList=blogDao.getBlogsApproved();


		if(blogsList!=null){

			return new ResponseEntity<List<Blog>>(blogsList,HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in approving Blog. Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	@GetMapping(value="blogsPending")
	public ResponseEntity<?> getPendingBlogs(){

		List<Blog> blogsList=blogDao.getBlogsWaitingForApproval();


		if(blogsList!=null){

			return new ResponseEntity<List<Blog>>(blogsList,HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in fetching BlogList. Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}


	@GetMapping(value="/getBlog")
	public ResponseEntity<?> getBlog(HttpSession session,@RequestParam int blogId){

		String email=(String)session.getAttribute("emailId");
		System.out.println(email);
		Blog blog=blogDao.getBlog(blogId);
		System.out.println("blogpost id is:::"+blogId);
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
	}

	@GetMapping(value="/approveBlog")
	public ResponseEntity<?> approveBlog(HttpSession session,@RequestParam int blogId){

		System.out.println("inside approving blog-----------------------------");
		System.out.println("successfull1");

		String email=(String)session.getAttribute("emailId");
		if(email==null){
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}

		User user=userDao.getUserByEmail(email);
		if(!user.getRole().equals("Admin")){
			Error error=new Error("Access Denied.. You are not authorized approve the blogs");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}

		if(blogDao.approveBlog(blogDao.getBlog(blogId)) ) {
			System.out.println("successfull2");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in approving Blog Try again");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value="/rejectBlog")
	public ResponseEntity<?> rejectBlog(HttpSession session,@RequestParam int blogId){

		String email=(String)session.getAttribute("emailId");
		if(email==null){
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}

		User user=userDao.getUserByEmail(email);
		if(!user.getRole().equals("Admin")){
			Error error=new Error("Access Denied.. You are not authorized to reject the blog");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		blogDao.rejectBlog(blogDao.getBlog(blogId));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}	

	@GetMapping(value="/getMyBlogs")
	public ResponseEntity<?> getBlogsByEmail(HttpSession session){

		String email=(String)session.getAttribute("emailId");
		if(email==null){
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}

		List<Blog> blogs=blogDao.getBlogsByEmail(email);
		if(blogs==null){
			Error error=new Error("Server Error");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
		}
	}

	@GetMapping(value="likeBlog")
	public ResponseEntity<?> likeBlog(@RequestParam int blogId,HttpSession session)
	{

		String email=(String)session.getAttribute("emailId");
		String userName=(String)session.getAttribute("userFullName");
		if(email==null)
		{
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else {
			String message=new String("Liked Successfully");
			blogLikeDao.hasUserLikedBlog(blogId, email, userName);
			return new ResponseEntity<String>(message,HttpStatus.OK);
		}
	}

	@GetMapping(value="dislikeBlog")
	public ResponseEntity<?> dislikeBlog(@RequestParam int blogId,HttpSession session)
	{
		String email=(String)session.getAttribute("emailId");
		String userName=(String)session.getAttribute("userFullName");
		if(email==null)
		{
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else {
			blogLikeDao.hasUserDisLikedBlog(blogId, email, userName);
			String msg=new String("Successfully liked");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}
	}

	@GetMapping(value="usersLikedBlog")
	public ResponseEntity<?> usersWhoLikedBlog(@RequestParam int blogId,HttpSession session){

		String email=(String)session.getAttribute("emailId");
		if(email==null)
		{
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogLikes> blogLikes= blogLikeDao.getBlogLike(blogId);
		return new ResponseEntity<List<BlogLikes>>(blogLikes,HttpStatus.OK);


	}
	@GetMapping(value="usersDislikedBlog")
	public ResponseEntity<?> usersWhoDislikedBlog(@RequestParam int blogId,HttpSession session){

		String email=(String)session.getAttribute("emailId");
		if(email==null)
		{
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogDislikes> blogDislikes= blogLikeDao.getBlogDislike(blogId);
		return new ResponseEntity<List<BlogDislikes>>(blogDislikes,HttpStatus.OK);


	}



}
