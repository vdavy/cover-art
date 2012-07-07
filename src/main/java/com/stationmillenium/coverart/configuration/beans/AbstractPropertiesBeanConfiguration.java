/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Abstract class for bean configuration classes
 * @author vincent
 *
 */
public abstract class AbstractPropertiesBeanConfiguration<T extends PropertyBeanInterface> implements ApplicationContextAware {

	//application context
	private ApplicationContext context;
	private static final Logger LOGGER  =  LoggerFactory.getLogger("Property initialization");
	
	//abstract methods
	/**
	 * Check values of properties
	 * @throws PropertyBeanException if a value is incorrect
	 */
	protected abstract void propertyCustomChecker() throws PropertyBeanException;
	
	/**
	 * Build the output bean with correct values
	 * @return the output bean
	 */
	protected abstract T buildBean();
	
	/**
	 * Provide the output bean, properly casted for dependency injection
	 * @return the configuration bean
	 */
	@Bean
	@Scope("singleton")	
	protected abstract T getBean();
		
	/**
	 * Assemble the bean : check values and make it
	 * @return the bean
	 */	
	protected T assembleBean() {
		try {
			checkNullOrEmptyPropertiesValues(); //check no null or empty value
			propertyCustomChecker(); //custom values
			return buildBean();
		} catch (PropertyBeanException e) { //if error
			LOGGER.error("Error during property parsing - app starting stopped", e);
			((AbstractApplicationContext) context).close(); //unload context (stop starting)
			return null; //nothing to return
		}
	}
		
	/**
	 * Check all properties values against null or empty values
	 * @throws PropertyBeanException if an error is found
	 */
	private void checkNullOrEmptyPropertiesValues() throws PropertyBeanException {
		Field[] propertyFields = this.getClass().getSuperclass().getDeclaredFields();
		if (propertyFields.length > 0) { //if fields found
			for (Field propertyField : propertyFields) {
				if (propertyField.getType() == String.class) {
					try {
						PropertyDescriptor propDesc = new PropertyDescriptor(propertyField.getName(), this.getClass()); //set up property accessor
						String value = (String) propDesc.getReadMethod().invoke(this, (Object[]) null); //get the property value
						if (value == null) //if null
							throw new PropertyBeanException(propertyField.getName(), "is null");
						else if (value.length() == 0) //if empty
							throw new PropertyBeanException(propertyField.getName(), "is empty");
						
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
						throw new PropertyBeanException(propertyField.getName(), e);
					}
				}
			}			
		}
	}
	

	/**
	 * Check a property value againt a spring expression
	 * @param expression the expression to check - mapped to "property" in expression
	 * @param propertyName the property to check : name
	 * @throws PropertyBeanException if value is incorrect
	 */
	protected void checkValueAgainstExpress(String expression, String propertyName) throws PropertyBeanException {
		Field[] propertyFields = this.getClass().getSuperclass().getDeclaredFields();
		if (propertyFields.length > 0) { //if fields found
			for (Field propertyField : propertyFields) {
				if (propertyField.getName().equals(propertyName)) { //if the selected property
					try { //check it
						PropertyDescriptor propDesc = new PropertyDescriptor(propertyField.getName(), this.getClass()); //set up property accessor
						String valueToCheck = (String) propDesc.getReadMethod().invoke(this, (Object[]) null); //get the property value
						
						//context and parser init
						EvaluationContext context = new StandardEvaluationContext();
						context.setVariable("property", valueToCheck);
						ExpressionParser parser = new SpelExpressionParser();
						
						//try expression
						if (!parser.parseExpression(expression).getValue(context, Boolean.class))
							throw new PropertyBeanException(propertyField.getName(), "expression not validated (" + expression + ")"); //if not valid : exception
						
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException | SpelEvaluationException e) {
						throw new PropertyBeanException(propertyField.getName(), e);
					}
				}
			}			
		}		
	}
	
	/**
	 * Set up context
	 * @param applicationContext the application context
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
}
