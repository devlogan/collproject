package com.middleware.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collbackend.daos.BlogCommentDao;
import com.collbackend.models.BlogComment;

@RestController
public class BlogCommentController {

	@Autowired
	private BlogCommentDao blogCommentDao;

	@PostMapping("addBlogComment")
	public ResponseEntity<?> addBlogComment(HttpSession session,@RequestBody BlogComment blogComment,@RequestParam int blogId){

        System.out.println("entered inside addcomment");
		String email=(String)session.getAttribute("emailId");
		String userName=(String) session.getAttribute("userFullName");
		if(email==null){
			Error error=new Error("Please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}

		blogComment.setBlogId(blogId);
		blogComment.setDate(new Date());
		blogComment.setEmail(email);
		blogComment.setUserName(userName);
		 System.out.println("try to add bro");
		blogCommentDao.addBlogComment(blogComment);
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}

	@GetMapping(value="getBlogComments")
	public ResponseEntity<?> getAllBlogComments(HttpSession session,@RequestParam int blogId){

		String email=(String)session.getAttribute("emailId");
		/*
		 * if(email==null){ Error error=new Error("Please login.."); return new
		 * ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); }
		 */
		List<BlogComment> blogComments=	blogCommentDao.getAllBlogComments(blogId);
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
	}

	@PutMapping(value="deleteBlogComment")
	public ResponseEntity<?> deleteBlogComment(@RequestBody BlogComment blogComment, HttpSession session){
		String email = (String) session.getAttribute("emailId");
		if (email == null) {
			Error error = new Error("Unauthorized access.. please login.....");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		blogCommentDao.deleteBlogComment(blogComment);
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}


}


