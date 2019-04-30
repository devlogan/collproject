package com.collbackend.daos;

import com.collbackend.models.ProfilePicture;

public interface ProfilePictureDao {
	
	boolean saveOrUpdate(ProfilePicture profilePicture);
	ProfilePicture getProfilePicture(String email);

}
