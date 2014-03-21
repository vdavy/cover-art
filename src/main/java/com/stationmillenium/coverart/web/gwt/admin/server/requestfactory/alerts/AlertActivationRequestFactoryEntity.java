/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.alerts;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.domain.alert.AlertActivation;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Virtual entity for the request factory process
 * @author vincent
 *
 */
@Service
public class AlertActivationRequestFactoryEntity {

	private static Mapper mapper;
	
	private AlertActivation alertActivation = new AlertActivation();
	
	/**
	 * Constructor with dozer mapper
	 * @param mapper dozer mapper
	 */
	@Autowired
	public AlertActivationRequestFactoryEntity(Mapper mapper) {
		AlertActivationRequestFactoryEntity.mapper = mapper;  
	}
	
	/**
	 * Defaut constructor
	 */
	public AlertActivationRequestFactoryEntity() {
		super();
	}	
	
	public Long getId() {
		return alertActivation.getId();
	}

	public void setId(Long id) {
		alertActivation.setId(id);		
	}
	
	public Integer getVersion() {
		return alertActivation.getVersion();
	}
	
	public void setVersion(Integer version) {
		alertActivation.setVersion(version);
	}

	public boolean isEnableAlert() {
        return alertActivation.isEnableAlert();
    }
    
    public void setEnableAlert(boolean enableAlert) {
    	alertActivation.setEnableAlert(enableAlert);
    }
    
    @NotNull(message = "{validation.notNull}")
    public AlertType getAlertType() {
        return alertActivation.getAlertType();
    }
    
    public void setAlertType(AlertType alertType) {
    	alertActivation.setAlertType(alertType);
    }

	/**
	 * List all entities
	 * @return list containing all entities
	 */
	public static List<AlertActivationRequestFactoryEntity> findAllAlertActivations() {
		List<AlertActivationRequestFactoryEntity> returnList = new ArrayList<>();
		for (AlertActivation alertActivation : AlertActivation.findAllAlertActivations())
			if (alertActivation != null)
				returnList.add(mapper.map(alertActivation, AlertActivationRequestFactoryEntity.class));
		
		return returnList;
	}
	
	/**
	 * Simple finder
	 * @param id the id of entity
	 * @return the found entity
	 */
	public static AlertActivationRequestFactoryEntity findAlertActivationRequestFactoryEntity(Long id) {
		AlertActivation alertActivation = AlertActivation.findAlertActivation(id);
		if (alertActivation != null)
			return mapper.map(alertActivation, AlertActivationRequestFactoryEntity.class);
		else 
			return null;
	}
	
	/**
	 * Process a merge of the current entity
	 */
	public void merge() {
		alertActivation.merge();
	}
	
}
