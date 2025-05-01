package com.honsoft.shopmall.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomLoggingAspect {
	private final Logger logger = LoggerFactory.getLogger(CustomLoggingAspect.class);
	
	@Before("execution(* com.honsoft.shopmall..*(..))")
	public void logBeforeMethodCall(JoinPoint jp) {
		logger.info(jp.getSignature()+" >>> 메소드 호출 시작");
	}
	
	@After("execution(* com.honsoft.shopmall..*(..))")
	public void logAfterMethodCall(JoinPoint jp) {
		logger.info(jp.getSignature()+" >>> 메소드 호출 완료");
	}
}
