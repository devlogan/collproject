package com.middleware.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.collbackend.config.DBConfig;


//@EnableWebMvc
//In place of web.xml
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	public WebAppInitializer(){
		System.out.println("webappinitializer is loaded and instantiated");
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{DBConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[]{WebAppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[]{"/"};
	}

}