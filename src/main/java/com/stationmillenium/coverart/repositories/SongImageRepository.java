/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.domain.history.SongHistoryImage;
import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;

/**
 * Repository for {@link SongHistoryImage}}
 * @author vincent
 *
 */
@Repository
public class SongImageRepository {

	//logger 
	private static final Logger LOGGER = LoggerFactory.getLogger(SongImageRepository.class);

	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;

	//the dozer mapper
	@Autowired
	private Mapper mapper;

	/**
	 * Search image for a song
	 * @param songToSearch the song to search for image
	 * @return the {@link SongImageDTO} if found, null otherwise
	 */
	public SongImageDTO getImageForSong(SongHistoryItemDTO songToSearch) {
		//load song
		Query query = entityManager.createNamedQuery("getImageForSongWithCache", SongHistoryImage.class); //create query
		query.setParameter("artist", songToSearch.getArtist()); //artist param
		query.setParameter("title", songToSearch.getTitle()); //title param
		@SuppressWarnings("unchecked")
		List<SongHistoryImage> imageEntityList = (List<SongHistoryImage>) query.getResultList(); //Execute query 

		if (imageEntityList.size() > 0) { //image found
			SongImageDTO image = mapper.map(imageEntityList.get(0), SongImageDTO.class); //conversion
			LOGGER.debug("Found image for " + songToSearch + " : " + image);
			return image;
		} else { //no image found
			LOGGER.debug("No image found for " + songToSearch);
			return null;
		}
	}

	/**
	 * Get all the songs with image associated (if image exists)
	 * @return the list of {@link SongHistoryItemImageDTO}
	 */
	public List<SongHistoryItemImageDTO> getAllSongsWithImage() {
		//load entities
		Query query = entityManager.createNamedQuery("getAllSongsWithImage", SongItem.class);
		@SuppressWarnings("unchecked")
		List<SongItem> entityList = (List<SongItem>) query.getResultList(); //Execute query 

		//convert to return type
		List<SongHistoryItemImageDTO> songHistoryItemImageDTOs = new ArrayList<>();
		for (SongItem song : entityList) {
			SongHistoryItemImageDTO songHistoryItemImageDTO = mapper.map(song, SongHistoryItemImageDTO.class);
			songHistoryItemImageDTOs.add(songHistoryItemImageDTO);
		}

		return songHistoryItemImageDTOs;
	}

	/**
	 * Delete image of a song
	 * @param songToDeleteImage the song to delete image
	 */
	@Transactional
	public void deleteImageOfSong(SongHistoryItemDTO songToDeleteImage) {
		//load song
		Query query = entityManager.createNamedQuery("getSongWithImage", SongItem.class); //create query
		query.setParameter("artist", songToDeleteImage.getArtist()); //artist param
		query.setParameter("title", songToDeleteImage.getTitle()); //title param
		SongItem song = (SongItem) query.getSingleResult();

		//delete image
		SongHistoryImage image = song.getImage();
		if (image != null) {
			song.setImage(null);
			song.merge();		
			image.remove();			
		}
	}

	/**
	 * Remove the custom image and attribute of a song (even if there is no image attached
	 * @param songToRemoveCustomImage the song to remove custom image
	 */
	@Transactional
	public void cancelCustomImage(SongHistoryItemDTO songToRemoveCustomImage) {
		//load song
		Query query = entityManager.createNamedQuery("loadExistingSong", SongItem.class); //create query
		query.setParameter("artist", songToRemoveCustomImage.getArtist()); //artist param
		query.setParameter("title", songToRemoveCustomImage.getTitle()); //title param
		SongItem song = (SongItem) query.getSingleResult();
		
		//delete image
		SongHistoryImage image = song.getImage();
		if (image != null) { //if there is an image
			song.setImage(null);
			song.setCustomImage(false);
			song.merge();		
			image.remove();			
		} else {
			song.setCustomImage(false);
			song.merge();	
		}
	}
	
	/**
	 * Update the custom image of song
	 * @param songToUpdateCustomImage the song to update
	 * @param fileName the file name of the new image
	 * @param width the width of the new image
	 * @param height the height of the new image
	 */
	@Transactional
	public void updateCustomImage(SongHistoryItemDTO songToUpdateCustomImage, String fileName,  int width, int height) {
		//load song
		Query query = entityManager.createNamedQuery("getSongWithImage", SongItem.class); //create query
		query.setParameter("artist", songToUpdateCustomImage.getArtist()); //artist param
		query.setParameter("title", songToUpdateCustomImage.getTitle()); //title param
		SongItem song = (SongItem) query.getSingleResult();
		
		//delete image
		SongHistoryImage image = song.getImage();
		if (image != null) { //if there is an image
			image.setFileName(fileName);
			image.setWidth(width);
			image.setHeight(height);
			image.merge();
		}
	}
	
	/**
	 * A song has an image ?
	 * @param song the song to analyse
	 * @return <code>true</code> if there is an image, <code>false</code> if not
	 */
	public boolean isSongHasImage(SongHistoryItemDTO song) {
		//load song
		Query query = entityManager.createNamedQuery("getSongWithImageLeftJoinFetch", SongItem.class); //create query
		query.setParameter("artist", song.getArtist()); //artist param
		query.setParameter("title", song.getTitle()); //title param
		SongItem songItem = (SongItem) query.getSingleResult();
		
		//return data
		return songItem.getImage() != null;
	}

	/**
	 * Get the file name of a song
	 * @param song the song to analyse
	 * @return the file name
	 */
	public String getImageFileNameOfSong(SongHistoryItemDTO song) {
		//load song
		Query query = entityManager.createNamedQuery("getImageFileNameOfSong", String.class); //create query
		query.setParameter("artist", song.getArtist()); //artist param
		query.setParameter("title", song.getTitle()); //title param
		String fileName = (String) query.getSingleResult();
		
		//return data
		return fileName;
	}
}
