/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

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
	 * Assemble the bean : check values and make it
	 * @return the bean
	 */	
	protected T assembleBean() {
		try {
			doBeanValidation(this);
			propertyCustomChecker(); //custom values
			return buildBean();
		} catch (PropertyBeanException e) { //if error
			LOGGER.error("Error during property parsing - app starting stopped", e);
			((AbstractApplicationContext) context).close(); //unload context (stop starting)
			return null; //nothing to return
		}
	}
	
	/**
	 * Do bean validation
	 * @param objectToValidate object to validate
	 * @throws PropertyBeanException if validation error is found
	 */
	private <V>  void doBeanValidation(V objectToValidate) throws PropertyBeanException {
		//process validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<V>> constraintViolations = validator.validate(objectToValidate);
		
		//process errors
		List<String> errorList = new ArrayList<>();
		String beanName = null;
		for (ConstraintViolation<V> constraint : constraintViolations) {
			String error = constraint.getPropertyPath() + " : " + constraint.getMessage()  + " (invalid value : " + constraint.getInvalidValue() +")";
			errorList.add(error);
			
			if (beanName == null) //save bean name
				beanName = constraint.getRootBeanClass().getName();
		}
		
		if (errorList.size() > 0) //if some errors found
			throw new PropertyBeanException(beanName, errorList.toString());
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
