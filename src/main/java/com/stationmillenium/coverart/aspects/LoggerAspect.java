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
 * Aspect pour le logger en débug et en trace
 * @author vincent
 *
 */
@Aspect
public class LoggerAspect {

	//déclaration du logger
	private static final Logger logger = LoggerFactory.getLogger("aspect");
	
	//déclaration des pointcut
	/**
	 * Toutes les méthodes publiques
	 */
	@Pointcut("execution(public * com.stationmillenium.covertart..*.*(..))")
	public void loggerDebug() {}
	
	/**
	 * Toutes les méthodes privées sauf celle de cet aspect
	 */
	@Pointcut("execution(private * com.stationmillenium.coverart..*.*(..)) && !(execution(private * com.stationmillenium.coverart.aspects.LoggerAspect.*(..)))")
	public void loggerTrace() {}
	
	
	//déclaration des advices
	//déclaration publiques
	/**
	 * Before pour les méthodes publiques
	 */
	@Before("loggerDebug()")
	public void loggerDebugBefore(JoinPoint joinPoint) { 
		if (logger.isDebugEnabled())
			logger.debug(getMethodInformation(joinPoint));
 	}
		
	/**
	 * After returning des méthodes publiques
	 * 
	 */
	@AfterReturning(pointcut = "loggerDebug()", returning = "returnObject")
	public void loggerDebugAfterReturning(Object returnObject, JoinPoint joinPoint) {
		if (logger.isDebugEnabled())
			logger.debug(getMehodInformationAfter(returnObject, joinPoint));
	}
	
	/**
	 * After throwing des méthodes publiques
	 * 
	 */
	@AfterThrowing(pointcut = "loggerDebug()", throwing = "exception")
	public void loggerDebugAfterThrowing(Exception exception, JoinPoint joinPoint) {
		if (logger.isDebugEnabled())
			logger.debug(getMehodInformationAfter(exception.getMessage(), joinPoint));
	}
	
	//déclaration privées
	/**
	 * Before pour les méthodes privées
	 */
	@Before("loggerTrace()")
	public void loggerTraceBefore(JoinPoint joinPoint) { 
		if (logger.isTraceEnabled())
			logger.trace(getMethodInformation(joinPoint));
 	}	
	
	/**
	 * After returning des méthodes privées
	 * 
	 */
	@AfterReturning(pointcut = "loggerTrace()", returning = "returnObject")
	public void loggerTraceAfterReturning(Object returnObject, JoinPoint joinPoint) {
		if (logger.isTraceEnabled())
			logger.trace(getMehodInformationAfter(returnObject, joinPoint));
	}
	
	/**
	 * After throwing des méthodes privées
	 * 
	 */
	@AfterThrowing(pointcut = "loggerTrace()", throwing = "exception")
	public void loggerTraceAfterThrowing(Exception exception, JoinPoint joinPoint) {
		if (logger.isTraceEnabled())
			logger.trace(getMehodInformationAfter(exception.getMessage(), joinPoint));
	}
	
	
	//méthodes utilitaires	
	/**
	 * Obtenir des informations sur un after
	 */
	private String getMehodInformationAfter(Object returnObject, JoinPoint joinPoint) {
		StringBuilder logString = new StringBuilder();
		logString.append(joinPoint.getSignature().toShortString()); //le nom de la méthode
		logString.append(" retour:");
		logString.append(parseNullString(returnObject)); //le retour
		return logString.toString();
	}
	
	
	/**
	 * Obtenir les informations sur un before
	 */
	private String getMethodInformation(JoinPoint joinPoint) {
		StringBuilder logString = new StringBuilder();
		logString.append(joinPoint.getSignature().toShortString()); //le nom de la méthode
		logString.append(" args:#");
		logString.append((joinPoint.getArgs() != null) ? joinPoint.getArgs().length : "null"); //le nombre d'argument
		logString.append("[");		
		for (Object arg : joinPoint.getArgs()) //pour chaque argument
			logString.append(parseNullString(arg) + ","); //sa valeur
		logString.deleteCharAt(logString.length() - 1);
		logString.append("]");
		
		return logString.toString();
	}
	
	/**
	 * Parser un objet et renvoyer son ToString ou null textuel
	 * @param object l'objet à parser
	 * @return sa valeur toString ou "null" sinon
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
