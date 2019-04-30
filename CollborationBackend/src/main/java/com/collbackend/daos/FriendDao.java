package com.collbackend.daos;

import java.util.List;

import com.collbackend.models.Friend;
import com.collbackend.models.User;

public interface FriendDao {


	List<User> getAllSuggestedUsers(String email);
	void addFriend(Friend friend);
	List<Friend> getPendingRequests(String email);
	void acceptRequest(Friend friend);
	void deleteRequest(Friend friend);
	List<Friend> listOfFriends(String email);
	List<User> searchUsers(String keyword);

}
