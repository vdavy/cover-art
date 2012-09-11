/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.domain.history.SongHistory;
import com.stationmillenium.coverart.domain.history.SongHistoryImage;
import com.stationmillenium.coverart.domain.history.SongHistoryImage.Provider;
import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Repository for {@link SongHistory}}
 * @author vincent
 *
 */
@Repository
public class SongItemRepository {

	//logger 
	private static final Logger LOGGER = LoggerFactory.getLogger(SongItemRepository.class);
			
	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;
	
	//the dozer mapper
	@Autowired
	private Mapper mapper;

	/**
	 * Get the last recorded song in the DB
	 * @return the {@link SongHistoryItemDTO} filled with last recorded song data - empty DTO if not found
	 */
	public SongHistoryItemDTO getLastSongHistoryItem() {
		try {
			Query query = entityManager.createNamedQuery("getLastSong"); //create query
			SongItem song = (SongItem) query.getSingleResult(); //Execute query and get entity
			SongHistoryItemDTO songHistoryItem = mapper.map(song, SongHistoryItemDTO.class); //process mapping to output dto
			songHistoryItem.setPlayedDate(((SongHistory) song.getPlayedTimes().toArray()[0]).getPlayedDate()); //process date
			return songHistoryItem; //return dto
			
		} catch (EmptyResultDataAccessException e) { //if not found
			LOGGER.warn("No entity found", e);
			return new SongHistoryItemDTO(); //return empty DTO
		}
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
		songHistory.persist(); //persist time
		songItemEntity.persist(); //persist song
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
	 * Add time to existing song
	 * @param songToAddTime the song with time to add
	 */
	@Transactional
	public void addTimeToExistingSong(SongHistoryItemDTO songToAddTime) {
		//load song
		Query query = entityManager.createNamedQuery("loadExistingSong"); //create query
		query.setParameter("artist", songToAddTime.getArtist()); //artist param
		query.setParameter("title", songToAddTime.getTitle()); //title param
		SongItem song  = (SongItem) query.getSingleResult(); //Execute query and get count
		
		//add time
		SongHistory songHistory = new SongHistory();
		songHistory.setPlayedDate(songToAddTime.getPlayedDate());
		songHistory.setSong(song);
		songHistory.persist(); //persit add
	}
	
	/**
	 * Add an image to the song
	 * @param songToAddTime the song to add image
	 * @param fileName the file name
	 * @param width the width
	 * @param height the height
	 * @param provider the image provider (see {@link Provider}
	 */
	@Transactional
	public void addImageToSong(SongHistoryItemDTO songToAddTime, String fileName,  int width, int height, Provider provider) {
		//load song
		Query query = entityManager.createNamedQuery("loadExistingSong"); //create query
		query.setParameter("artist", songToAddTime.getArtist()); //artist param
		query.setParameter("title", songToAddTime.getTitle()); //title param
		SongItem song  = (SongItem) query.getSingleResult(); //Execute query and get count
		
		//new image
		SongHistoryImage songHistoryImage = new SongHistoryImage(); //create entity
		songHistoryImage.setWidth(width); //set width
		songHistoryImage.setHeight(height); //set height
		songHistoryImage.setFileName(fileName); //set file name
		songHistoryImage.setProvider(provider); //set image provider
		songHistoryImage.persist();
		
		//link to song
		song.setImage(songHistoryImage);
		song.merge();
	}
}
