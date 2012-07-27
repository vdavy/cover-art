/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans.impl;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;
import com.stationmillenium.coverart.configuration.beans.AbstractPropertiesBeanConfiguration;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Shoutcast server properties bean configuration
 * @author vincent
 *
 */
@Configuration
public class SongHistoryFilterPropertiesBeanConfiguration extends AbstractPropertiesBeanConfiguration<SongHistoryFilterPropertiesBean> {

	//local logger
	private static final Logger LOGGER = LoggerFactory.getLogger(SongHistoryFilterPropertiesBeanConfiguration.class);
	
	//property list
	@NotNull
	@Size(min = 5)
	private @Value("${songHistory.forbiddenKeywords}") String forbiddenKeywordsString;
	
	@NotNull
	@Pattern(regexp = "[\\d]+")
	private @Value("${songHistory.minimalLength}") String minimalLength;

	//instance variable
	private List<String> forbiddenKeywordsList;
	
	@Override
	protected SongHistoryFilterPropertiesBean buildBean() {
		SongHistoryFilterPropertiesBean propertiesBean = new SongHistoryFilterPropertiesBean();
		propertiesBean.setForbiddenKeywords(forbiddenKeywordsList);
		propertiesBean.setMinimalLength(new Integer(minimalLength));
		return propertiesBean;
	}
	
	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		try {
			//forbidden keywords list
			EvaluationContext context = new StandardEvaluationContext();
			ExpressionParser parser = new SpelExpressionParser();		
			forbiddenKeywordsList = (List<String>) parser.parseExpression(forbiddenKeywordsString).getValue(context);
		} catch (SpelEvaluationException e) {
			LOGGER.warn("Error during parsing forbidden keywords : " + forbiddenKeywordsString, e);
			throw new PropertyBeanException("forbiddenKeywordsString", e);
		}
	}
	
	/**
	 * Provide the produced bean
	 * @return the <code>SongHistoryFilterPropertiesBean</code>
	 */
	@Bean
	@Qualifier("songHistoryFilterPropertiesBean")
	public SongHistoryFilterPropertiesBean getSongHistoryFilterPropertiesBean() {
		return assembleBean();
	}
	
}
