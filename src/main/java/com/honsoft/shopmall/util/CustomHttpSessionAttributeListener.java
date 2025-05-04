package com.honsoft.shopmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@Component
public class CustomHttpSessionAttributeListener implements HttpSessionAttributeListener {
	private static Logger logger = LoggerFactory.getLogger(CustomHttpSessionAttributeListener.class);
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		logger.info("session attribute "+se.getName()+"added.");
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		logger.info("session attribute "+se.getName()+"removed.");
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		logger.info("session attribute "+se.getName()+"replaced.");
	}
}
