package com.collbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.collbackend.models.Blog;
import com.collbackend.models.BlogComment;
import com.collbackend.models.BlogDislikes;
import com.collbackend.models.BlogLikes;
import com.collbackend.models.Friend;
import com.collbackend.models.Job;
import com.collbackend.models.Notification;
import com.collbackend.models.ProfilePicture;
import com.collbackend.models.User;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.collbackend")
public class DBConfig {

	@Bean(name="dataSource")
	public DataSource getDataSource() {

		DriverManagerDataSource dataSource=new DriverManagerDataSource();

		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		dataSource.setUsername("divyanshu");
		dataSource.setPassword("divyanshu123");

		System.out.println("Data Source Created");

		return dataSource;

	}

	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory() {

		Properties properties=new Properties();
		properties.put("hibernate.dialect","org.hibernate.dialect.OracleDialect");
		properties.put("hibernate.show_sql",true);
		properties.put("hibernate.hbm2ddl.auto","update");

		LocalSessionFactoryBuilder builder=new LocalSessionFactoryBuilder(getDataSource());
		builder.addProperties(properties);
		builder.addAnnotatedClass(User.class);
		builder.addAnnotatedClass(ProfilePicture.class);
		builder.addAnnotatedClass(Friend.class);
		builder.addAnnotatedClass(Blog.class);
		builder.addAnnotatedClass(BlogLikes.class);
		builder.addAnnotatedClass(BlogDislikes.class);
		builder.addAnnotatedClass(Job.class);
		builder.addAnnotatedClass(BlogComment.class);
		builder.addAnnotatedClass(Notification.class);

		SessionFactory sessionFactory=builder.buildSessionFactory();

		System.out.println("Session Factory created");

		return sessionFactory;

	}

	@Bean(name="transactionManager")
	@Autowired
	public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory){

		HibernateTransactionManager txManager=new HibernateTransactionManager(sessionFactory);

		System.out.println("Transaction manager created...");

		return txManager;
	}


}
