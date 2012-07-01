/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.stationmillenium.coverart.beans.PaginationPropertiesBean;
import com.stationmillenium.coverart.beans.PaginationPropertiesBean.PaginationList;

/**
 * Classe de configuration des données pour la pagination
 * @author vincent
 *
 */
@Configuration
public class PaginationBeanConfiguration {

	//liste des options de pagination
	private @Value("${pagination.listOfOptions}") String paginationOptions; 
	
	//liste par defaut pour les utilisateurs
	private @Value("${pagination.defaultUserList}") int defaultUserList; 
	
	//liste par defaut pour les streams
	private @Value("${pagination.defaultStreamList}") int defaultStreamList; 
	
	//liste par defaut pour les visites vip
	private @Value("${pagination.defaultVIPVisitorList}") int defaultVIPVisitorList; 
		
	/**
	 * Mettre en place le bean des propriétés de la pagination
	 * @return le bean de configuration de la pagination
	 */
	@Bean
	@Qualifier("paginationBean")
	@Scope("singleton")
	public PaginationPropertiesBean getPaginationBean() {
		PaginationPropertiesBean paginationBean = new PaginationPropertiesBean();
		ExpressionParser expressionParser = new SpelExpressionParser();		
		List<Integer> listOfInt = (List<Integer>) expressionParser.parseExpression(paginationOptions).getValue(List.class);
		paginationBean.setPaginationOptions(listOfInt);
		
		//faire un tableau avec toutes les variables
		int[] defaultValues = getDefaultValues();
		
		//pagination par defaut
		paginationBean.setDefaultPagination(setupDefaultPaginationProperties(defaultValues)); //mettre en place la map
		
		return paginationBean;
	}
	
	/**
	 * Remplir la map des valeurs par defaut en fonction des types de pagination
	 * @param defaultValues la liste des valeurs
	 * @return la map remplie
	 */
	private Map<PaginationList, Integer> setupDefaultPaginationProperties(int[] defaultValues) {
		Map<PaginationList, Integer> defaultPaginationProperties = new HashMap<PaginationList, Integer>(); //definition de la map
		
		int i = 0; //compteur pour indexation 
		for (PaginationList pagination : PaginationList.values())  //pour chaque pagination
			defaultPaginationProperties.put(pagination, defaultValues[i++]); //ajouter la propriété
				
		return defaultPaginationProperties; //renvoyer la map
	}
	
	/**
	 * Obtenir les valeurs par defaut de pagination sous forme de tableau
	 * @return le tableau des valeurs
	 */
	private int[] getDefaultValues() {
		EvaluationContext context = new StandardEvaluationContext(); //mise en place du parseur et expression
		Expression expression = new SpelExpressionParser().parseExpression("new int[]{#defaultUserList, #defaultStreamList, #defaultVIPVisitorList}");
		context.setVariable("defaultUserList", defaultUserList); //definition des variables
		context.setVariable("defaultStreamList", defaultStreamList);
		context.setVariable("defaultVIPVisitorList",defaultVIPVisitorList);
		
		int[] defaultValues = (int[]) expression.getValue(context); //evaluation
		return defaultValues; //retourner la valeur
	}
}
