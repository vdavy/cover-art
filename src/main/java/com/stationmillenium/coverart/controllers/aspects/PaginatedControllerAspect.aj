/**
 * 
 */
package com.stationmillenium.coverart.controllers.aspects;

import com.stationmillenium.coverart.controllers.interfaces.PaginatedControllerInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Ajouter les annotations sur les controllers de l'interface <code>PaginatedControllerInterface</code>
 * @author davyv
 *
 */
public aspect PaginatedControllerAspect {

	declare @type : PaginatedControllerInterface+ : @Controller;
	declare @type : PaginatedControllerInterface+ : @SessionAttributes({"paginationDTO"});
}
