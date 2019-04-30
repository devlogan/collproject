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

import com.collbackend.daos.BlogDao;
import com.collbackend.models.Blog;

@Repository("blogDao")
@Transactional
public class BlogDaoImpl implements BlogDao {

	private static final Logger logger=LogManager.getLogger(BlogDaoImpl.class);	

	@Autowired
	SessionFactory sessionFactory;

	public boolean addBlog(Blog blog) {

		logger.info("Process of adding blog");
		try {
			sessionFactory.getCurrentSession().save(blog);

			logger.info("Added blog Successfully",blog);
			return true;
		}
		catch(HibernateException e) {

			logger.error("Exception occured while adding blog" + e);
			e.printStackTrace();

		}
		return false;
	}

	public boolean deleteBlog(Blog blog) {

		logger.info("Process of deleting blog");
		try {
			sessionFactory.getCurrentSession().delete(blog);

			logger.info("deleted blog Successfully",blog);
			return true;
		}
		catch(HibernateException e) {

			logger.error("Exception occured while deleting blog" + e);
			e.printStackTrace();

		}
		return false;
	}

	public boolean updateBlog(Blog blog) {

		logger.info("Process of updating blog");
		try {
			sessionFactory.getCurrentSession().update(blog);

			logger.info("Updated blog Successfully");
			return true;
		}
		catch(HibernateException e) {

			logger.error("Exception occured while updating blog" + e);
			e.printStackTrace();

		}
		return false;
	}

	public Blog getBlog(int blogId) {

		logger.info("Process of getting blog's info");
		try {
			Session session=sessionFactory.getCurrentSession();
			Blog blog=session.get(Blog.class,blogId);
			return blog;

		}
		catch(HibernateException e){
			logger.error("Exception occured while checking blog's info" + e);
			e.printStackTrace();
		}
		return null;
	}

	public List<Blog> getAllBlogs() {

		Session session=sessionFactory.getCurrentSession();
		Query<Blog> query=session.createQuery("from Blog");
		List<Blog> blogList=query.list();
		return blogList;

	}

	public boolean approveBlog(Blog blog) {
		try
		{
			blog.setApproved("Approved");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}
		catch(HibernateException e)
		{
			logger.error("Exception occured while approving blog" + e);
			e.printStackTrace();
			return false;
		}

	}

	public boolean rejectBlog(Blog blog) {
		try
		{
			blog.setApproved("Rejected");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}
		catch(HibernateException e)
		{
			logger.error("Exception occured while approving blog" + e);
			e.printStackTrace();
			return false;
		}

	}

	public List<Blog> getBlogsApproved() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Blog where approved='Approved'");
		List<Blog> blogsApproved=query.list();
		return blogsApproved;
	}

	public List<Blog> getBlogsWaitingForApproval() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Blog where approved='Pending'");
		List<Blog> blogsWaitingForApproval=query.list();
		return blogsWaitingForApproval;
	}

	public List<Blog> getBlogsRejected() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Blog where approved='Rejected'");
		List<Blog> blogsRejected=query.list();
		return blogsRejected;
	}

	public List<Blog> getBlogsByEmail(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Blog where B_Email= :email");
		query.setParameter("email", email);
		List<Blog> blogs=query.list();
		return blogs;

	}

}
