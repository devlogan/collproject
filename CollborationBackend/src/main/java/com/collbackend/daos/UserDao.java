package com.collbackend.daos;

import com.collbackend.models.User;

public interface UserDao {
	
	boolean registerUser(User user);
	boolean isEmailUnique(String email);
	boolean isUsernameUnique(String username);
	boolean makeOnline(User user);
	User login(User user);
	User getUserByEmail(String email);
	User getUserByUsername(String username);
	User updateUser(User user);

}
