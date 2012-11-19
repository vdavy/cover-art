/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stationmillenium.coverart.domain.history.SongHistoryImage;
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
		Query query = entityManager.createNamedQuery("getImageForSong", SongHistoryImage.class); //create query
		query.setParameter("artist", songToSearch.getArtist()); //artist param
		query.setParameter("title", songToSearch.getTitle()); //title param
		query.setParameter("calendar", songToSearch.getPlayedDate()); //calendar param
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
}
