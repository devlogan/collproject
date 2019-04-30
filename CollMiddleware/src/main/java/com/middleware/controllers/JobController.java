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
import org.springframework.web.bind.annotation.RestController;

import com.collbackend.daos.JobDao;
import com.collbackend.daos.UserDao;
import com.collbackend.models.Job;
import com.collbackend.models.User;

@RestController
public class JobController {

	@Autowired
	private JobDao jobDao;
	@Autowired
	private UserDao userDao;



	@PostMapping(value="addJob")
	public ResponseEntity<?> addJob(@RequestBody Job job,HttpSession session)
	{
		String email=(String)session.getAttribute("emailId");
		if(email==null)
		{
			Error error=new Error("You must Login");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); 
		}
		User user=userDao.getUserByEmail(email);
		if(!user.getRole().equals("Admin"))
		{
			Error error=new Error("Access Denied; you are not authorised");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);  
		}

		try{
			job.setPostedOn(new Date());
			jobDao.addJob(job);

			System.out.println("Session Id::::"+session.getId());
			System.out.println("Session Creation time:::: "+session.getCreationTime());
			System.out.println("Session Attribute loginId value:::"+session.getAttribute("emailId"));
		}catch(Exception e){
			Error error=new Error("Job details not inserted..something went wrong..");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}

	@GetMapping(value="getAllJobs")
	public ResponseEntity<?> getAllJobs(HttpSession session)
	{

		System.out.println("Session Id::::"+session.getId());
		System.out.println("Session Creation time:::: "+session.getCreationTime());
		System.out.println("Session Attribute loginId value:::"+session.getAttribute("emailId"));

		String email=(String)session.getAttribute("emailId");
		if(email==null)
		{
			Error error=new Error("You must Login");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); 
		} 
		List<Job> jobs=jobDao.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}

}
