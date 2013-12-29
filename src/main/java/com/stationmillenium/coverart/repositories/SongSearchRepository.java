/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.dozer.Mapper;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.domain.history.SongHistory;
import com.stationmillenium.coverart.domain.history.SongHistoryImage;
import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Repository for {@link SongHistory}}
 * @author vincent
 *
 */
@Repository
public class SongSearchRepository {

	//logger 
	private static final Logger LOGGER = LoggerFactory.getLogger(SongSearchRepository.class);
			
	private Future<?> indexerFuture;
	
	//full text entity manager for hibernate search
	@PersistenceContext
	private EntityManager entityManager;

	//dozer
	@Autowired
	private Mapper mapper;
	
	//config
	@Autowired
	private GeneralPropertiesBean config;
	
	/**
	 * Launch async indexing
	 */
	@Transactional
	public void indexAsync() {
		LOGGER.debug("Launch async indexing");
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		MassIndexer indexer = fullTextEntityManager.createIndexer(); //get indexer
		if (indexerFuture != null) {
			synchronized (indexerFuture) {
				indexerFuture = indexer.start();
			}		
		} else
			indexerFuture = indexer.start();
	}
	
	/**
	 * Is the current indexing is finished ?
	 * @return <code>true</code> if done, <code>false</code> if not
	 */
	public boolean isIndexingFinished() {
		if (indexerFuture != null) {
			synchronized (indexerFuture) {
				return indexerFuture.isDone(); 
			}
		}	else
			return true;
	}
	
	/**
	 * Search song by artist across Lucene index
	 * @param artistName the artist name to search for
	 * @param maxResults the max results count to return
	 * @return the song list found (empty list if nothing found)
	 */	
	public List<SongHistoryItemImageDTO> searchSongsByArtist(String artistName, int maxResults) {				
		return searchSongsWithFuzzyField(artistName, "artist", maxResults);
	}
	
	/**
	 * Search song by titke across Lucene index
	 * @param titleName the title name to search for
	 * @param maxResults the max results count to return
	 * @return the song list found (empty list if nothing found)
	 */
	public List<SongHistoryItemImageDTO> searchSongsByTitle(String titleName, int maxResults) {
		return searchSongsWithFuzzyField(titleName, "title", maxResults);
	}
	
	/**
	 * Search songs by field with fuzzy query
	 * @param keywords the keywords to search for
	 * @param fieldName the field to search for into
	 * @param maxResults the max results count to return
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	private List<SongHistoryItemImageDTO> searchSongsWithFuzzyField(String keywords, String fieldName, int maxResults) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		try  {
			//make query
			Query query = queryBuilder.keyword()
					.fuzzy()
					.withThreshold(0.8f)			
					.withPrefixLength(3)
					.onField(fieldName)
					.matching(keywords)
					.createQuery();

			return processFullTextQuery(fullTextEntityManager, query, maxResults);
			
		} catch(EmptyQueryException e) { //protect agasint empty query or reserved keyword
			LOGGER.warn("Empty query exception detected", e);
			return  new ArrayList<>(); 
		}
	}
	
	/**
	 * Search songs across Lucene index
	 * @param keywords the words to search for
	 * @param maxResults the max results count to return
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	public List<SongHistoryItemImageDTO> searchSongs(String keywords, int maxResults) {				
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		try {
			//make query
			Query query = queryBuilder.keyword()
					.onFields("artist", "title")
					.matching(keywords)
					.createQuery();
			
			return processFullTextQuery(fullTextEntityManager, query, maxResults);
			
		} catch(EmptyQueryException e) { //protect agasint empty query or reserved keyword
			LOGGER.warn("Empty query exception detected", e);
			return  new ArrayList<>(); 
		}
	}

	/**
	 * Process full text query
	 * @param fullTextEntityManager entity manager to execute query
	 * @param query the query to execute
	 * @param maxResults the max results count to return
	 * @return the found entities
	 */
	private List<SongHistoryItemImageDTO> processFullTextQuery(FullTextEntityManager fullTextEntityManager, Query query, int maxResults) {
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, SongItem.class); //create query
		fullTextQuery.setMaxResults(maxResults);
		
		//search
		fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID);
		@SuppressWarnings("unchecked")
		List<SongItem> songList = fullTextQuery.getResultList();

		//mapping
		List<SongHistoryItemImageDTO> resultsList = new ArrayList<>(); //return list
		for (SongItem song : songList) {
			int timesCount = 1;
			SongHistoryImage image = song.getImage(); //do not reload the image each time
			
			for (SongHistory history : song.getPlayedTimes()) {
				//fill item
				SongHistoryItemImageDTO	item = new SongHistoryItemImageDTO();
				item.getSongHistoryItemDTO().setArtist(song.getArtist());
				item.getSongHistoryItemDTO().setTitle(song.getTitle());
				item.getSongHistoryItemDTO().setPlayedDate(history.getPlayedDate());
				
				//image
				if (image != null) {
					item.getSongImageDTO().setFileName(image.getFileName());
					item.getSongImageDTO().setHeight(image.getHeight());
					item.getSongImageDTO().setWidth(image.getWidth());
				}
				
				//add to return list
				resultsList.add(item);	
				
				//do we reach the max time count? 
				if (timesCount < config.getHistoryMaxTimesCountInt()) //no, increase and continue
					timesCount++;
				else //stop and exit
					break;
				
			}
		}				
		
		return resultsList;
	}
	
	/**
	 * Search songs across Lucene index
	 * @param keywords the words to search for
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	public List<SongHistoryItemDTO> searchSongsForSuggest(String keywords, int maxResults) {				
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		//make query
		Query query = null;
		try {
			query = queryBuilder.keyword()
				.fuzzy()
				.onFields("artist", "title")
				.matching(keywords)
				.createQuery();
		} catch(EmptyQueryException e) { //protect agasint empty query or reserved keyword
			LOGGER.warn("Empty query exception detected", e);
			return  new ArrayList<>(); 
		}
		
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, SongItem.class); //create query
		fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID);
		if (maxResults > 0)
			fullTextQuery.setMaxResults(maxResults);
		
		//search
		@SuppressWarnings("unchecked")
		List<SongItem> songList = fullTextQuery.getResultList();

		//mapping
		List<SongHistoryItemDTO> resultsList = new ArrayList<>(); //return list
		for (SongItem song : songList) {
				SongHistoryItemDTO	item = mapper.map(song, SongHistoryItemDTO.class);
				resultsList.add(item);
		}			
		
		return resultsList;
	}
	
	/**
	 * Search songs for custom image - return songs not having already custom image
	 * @param keywords the keywords for search 
	 * @param maxResults the max results - 0 for not limit
	 * @param includeCustomImage <code>true</code> search songs with custom images, <code>false</code> search songs without
	 * @return the found songs
	 */
	@Transactional
	public List<SongHistoryItemImageDTO> searchSongsForCustomImage(String keywords, int maxResults, boolean includeCustomImage) {				
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		//make query
		Query query = null;
		try {
			query = queryBuilder.bool().should(
					queryBuilder.keyword().fuzzy()
					.onFields("artist", "title")
					.matching(keywords).createQuery())
				.must(
						queryBuilder.keyword().
						onField("customImage").
						matching(includeCustomImage).createQuery())
				.createQuery();
		} catch(EmptyQueryException e) { //protect agasint empty query or reserved keyword
			LOGGER.warn("Empty query exception detected", e);
			return  new ArrayList<>(); 
		}
		
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, SongItem.class); //create query
		fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID);
		if (maxResults > 0)
			fullTextQuery.setMaxResults(maxResults);
		
		//search
		@SuppressWarnings("unchecked")
		List<SongItem> songList = fullTextQuery.getResultList();

		//mapping
		List<SongHistoryItemImageDTO> resultsList = new ArrayList<>(); //return list
		for (SongItem song : songList) {
			if (song.isCustomImage() == includeCustomImage) { //add song to return list if match custom image requirement
				SongHistoryItemImageDTO	item = mapper.map(song, SongHistoryItemImageDTO.class);
				resultsList.add(item);
			}
		}			
		
		return resultsList;
	}
	
}
