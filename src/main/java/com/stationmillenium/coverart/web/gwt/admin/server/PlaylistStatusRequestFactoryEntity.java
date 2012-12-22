/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.domain.statuses.PlaylistStatus;

/**
 * Virtual entity for the request factory process
 * @author vincent
 *
 */
@Service
public class PlaylistStatusRequestFactoryEntity {

	private static Mapper mapper;
	
	private PlaylistStatus playlistStatus = new PlaylistStatus();
	
	/**
	 * Constructor with dozer mapper
	 * @param mapper dozer mapper
	 */
	@Autowired
	public PlaylistStatusRequestFactoryEntity(Mapper mapper) {
		PlaylistStatusRequestFactoryEntity.mapper = mapper;  
	}
	
	/**
	 * Defaut constructor
	 */
	public PlaylistStatusRequestFactoryEntity() {
		super();
	}	
	
	public Long getId() {
		return playlistStatus.getId();
	}

	public void setId(Long id) {
		playlistStatus.setId(id);		
	}

	public Integer getVersion() {
		return playlistStatus.getVersion();
	}
	
	public void setVersion(Integer version) {
		playlistStatus.setVersion(version);
	}
	
	public Date getDateOfChange() {
		return playlistStatus.getDateOfChange();
	}

	public void setDateOfChange(Date dateOfChange) {
		playlistStatus.setDateOfChange(dateOfChange);
	}

	public boolean isPlaylistUpdated() {
		return playlistStatus.isPlaylistUpdated();		
	}

	public void setPlaylistUpdated(boolean playlistUpdated) {
		playlistStatus.setPlaylistUpdated(playlistUpdated);
	}

	/**
	 * List all entities
	 * @return list containing all entities
	 */
	public static List<PlaylistStatusRequestFactoryEntity> findAllPlaylistStatuses() {
		List<PlaylistStatusRequestFactoryEntity> returnList = new ArrayList<>();
		for (PlaylistStatus status : PlaylistStatus.findAllPlaylistStatuses())
			returnList.add(mapper.map(status, PlaylistStatusRequestFactoryEntity.class));
		
		return returnList;
	}
	
	/**
	 * Simple finder
	 * @param id the id of entity
	 * @return the found entity
	 */
	public static PlaylistStatusRequestFactoryEntity findPlaylistStatusRequestFactoryEntity(Long id) {
		return mapper.map(PlaylistStatus.findPlaylistStatus(id), PlaylistStatusRequestFactoryEntity.class);
	}
	
}
