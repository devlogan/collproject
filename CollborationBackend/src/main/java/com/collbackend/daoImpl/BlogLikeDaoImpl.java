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
import com.collbackend.daos.BlogLikeDao;
import com.collbackend.models.Blog;
import com.collbackend.models.BlogDislikes;
import com.collbackend.models.BlogLikes;
import com.collbackend.models.User;

@Transactional
@Repository("blogLikeDao")
public class BlogLikeDaoImpl implements BlogLikeDao {

	private static final Logger logger=LogManager.getLogger(BlogLikeDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	BlogDao blogDao;

	public boolean hasUserLikedBlog(int blogId, String email, String userName) {

		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from BlogLikes where blogId=:blogId and userEmail=:email");
			query.setInteger("blogId", blogId);
			query.setString("email", email);
			BlogLikes blogLikes=(BlogLikes) query.uniqueResult();
			//to check if user has already liked the blog
			if(blogLikes!=null) {

				session.delete(blogLikes);
				Blog blog=blogDao.getBlog(blogId);
				blog.setLikes(blog.getLikes()-1);
				blogDao.updateBlog(blog);
				return true;
			}
			else {

				blogLikes=new BlogLikes();
				blogLikes.setBlogId(blogId);
				blogLikes.setUserEmail(email);
				blogLikes.setUserName(userName);

				Blog blog=blogDao.getBlog(blogId);
				blog.setLikes(blog.getLikes()+1);
				blogDao.updateBlog(blog);
				session.save(blogLikes);
			}
		}
		catch(HibernateException e) {

		}
		return false;

	}

	public boolean hasUserDisLikedBlog(int blogId, String email, String userName) {

		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from BlogDislikes where blogId=:blogId and userEmail=:email");
			query.setInteger("blogId", blogId);
			query.setString("email", email);
			BlogDislikes blogDislikes=(BlogDislikes) query.uniqueResult();
			//to check if user has already liked the blog
			if(blogDislikes!=null) {

				session.delete(blogDislikes);
				Blog blog=blogDao.getBlog(blogId);
				blog.setDislikes(blog.getDislikes()-1);
				blogDao.updateBlog(blog);
				return true;
			}
			else {

				blogDislikes=new BlogDislikes();
				blogDislikes.setBlogId(blogId);
				blogDislikes.setUserEmail(email);
				blogDislikes.setUserName(userName);
				session.save(blogDislikes);

				Blog blog=blogDao.getBlog(blogId);
				blog.setDislikes(blog.getDislikes()+1);
				blogDao.updateBlog(blog);

			}

		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<BlogLikes> getBlogLike(int blogId) {
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from BlogLikes where blogId= :blogId");
			query.setParameter("blogId", blogId);
			List<BlogLikes> blogLikes= query.getResultList();

			return blogLikes;
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BlogDislikes> getBlogDislike(int blogId) {
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from BlogLikes where blogId= :blogId");
			query.setParameter("blogId", blogId);
			List<BlogDislikes> blogDislikes= query.getResultList();

			return blogDislikes;
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}





}
