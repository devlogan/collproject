package com.collbackend.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collbackend.daos.FriendDao;
import com.collbackend.models.Friend;
import com.collbackend.models.User;

@Transactional
@Repository("friendDao")
public class FriendDaoImpl implements FriendDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<User> getAllSuggestedUsers(String email) {
		Session session=sessionFactory.getCurrentSession();

		String queryString="select * from UserTab where email in "
				+ "(select email from UserTab where email!=? "
				+ "minus "
				+ "(select toId_email from Friend where fromId_email=? "
				+ "union "
				+ "select fromId_email from Friend where toId_email=? )) ";
		SQLQuery query=session.createSQLQuery(queryString);
		query.setString(0, email);
		query.setString(1, email);
		query.setString(2, email);
		query.addEntity(User.class);
		List<User> suggestedUsers=query.list();

		return suggestedUsers;
	}

	public void addFriend(Friend friend) {

		sessionFactory.getCurrentSession().save(friend);

	}

	public List<Friend> getPendingRequests(String email) {

		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where status=? and toId.email=?");
		query.setCharacter(0, 'P');
		query.setString(1, email);
		List<Friend> pendingRequests=query.list();
		return pendingRequests;

	}

	public void acceptRequest(Friend request) {
		Session session=sessionFactory.getCurrentSession();
		request.setStatus('A');
		session.update(request);

	}

	public void deleteRequest(Friend request) {
		sessionFactory.getCurrentSession().delete(request);

	}

	public List<Friend> listOfFriends(String email) {

		Session session=sessionFactory.getCurrentSession();

		Query query1=session.createQuery
				("select f.toId from Friend f where f.fromId.email=? and f.status=?");

		query1.setString(0, email);
		query1.setCharacter(1, 'A');
		List<Friend> friendList1=query1.list();

		Query query2=session.createQuery("select f.fromId "
				+ "from Friend f  where f.toId.email=? and f.status=?");
		query2.setString(0, email);
		query2.setCharacter(1, 'A');
		List<Friend> friendList2=query2.list();

		friendList1.addAll(friendList2);
		return friendList1;

	}

	public List<User> searchUsers(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
