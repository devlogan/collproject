package com.collbackend.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collbackend.daos.JobDao;
import com.collbackend.models.Job;

@Transactional
@Repository("jobDao")
public class JobDaoImpl implements JobDao {

	@Autowired
	private SessionFactory sessionFactory;
	public void addJob(Job job) {

		try {
			Session session=sessionFactory.getCurrentSession();
			session.save(job);	

		}catch(HibernateException e) {
			e.printStackTrace();
		}

	}

	public List<Job> getAllJobs() {

		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Job");
			List<Job> jobs=query.list();
			return jobs;

		}catch(HibernateException e) {
			e.printStackTrace();
		}
		return null;

	}


}
