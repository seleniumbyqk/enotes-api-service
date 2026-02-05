package com.enotes.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//Aspect
@Aspect
@Component
public class LoggingAspect {

	Logger log = LoggerFactory.getLogger(LoggingAspect.class);
	
	//Create Join point - it will call before controller
	//pointcut - @Before("execution (* com.enotes.controller..*(..))")
	//Before - advice
	//execution (* com.enotes.controller..*(..)) - execution
	
	/*
	@Before("execution (* com.enotes.controller..*(..))")    //Advice and pointcut
	public void beforeController(JoinPoint joinPoint)
	{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		
		log.info("Start Calling :: {} :: {}()", className, methodName);
	}
	
	
	@After("execution (* com.enotes.controller..*(..))")    //Advice and pointcut
	public void afterController(JoinPoint joinPoint)
	{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		
		log.info("End Calling :: {} :: {}()", className, methodName);
	}
	*/
	
	//Controller
	//Instead of before and after we can use around
	@Around("execution (* com.enotes.controller..*(..))")    //Advice and pointcut
	public Object joinPointController(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		
		log.info("Start Calling :: {} :: {}()", className, methodName);
		
		//Performance monitoring
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		
		log.info("End Calling :: {} :: {}() :: {} ms", className, methodName, duration);
		
		return result;
	}
	
	
	//Service
	@Around("execution (* com.enotes.service..*(..))")    //Advice and pointcut
	public Object joinPointService(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		
		log.info("Start Calling :: {} :: {}()", className, methodName);
		
		//Performance monitoring
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		
		log.info("End Calling :: {} :: {}() :: {} ms", className, methodName, duration);
		
		return result;
	}
	

}
