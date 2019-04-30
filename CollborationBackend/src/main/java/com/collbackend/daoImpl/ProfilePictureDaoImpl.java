package com.collbackend.daoImpl;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collbackend.daos.ProfilePictureDao;
import com.collbackend.models.ProfilePicture;

@Transactional
@Repository("profilePictureDao")
public class ProfilePictureDaoImpl implements ProfilePictureDao {

	private static final Logger logger=LogManager.getLogger(ProfilePictureDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	public boolean saveOrUpdate(ProfilePicture picture) {
		
		logger.info("Process of adding profile picture");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(picture);
			
			logger.info("Picture added Successfully",picture);
			return true;
		}
		catch(HibernateException e) {
			
			logger.error("Exception occured while adding picture" + e);
			e.printStackTrace();
		}
		return false;
	}

	public ProfilePicture getProfilePicture(String email) {

		try {
			logger.info("Process of fetching profile picture");
			
			ProfilePicture picture = sessionFactory.getCurrentSession().get(ProfilePicture.class, email);
			
			return picture;	
		}
		catch(HibernateException e) {
			logger.error("Exception occured while fetching picture" + e);
			e.printStackTrace();
		}
		return null;
	}

}
