package com.scraper.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("InstanceFactory")
public class InstanceFactory {
	
	@Autowired
    private BeanFactory beanFactory;
	
	private static BeanFactory beanFactoryStatic;
	
	@PostConstruct
	public void init() {
		beanFactoryStatic = beanFactory;
	}
	
	
	public static ACADriver getACADriverInstance() {
		
		return (ACADriver) beanFactoryStatic.getBean("ACADriver");
		
	}
	
	public static Sherlock getSherlockInstance() {
		
		return (Sherlock) beanFactoryStatic.getBean("sherlock");
		
	}
	
	public static Object getInstance(String name) {
		
		return beanFactoryStatic.getBean(name);
	}

}
