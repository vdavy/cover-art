/**
 * 
 */
package com.stationmillenium.coverart.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Logger aspect for debugging and trace
 * @author vincent
 *
 */
@Aspect
public class LoggerAspect {

	//logger declaration
	private static final Logger logger = LoggerFactory.getLogger("aspect");
	
	//pointcut declaration
	/**
	 * All public methods
	 */
	@Pointcut("execution(public * com.stationmillenium.coverart..*.*(..))")
	public void loggerDebug() {}
	
	/**
	 * All private methods except these of this aspect
	 */
	@Pointcut("execution(private * com.stationmillenium.coverart..*.*(..)) && !(execution(private * com.stationmillenium.coverart.aspects.LoggerAspect.*(..)))")
	public void loggerTrace() {}
	
	
	//advices declaration 
	//public declaration
	/**
	 * Before for public methods
	 */
	@Before("loggerDebug()")
	public void loggerDebugBefore(JoinPoint joinPoint) { 
		if (logger.isDebugEnabled())
			logger.debug(getMethodInformation(joinPoint));
 	}
		
	/**
	 * After returning public methods
	 * 
	 */
	@AfterReturning(pointcut = "loggerDebug()", returning = "returnObject")
	public void loggerDebugAfterReturning(Object returnObject, JoinPoint joinPoint) {
		if (logger.isDebugEnabled())
			logger.debug(getMehodInformationAfter(returnObject, joinPoint));
	}
	
	/**
	 * After throwing public methods
	 * 
	 */
	@AfterThrowing(pointcut = "loggerDebug()", throwing = "exception")
	public void loggerDebugAfterThrowing(Exception exception, JoinPoint joinPoint) {
		if (logger.isDebugEnabled())
			logger.debug(getMehodInformationAfter(exception.getMessage(), joinPoint));
	}
	
	//private declarations
	/**
	 * Before private methods
	 */
	@Before("loggerTrace()")
	public void loggerTraceBefore(JoinPoint joinPoint) { 
		if (logger.isTraceEnabled())
			logger.trace(getMethodInformation(joinPoint));
 	}	
	
	/**
	 * After returning private methods
	 * 
	 */
	@AfterReturning(pointcut = "loggerTrace()", returning = "returnObject")
	public void loggerTraceAfterReturning(Object returnObject, JoinPoint joinPoint) {
		if (logger.isTraceEnabled())
			logger.trace(getMehodInformationAfter(returnObject, joinPoint));
	}
	
	/**
	 * After throwing private methods
	 * 
	 */
	@AfterThrowing(pointcut = "loggerTrace()", throwing = "exception")
	public void loggerTraceAfterThrowing(Exception exception, JoinPoint joinPoint) {
		if (logger.isTraceEnabled())
			logger.trace(getMehodInformationAfter(exception.getMessage(), joinPoint));
	}
	
	
	//utils methods
	/**
	 * Get information about after
	 */
	private String getMehodInformationAfter(Object returnObject, JoinPoint joinPoint) {
		StringBuilder logString = new StringBuilder();
		logString.append(joinPoint.getSignature().toShortString()); //method name
		logString.append(" return:");
		logString.append(parseNullString(returnObject)); //return
		return logString.toString();
	}
	
	
	/**
	 * Get information about before
	 */
	private String getMethodInformation(JoinPoint joinPoint) {
		StringBuilder logString = new StringBuilder();
		logString.append(joinPoint.getSignature().toShortString()); //method name
		logString.append(" args:#");
		logString.append((joinPoint.getArgs() != null) ? joinPoint.getArgs().length : "null"); //args count
		logString.append("[");		
		for (Object arg : joinPoint.getArgs()) //for each args
			logString.append(parseNullString(arg) + ","); //value
		logString.deleteCharAt(logString.length() - 1);
		logString.append("]");
		
		return logString.toString();
	}
	
	/**
	 * Parser object and return toString() or null text
	 * @param object object to parse
	 * @return toString() or "null" else
	 */
	private String parseNullString(Object object) {
		if (object instanceof Class<?>)
			return ((Class<?>) object).getName();
		else {
			Expression expression = new SpelExpressionParser().parseExpression("#this != null ? #this.toString() : 'null'");
			return expression.getValue(object, String.class);
		}
	}
}
