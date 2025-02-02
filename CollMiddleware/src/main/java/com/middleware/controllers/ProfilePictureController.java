package com.middleware.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.collbackend.daos.ProfilePictureDao;
import com.collbackend.models.ProfilePicture;
import com.collbackend.models.User;

@RestController
public class ProfilePictureController {

	@Autowired
	private ProfilePictureDao profilePictureDao;


	@PostMapping(value="uploadPicture")
	public ResponseEntity<?> uploadPicture(@RequestParam MultipartFile image,HttpSession session)
	{   
		String email=(String)session.getAttribute("emailId");
		System.out.println(email);
		if(email==null) {
			Error errorObj=new Error("User is not signed in");
			return new ResponseEntity<Error>(errorObj,HttpStatus.UNAUTHORIZED);
		}
		else {

			ProfilePicture picture=new ProfilePicture();
			picture.setEmail(email);
			try {
				picture.setImage(image.getBytes());
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			profilePictureDao.saveOrUpdate(picture);
			return new ResponseEntity<String>("Uploaded Successfully",HttpStatus.OK);
		}


	}

	@RequestMapping(value="/getimage",method=RequestMethod.GET)
	public @ResponseBody byte[] getImage(@RequestParam String email, HttpSession session)
	{
		System.out.println(email);
		if(email==null) 
		{
			return null;
		}
		ProfilePicture profilePicture=profilePictureDao.getProfilePicture(email);
		if(profilePicture==null) 
			return null;
		else
			return profilePicture.getImage();
	}


}
