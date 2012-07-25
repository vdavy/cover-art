/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.domain.SongHistory;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Repository for {@link SongHistory}}
 * @author vincent
 *
 */
@Repository
public class SongHistoryRepository {

	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;
	
	//the dozer mapper
	@Autowired
	private Mapper mapper;

	/**
	 * Get the last recorded song in the DB
	 * @return the {@link SongHistoryItemDTO} filled with last recorded song data
	 */
	public SongHistoryItemDTO getLastSongHistoryItem() {
		Query query = entityManager.createNamedQuery("getLastSong"); //create query
		SongHistory song = (SongHistory) query.getSingleResult(); //Execute query and get entity
		SongHistoryItemDTO songHistoryItem = mapper.map(song, SongHistoryItemDTO.class); //process mapping to output dto
		return songHistoryItem; //return dto
	}
	
	/**
	 * Remove the last recored song
	 */
	public void deleteLastRecordedSong() {
		Query query = entityManager.createNamedQuery("getLastSong"); //create query
		SongHistory song = (SongHistory) query.getSingleResult(); //Execute query and get entity
		song.remove(); //remove the song
	}
	
	/**
	 * Insert a {@link SongHistoryItemDTO} list
	 * @param listToInsert the list of {@link SongHistoryItemDTO} to insert
	 */
	@Transactional
	public void insertSongHistoryList(List<SongHistoryItemDTO> listToInsert) {
		for (SongHistoryItemDTO song : listToInsert) { //for each song
			SongHistory songHistoryEntity = mapper.map(song, SongHistory.class); //convert into entity
			songHistoryEntity.persist(); //persist
		}
	}
}
