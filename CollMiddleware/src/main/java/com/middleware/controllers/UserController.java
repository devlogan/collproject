package com.middleware.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collbackend.daos.UserDao;
import com.collbackend.models.User;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;

	@GetMapping(value="hello")
	public ResponseEntity<String> demo(){

		return new ResponseEntity<String>("Hello",HttpStatus.OK);
	}

	@PostMapping(value="register")
	public ResponseEntity<?> registerUser(@RequestBody User user){

		System.out.println("Registering user process in middleware");

		if(userDao.isEmailUnique(user.getEmail())){

			System.out.println("User email is unique");
			user.setRole("User");
			boolean r=userDao.registerUser(user);

			if(r){

				return new ResponseEntity<User>(user,HttpStatus.OK);
			}
			else {
				Error errorObj=new Error("Problem in Registering User. Try again");
				return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		else {
			System.out.println("Email is not unique");
			Error errorObj=new Error("Email is already Registered");
			return new ResponseEntity<Error>(errorObj,HttpStatus.CONFLICT);
		}
	}

	@PostMapping(value="login")
	public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session){

		System.out.println("signing in user");
		User userObj=userDao.login(user);
		if(userObj!=null) {

			session.setAttribute("emailId", userObj.getEmail());
			session.setAttribute("username",userObj.getUsername());
			session.setAttribute("userFullName",userObj.getFirstName()+" "+userObj.getLastName());
			return new ResponseEntity<User>(userObj,HttpStatus.OK);
		}

		else {
			System.out.println("Email or password incorrect");
			Error errorObj=new Error("Incorrect email or password");
			return new ResponseEntity<Error>(errorObj,HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(value="viewProfile")
	public ResponseEntity<?> viewProfile(@RequestParam("param1") String email){


		User user=userDao.getUserByEmail(email);
		if(user!=null) {
			System.out.println("getting user here");
			user.setPassword("null");
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}

		else {
			System.out.println("Incorrect username");
			Error errorObj=new Error("username doesn't exist");
			return new ResponseEntity<Error>(errorObj,HttpStatus.CONFLICT);
		}

	}

	@PostMapping(value="editProfile")
	public ResponseEntity<?> editProfile(@RequestBody User user){

		System.out.println("updating user");
		User userObj=userDao.updateUser(user);
		if(userObj!=null) {

			return new ResponseEntity<User>(userObj,HttpStatus.OK);
		}

		else {
			System.out.println("Could not update");
			Error errorObj=new Error("Cannot be updated");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}


