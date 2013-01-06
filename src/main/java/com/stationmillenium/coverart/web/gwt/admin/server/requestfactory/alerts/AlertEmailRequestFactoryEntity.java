/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.alerts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.domain.alert.AlertEmail;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Virtual entity for the request factory process
 * @author vincent
 *
 */
@Service
public class AlertEmailRequestFactoryEntity {

	private static Mapper mapper;
	
	private AlertEmail alertEmail = new AlertEmail();
	
	/**
	 * Constructor with dozer mapper
	 * @param mapper dozer mapper
	 */
	@Autowired
	public AlertEmailRequestFactoryEntity(Mapper mapper) {
		AlertEmailRequestFactoryEntity.mapper = mapper;  
	}
	
	/**
	 * Defaut constructor
	 */
	public AlertEmailRequestFactoryEntity() {
		super();
	}	
	
	public Long getId() {
		return alertEmail.getId();
	}

	public void setId(Long id) {
		alertEmail.setId(id);		
	}
	
	public Integer getVersion() {
		return alertEmail.getVersion();
	}
	
	public void setVersion(Integer version) {
		alertEmail.setVersion(version);
	}

	@NotNull(message = "{validation.notNull}")
	@Pattern(flags = Flag.CASE_INSENSITIVE, regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", message = "{validation.wrongEmail}")
	public String getEmail() {
        return alertEmail.getEmail();
    }
	
    public void setEmail(String email) {
    	alertEmail.setEmail(email);
    }
    
    @NotNull(message = "{validation.notNull}")
    @Size(min = 1, max = 3, message = "{validaton.alertType.oneSelected}")
    public Set<AlertType> getAlertType() {
        return alertEmail.getAlertType();
    }
    
    public void setAlertType(Set<AlertType> alertType) {
    	alertEmail.setAlertType(alertType);
    }

	/**
	 * List all entities
	 * @return list containing all entities
	 */
	public static List<AlertEmailRequestFactoryEntity> findAllAlertEmails() {
		List<AlertEmailRequestFactoryEntity> returnList = new ArrayList<>();
		for (AlertEmail alertEmail : AlertEmail.findAllAlertEmails())
			if (alertEmail != null) //take care about null values
				returnList.add(mapper.map(alertEmail, AlertEmailRequestFactoryEntity.class));
		
		return returnList;
	}
	
	/**
	 * Simple finder
	 * @param id the id of entity
	 * @return the found entity
	 */
	public static AlertEmailRequestFactoryEntity findAlertEmailRequestFactoryEntity(Long id) {
		AlertEmail email = AlertEmail.findAlertEmail(id);
		if (email != null)
			return mapper.map(AlertEmail.findAlertEmail(id), AlertEmailRequestFactoryEntity.class);
		else 
			return null;
	}
	
	/**
	 * Process a merge of the current entity
	 */
	public void merge() {
		alertEmail.merge();
	}
	
	/**
	 * Process a persist of the current entity
	 */
	public void persist() {
		alertEmail.persist();
	}
	
	/**
	 * Process a remove of the current entity
	 */
	public void remove() {
		alertEmail.remove();
	}
	
}
