/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

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
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
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
		
		//remove needed part
		if (song.getPlayedTimes().size() > 0) {
			boolean removeSong = song.getPlayedTimes().size() == 1;
			SongHistory[] songHistories = new SongHistory[song.getPlayedTimes().size()];
			song.getPlayedTimes().toArray(songHistories);
			song.getPlayedTimes().remove(songHistories[0]);
			song.merge();
			songHistories[0].remove();
			if (removeSong) //if only one played time
				song.remove(); //remove the song
			
		} else
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
	private void addImageToSong(SongHistoryItemDTO songToAddTime, String fileName,  int width, int height, Provider provider, boolean customImage) {
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
		song.setCustomImage(customImage);
		song.merge();
	}
	
	/**
	 * Add an image to the song (non custom)
	 * @param songToAddTime the song to add image
	 * @param fileName the file name
	 * @param width the width
	 * @param height the height
	 * @param provider the image provider (see {@link Provider}
	 */
	@Transactional
	public void addImageToSong(SongHistoryItemDTO songToAddTime, String fileName,  int width, int height, Provider provider) {
		addImageToSong(songToAddTime, fileName, width, height, provider, false);
	}
	
	/**
	 * Add an custom image to the song
	 * @param songToAddTime the song to add image
	 * @param fileName the file name
	 * @param width the width
	 * @param height the height
	 */
	@Transactional
	public void addCustomImageToSong(SongHistoryItemDTO songToAddTime, String fileName,  int width, int height) {
		addImageToSong(songToAddTime, fileName, width, height, Provider.CUSTOM, true);
	}
	
	/**
	 * Get the last 5 played songs
	 * @param displayLastSong display last song
	 * @return the list of {@link SongHistoryItemDTO}
	 */
	public List<SongHistoryItemDTO> getLast5PlayedSongs(boolean displayLastSong) {
		//query db
		Query query = entityManager.createNamedQuery("getSongsOrderedByPlayedTime", SongItem.class); //create query
		query.setMaxResults(5);
		if (!displayLastSong) //if needed shift first result
			query.setFirstResult(1);
		@SuppressWarnings("unchecked")
		List<SongItem> lastSongsList = query.getResultList();

		//convert
		List<SongHistoryItemDTO> resultSongsList = new ArrayList<>();
		for (SongItem songItem : lastSongsList) {
			SongHistoryItemDTO songHistoryItem = mapper.map(songItem, SongHistoryItemDTO.class); //process mapping to output dto
			resultSongsList.add(songHistoryItem);
		}

		return resultSongsList; 
	}
	
	/**
	 * Get the last played songs with played date and image 
	 * Limit search to a min date
	 * @param minDate the min date for song extract
	 * @return the list of {@link SongHistoryItemImageDTO}
	 */
	public List<SongHistoryItemImageDTO> getLastSongsFromMinDate(Calendar minDate) {
		//query db
		Query query = entityManager.createNamedQuery("getSongsFetchedOrderedByPlayedTimeWithMinDate", SongHistory.class); //create query
		query.setParameter("minDate", minDate);
		@SuppressWarnings("unchecked")
		List<SongHistory> songHistoryList = query.getResultList();
		
		//convert to result list
		List<SongHistoryItemImageDTO> resultList = new ArrayList<>();
		for (SongHistory song : songHistoryList) {
			SongHistoryItemImageDTO resultSong = mapper.map(song, SongHistoryItemImageDTO.class);
			resultList.add(resultSong);
		}
		
		return resultList;
	}
	
	/**
	 * Get songs played between 2 dates
	 * @param minDate the min date (excluding)
	 * @param maxDate the max date (excluding)
	 * @return the list of {@link SongHistoryItemDTO}
	 */
	public List<SongHistoryItemDTO> getSongsPlayedBetween2Dates(Calendar minDate, Calendar maxDate) {
		//prepare query
		Query query = entityManager.createNamedQuery("getSongsFetchedOrderedByPlayedTimeBetween2Dates", SongHistory.class); //create query
		query.setParameter("minDate", minDate);
		query.setParameter("maxDate", maxDate);
		@SuppressWarnings("unchecked")
		List<SongHistory> songHistoryList = query.getResultList();
		
		//convert
		List<SongHistoryItemDTO> returnList = new ArrayList<>();
		for (SongHistory songItem : songHistoryList) {
			SongHistoryItemDTO songHistoryItem = mapper.map(songItem, SongHistoryItemDTO.class); //process mapping to output dto
			returnList.add(songHistoryItem);
		}
				
		return returnList;
	}
	
	/**
	 * Get songs played between 2 dates, with images
	 * @param minDate the min date (excluding)
	 * @param maxDate the max date (excluding)
	 * @return the list of {@link SongHistoryItemDTO}
	 */
	public List<SongHistoryItemImageDTO> getSongsPlayedBetween2DatesWithImages(Calendar minDate, Calendar maxDate) {
		//prepare query
		Query query = entityManager.createNamedQuery("getSongsFetchedOrderedWithImageByPlayedTimeBetween2Dates", SongHistory.class); //create query
		query.setParameter("minDate", minDate);
		query.setParameter("maxDate", maxDate);
		@SuppressWarnings("unchecked")
		List<SongHistory> songHistoryList = query.getResultList();
		
		//convert
		List<SongHistoryItemImageDTO> returnList = new ArrayList<>();
		for (SongHistory songItem : songHistoryList) {
			SongHistoryItemImageDTO songHistoryItem = mapper.map(songItem, SongHistoryItemImageDTO.class); //process mapping to output dto
			returnList.add(songHistoryItem);
		}
				
		return returnList;
	}
	
	/**
	 * Get all the songs with custom images - images are fetched
	 * @return the list of {@link SongHistoryItemImageDTO}
	 */
	public List<SongHistoryItemImageDTO>  getSongsWithCustomImages() {
		Query query = entityManager.createNamedQuery("getSongsWithCustomFetchedImage", SongItem.class); //create query
		@SuppressWarnings("unchecked")
		List<SongItem> songList = query.getResultList();
		
		//convert
		List<SongHistoryItemImageDTO> returnList = new ArrayList<>();
		for (SongItem songItem : songList) {
			SongHistoryItemImageDTO songHistoryItem = mapper.map(songItem, SongHistoryItemImageDTO.class); //process mapping to output dto
			returnList.add(songHistoryItem);
		}
		
		return returnList;
	}
	
	/**
	 * Set a song as a song with custom image
	 * @param artist the artist of the song
	 * @param title the title of the song
	 */
	public void setSongAsCustomImageSong(String artist, String title) {
		//load song
		Query query = entityManager.createNamedQuery("loadExistingSong"); //create query
		query.setParameter("artist", artist); //artist param
		query.setParameter("title", title); //title param
		SongItem song  = (SongItem) query.getSingleResult(); //Execute query and get count
				
		//set as custom image song
		song.setCustomImage(true);
		song.merge();
	}
		
}
