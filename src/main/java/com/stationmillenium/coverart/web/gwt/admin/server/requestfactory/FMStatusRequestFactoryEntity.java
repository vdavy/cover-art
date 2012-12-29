/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server.requestfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.domain.statuses.FMStatus;

/**
 * Virtual entity for the request factory process
 * @author vincent
 *
 */
@Service
public class FMStatusRequestFactoryEntity {

	private static Mapper mapper;
	
	private FMStatus fmStatus = new FMStatus();
	
	/**
	 * Constructor with dozer mapper
	 * @param mapper dozer mapper
	 */
	@Autowired
	public FMStatusRequestFactoryEntity(Mapper mapper) {
		FMStatusRequestFactoryEntity.mapper = mapper;  
	}
	
	/**
	 * Defaut constructor
	 */
	public FMStatusRequestFactoryEntity() {
		super();
	}	
	
	public Long getId() {
		return fmStatus.getId();
	}

	public void setId(Long id) {
		fmStatus.setId(id);		
	}
	
	public Integer getVersion() {
		return fmStatus.getVersion();
	}
	
	public void setVersion(Integer version) {
		fmStatus.setVersion(version);
	}

	public Date getDateOfChange() {
		return fmStatus.getDateOfChange();
	}

	public void setDateOfChange(Date dateOfChange) {
		fmStatus.setDateOfChange(dateOfChange);
	}

	public boolean isFmUp() {
		return fmStatus.isFmUp();		
	}

	public void setFmUp(boolean fmUp) {
		fmStatus.setFmUp(fmUp);		
	}

	/**
	 * List all entities
	 * @return list containing all entities
	 */
	public static List<FMStatusRequestFactoryEntity> findAllFMStatuses() {
		List<FMStatusRequestFactoryEntity> returnList = new ArrayList<>();
		for (FMStatus status : FMStatus.findAllFMStatuses())
			returnList.add(mapper.map(status, FMStatusRequestFactoryEntity.class));
		
		return returnList;
	}
	
	/**
	 * Simple finder
	 * @param id the id of entity
	 * @return the found entity
	 */
	public static FMStatusRequestFactoryEntity findFMStatusRequestFactoryEntity(Long id) {
		return mapper.map(FMStatus.findFMStatus(id), FMStatusRequestFactoryEntity.class);
	}
	
}
