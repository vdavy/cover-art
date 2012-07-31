/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.domain.SongHistory;
import com.stationmillenium.coverart.domain.SongItem;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Repository for {@link SongHistory}}
 * @author vincent
 *
 */
@Repository
public class SongItemRepository {

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
		SongItem song = (SongItem) query.getSingleResult(); //Execute query and get entity
		SongHistoryItemDTO songHistoryItem = mapper.map(song, SongHistoryItemDTO.class); //process mapping to output dto
		songHistoryItem.setPlayedDate(((SongHistory) song.getPlayedTimes().toArray()[0]).getPlayedDate()); //process date
		return songHistoryItem; //return dto
	}
	
	/**
	 * Remove the last recored song
	 */
	public void deleteLastRecordedSong() {
		Query query = entityManager.createNamedQuery("getLastSong"); //create query
		SongItem song = (SongItem) query.getSingleResult(); //Execute query and get entity
		song.remove(); //remove the song
	}
	
	/**
	 * Insert a {@link SongHistoryItemDTO} list
	 * @param songToInsert {@link SongHistoryItemDTO} to insert
	 */
	@Transactional
	public void insertSongHistory(SongHistoryItemDTO songToInsert) {
		SongItem songItemEntity = mapper.map(songToInsert, SongItem.class); //convert into entity
		SongHistory songHistory = new SongHistory();
		songHistory.setPlayedDate(songToInsert.getPlayedDate()); //played date			
		songItemEntity.setPlayedTimes(new HashSet<SongHistory>());

		//link history and item
		songItemEntity.getPlayedTimes().add(songHistory);
		songHistory.setSong(songItemEntity); 		
		songItemEntity.persist(); //persist
	}
	
	/**
	 * Check if a song exists
	 * @param songToCheck the song to check
	 * @return <code>true</code> if found, <code>false</code> if not found
	 */
	public boolean isExistingSong(SongHistoryItemDTO songToCheck) {
		Query query = entityManager.createNamedQuery("checkExistingSong"); //create query
		query.setParameter("artist", songToCheck.getArtist()); //artist param
		query.setParameter("title", songToCheck.getTitle()); //title param
		Long songCount = (Long) query.getSingleResult(); //Execute query and get count
		return (songCount != 0);
	}
	
	/**
	 * Check if a song exists
	 * @param songToCheck the song to check
	 * @return <code>true</code> if found, <code>false</code> if not found
	 */
	public void addTimeToExistingSong(SongHistoryItemDTO songToCheck) {
		//load song
		Query query = entityManager.createNamedQuery("loadExistingSong"); //create query
		query.setParameter("artist", songToCheck.getArtist()); //artist param
		query.setParameter("title", songToCheck.getTitle()); //title param
		SongItem song  = (SongItem) query.getSingleResult(); //Execute query and get count
		
		//add time
		SongHistory songHistory = new SongHistory();
		songHistory.setPlayedDate(songToCheck.getPlayedDate());
		songHistory.setSong(song);
		songHistory.persist(); //persit add
	}
}
