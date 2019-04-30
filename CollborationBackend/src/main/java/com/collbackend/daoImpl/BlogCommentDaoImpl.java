package com.collbackend.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collbackend.daos.BlogCommentDao;
import com.collbackend.models.BlogComment;

@Repository("blogCommentDao")
@Transactional
public class BlogCommentDaoImpl implements BlogCommentDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void addBlogComment(BlogComment blogComment) {
		
		sessionFactory.getCurrentSession().save(blogComment);
		
		
	}

	public List<BlogComment> getAllBlogComments(int blogId) {
		
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogComment where blogId = :blogId");
		query.setParameter("blogId", blogId);
		List<BlogComment> blogComments=query.list();
		return blogComments;
	}

	public void deleteBlogComment(BlogComment blogComment) {
		
		sessionFactory.getCurrentSession().delete(blogComment);
		
		
	}

}
