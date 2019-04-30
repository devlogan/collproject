package com.collbackend.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collbackend.daos.NotificationDao;
import com.collbackend.models.Notification;

@Transactional
@Repository("notificationDao")
public class NotificationDaoImpl implements NotificationDao {

	@Autowired
	SessionFactory sessionFactory;

	public boolean addOrUpdateNotification(Notification notification) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(notification);
			return true;
		}catch(HibernateException e) {

			e.printStackTrace();
		}
		return false;
	}

	public Notification getNotificationById(int notificationId) {

		try {
			Session session=sessionFactory.getCurrentSession();
			Notification notification=session.get(Notification.class, notificationId);

			return notification;
		}catch(HibernateException e) {

		}

		return null;
	}

	public List<Notification> getAllNotificationByEmail(String email) {
		try {


			Session session=sessionFactory.getCurrentSession();
			Query<Notification> query=session.createQuery("from Notification where toUser.email=:email and status='Not Viewed'");
			query.setParameter("email", email);
			List<Notification> notificationList=query.list();
			return notificationList;

		}catch(HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateNotification(Notification notification) {

		try {



		}catch(HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}


}
