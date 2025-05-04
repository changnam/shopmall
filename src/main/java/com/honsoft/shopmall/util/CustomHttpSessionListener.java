package com.honsoft.shopmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class CustomHttpSessionListener implements HttpSessionListener{
	private static Logger logger = LoggerFactory.getLogger(CustomHttpSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		logger.info("SESSION CREATED: ID+"+se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		logger.info("SESSION DESTROYED: ID+"+se.getSession().getId());
	}
	
	
}
