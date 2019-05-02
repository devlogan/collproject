package com.collbackend.daoImpl;


import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collbackend.daos.UserDao;
import com.collbackend.models.User;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

	private static final Logger logger=LogManager.getLogger(UserDaoImpl.class);	

	@Autowired
	SessionFactory sessionFactory;


	public boolean registerUser(User user) {

		logger.info("Process of Registering user");
		try {
			sessionFactory.getCurrentSession().save(user);

			logger.info("Registered user Successfully",user);
			return true;
		}
		catch(HibernateException e) {

			logger.error("Exception occured while registering user" + e);
			e.printStackTrace();

		}

		return false;
	}

	public boolean isEmailUnique(String email) {

		logger.info("Process of checking user's email");
		try {
			Session session=sessionFactory.getCurrentSession();
			User user=session.get(User.class,email);
			if(user==null){
				logger.info("User Email is unique");
				return true;
			}
		}
		catch(HibernateException e){
			logger.error("Exception occured while checking user's email" + e);
			e.printStackTrace();
		}
		return false;
	}

	public boolean isUsernameUnique(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public User login(User user) {

		logger.info("Process of signing in user");
		try{
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from User where email= :email and password= :password");
			query.setParameter("email", user.getEmail());
			query.setParameter("password",user.getPassword());
			List<User> users= query.getResultList();
			if(users.size()>0) {
				logger.info("Logged in successfully");
				return users.get(0);
			}
		}
		catch(HibernateException e){
			logger.error("Exception occured while signing in user" + e);
			e.printStackTrace();
		}

		return null;

	}

	public User getUserByEmail(String email) {

		logger.info("Process of getting user's info");
		try {
			Session session=sessionFactory.getCurrentSession();
			User user=session.get(User.class,email);
			return user;

		}
		catch(HibernateException e){
			logger.error("Exception occured while getting user's info" + e);
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByUsername(String username) {
		logger.info("Process of getting user's info");
		try {
			Session session=sessionFactory.getCurrentSession();
			User user=session.get(User.class,username);
			return user;

		}
		catch(HibernateException e){
			logger.error("Exception occured while getting user's info" + e);
			e.printStackTrace();
		}
		return null;

	}

	public User updateUser(User user) {

		logger.info("Process of updating user");
		try {
			sessionFactory.getCurrentSession().update(user);

			logger.info("Updated user Successfully",user);
			return user;
		}
		catch(HibernateException e) {

			logger.error("Exception occured while updating user" + e);
			e.printStackTrace();

		}
		return null;
	}

	public boolean makeOnline(User user) {

		try
		{
			user.setOnline("Online");
			sessionFactory.getCurrentSession().update(user);
			return true;
		}
		catch(HibernateException e)
		{
			System.out.println("Exception Arised:Make Online:"+e);
		}
		return false;
	}


}
