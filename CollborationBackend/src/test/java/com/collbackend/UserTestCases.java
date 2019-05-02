package com.collbackend;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.collbackend.config.DBConfig;
import com.collbackend.daos.UserDao;
import com.collbackend.models.User;

public class UserTestCases {

	static UserDao userDao;
    
	
	@BeforeClass
	public static void init(){
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.register(DBConfig.class);
		context.refresh();

		userDao=context.getBean("userDao",UserDao.class);
		//context.close();
	}

	@Test
	//@Ignore
	public void shouldAnswerWithTrue()
	{
		User user=new User();
		user.setEmail("divyanshu123566@gmail.com");
		user.setFirstName("Divyanshu");
		user.setLastName("Shrivastava");
		user.setOnline("Offline");
		user.setPassword("d123");
		user.setMobileNo("9878786756");;
		user.setRole("Admin");
		user.setUsername("devlogan");

		assertTrue("Problem in  Registering User",userDao.registerUser(user));
	}
    
	@Test
	@Ignore
	public void fetchingUser()
	{   boolean r=false;
		User user=userDao.getUserByUsername("devlogan");
		if(user!=null) {
			 r= true;
		}
		assertTrue("Problem in  Registering User",r);
	}
}
