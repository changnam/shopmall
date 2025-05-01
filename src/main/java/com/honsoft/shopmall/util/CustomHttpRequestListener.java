package com.honsoft.shopmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
@Component
public class CustomHttpRequestListener implements ServletRequestListener{
	private static Logger logger = LoggerFactory.getLogger(CustomHttpRequestListener.class);
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		logger.info("REQUEST INITIALIZED: ID+"+sre.getServletRequest().getRequestId());
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		logger.info("REQUEST DESTROYED: ID+"+sre.getServletRequest().getRequestId());
	}
}
