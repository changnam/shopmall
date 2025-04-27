package com.honsoft.shopmall.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomLoggingAspect {
	@Before("execution(* com.honsoft.shopmall..*(..))")
	public void logBeforeMethodCall(JoinPoint jp) {
		System.out.println(jp.getSignature()+" >>> 메소드 호출 시작");
	}
	
	@After("execution(* com.honsoft.shopmall..*(..))")
	public void logAfterMethodCall(JoinPoint jp) {
		System.out.println(jp.getSignature()+" >>> 메소드 호출 완료");
	}
}
