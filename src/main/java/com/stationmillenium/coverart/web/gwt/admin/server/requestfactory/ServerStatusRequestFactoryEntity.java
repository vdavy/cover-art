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

import com.stationmillenium.coverart.domain.statuses.ServerStatus;

/**
 * Virtual entity for the request factory process
 * @author vincent
 *
 */
@Service
public class ServerStatusRequestFactoryEntity {

	private static Mapper mapper;
	
	private ServerStatus serverStatus = new ServerStatus();
	
	/**
	 * Constructor with dozer mapper
	 * @param mapper dozer mapper
	 */
	@Autowired
	public ServerStatusRequestFactoryEntity(Mapper mapper) {
		ServerStatusRequestFactoryEntity.mapper = mapper;  
	}
	
	/**
	 * Defaut constructor
	 */
	public ServerStatusRequestFactoryEntity() {
		super();
	}
	
	public Long getId() {
		return serverStatus.getId();
	}

	public void setId(Long id) {
		serverStatus.setId(id);		
	}

	public Integer getVersion() {
		return serverStatus.getVersion();
	}
	
	public void setVersion(Integer version) {
		serverStatus.setVersion(version);
	}
	
	public Date getDateOfChange() {
		return serverStatus.getDateOfChange();
	}

	public void setDateOfChange(Date dateOfChange) {
		serverStatus.setDateOfChange(dateOfChange);
	}

	public boolean isServerUp() {
		return serverStatus.isServerUp();
	}

	public void setServerUp(boolean serverUp) {
		serverStatus.setServerUp(serverUp)	;
	}

	/**
	 * List all entities
	 * @return list containing all entities
	 */
	public static List<ServerStatusRequestFactoryEntity> findAllServerStatuses() {
		List<ServerStatusRequestFactoryEntity> returnList = new ArrayList<>();
		for (ServerStatus status : ServerStatus.findAllServerStatuses())
			returnList.add(mapper.map(status, ServerStatusRequestFactoryEntity.class));
		
		return returnList;
	}
	
	/**
	 * Simple finder
	 * @param id the id of entity
	 * @return the found entity
	 */
	public static ServerStatusRequestFactoryEntity findServerStatusRequestFactoryEntity(Long id) {
		return mapper.map(ServerStatus.findServerStatus(id), ServerStatusRequestFactoryEntity.class);
	}
	
}
