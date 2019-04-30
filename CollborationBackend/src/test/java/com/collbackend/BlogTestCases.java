package com.collbackend;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.collbackend.config.DBConfig;
import com.collbackend.daos.BlogDao;
import com.collbackend.models.Blog;
import com.collbackend.models.User;

public class BlogTestCases {

static BlogDao blogDao;
    
	
	@BeforeClass
	public static void init(){
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.register(DBConfig.class);
		context.refresh();

		blogDao=context.getBean("blogDao",BlogDao.class);
		//context.close();
	}

	@Test
	@Ignore
	public void shouldAnswerWithTrue()
	{
		Blog blog=new Blog();
		blog.setBlogName("Core Java");
		blog.setBlogContent("all core concepts");
		blog.setDate(new Date());
		blog.setEmail("divyanshu@gmail.com");
		blog.setApproved("Approved");
		blog.setUserName("Divyanshu Srivastava");
  

		assertTrue("Problem in  Registering User",blogDao.addBlog(blog));
	}
	
	@Test
	//@Ignore
	public void getBlog() {
		
	blogDao.getBlog(11);
	
		
		
	}
}
