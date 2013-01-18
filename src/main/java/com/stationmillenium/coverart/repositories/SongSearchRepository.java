/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.dozer.Mapper;
import org.hibernate.search.MassIndexer;
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
	 * @return the song list found (empty list if nothing found)
	 */	
	public List<SongHistoryItemImageDTO> searchSongsByArtist(String artistName) {				
		return searchSongsWithFuzzyField(artistName, "artist");
	}
	
	/**
	 * Search song by titke across Lucene index
	 * @param titleName the title name to search for
	 * @return the song list found (empty list if nothing found)
	 */
	public List<SongHistoryItemImageDTO> searchSongsByTitle(String titleName) {
		return searchSongsWithFuzzyField(titleName, "title");
	}
	
	/**
	 * Search songs between times
	 * @param beginning beginning time for search
	 * @param ending ending time for search
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	public List<SongHistoryItemImageDTO> searchSongsByTime(Calendar beginning, Calendar ending) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		//make query
		Query query = queryBuilder.bool().
				must(queryBuilder.range()
						.onField("playedTimes.playedDate")
						.below(ending)
						.createQuery())
				.must(queryBuilder.range()
						.onField("playedTimes.playedDate")
						.above(beginning)
						.createQuery())
				.createQuery();
		
		//query
		List<SongHistoryItemImageDTO> resultList = processFullTextQuery(fullTextEntityManager, query);
		Collections.sort(resultList, new Comparator<SongHistoryItemImageDTO>() { //sort
			
			@Override
			public int compare(SongHistoryItemImageDTO o1, SongHistoryItemImageDTO o2) {
				if ((o1 != null) && (o2 != null))
					return o1.getSongHistoryItemDTO().getPlayedDate().compareTo(o2.getSongHistoryItemDTO().getPlayedDate());
				else
					return -1;
			}
			
		});
		
		//refilter to be sure
		List<SongHistoryItemImageDTO> resultFilteredList =  new ArrayList<>(); //ending list
		for (SongHistoryItemImageDTO song : resultList) { //for each found song
			if ((song.getSongHistoryItemDTO().getPlayedDate().after(beginning)) && (song.getSongHistoryItemDTO().getPlayedDate().before(ending))) //if in time range
					resultFilteredList.add(song);
		}
		
		return resultFilteredList;
	}
	
	/**
	 * Search songs by field with fuzzy query
	 * @param keywords the keywords to search for
	 * @param fieldName the field to search for into
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	private List<SongHistoryItemImageDTO> searchSongsWithFuzzyField(String keywords, String fieldName) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		//make query
		Query query = queryBuilder.keyword()
				.fuzzy()
				.withThreshold(0.8f)			
				.withPrefixLength(3)
				.onField(fieldName)
				.matching(keywords)
				.createQuery();
		
		return processFullTextQuery(fullTextEntityManager, query);
	}
	
	/**
	 * Search songs across Lucene index
	 * @param keywords the words to search for
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	public List<SongHistoryItemImageDTO> searchSongs(String keywords) {				
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		//make query
		Query query = queryBuilder.keyword()
				.onFields("artist", "title")
				.matching(keywords)
				.createQuery();
		
		return processFullTextQuery(fullTextEntityManager, query);
	}

	/**
	 * Process full text query
	 * @param fullTextEntityManager entity manager to execute query
	 * @param query the query to execute
	 * @param loadImage load the image
	 * @return the found entities
	 */
	private List<SongHistoryItemImageDTO> processFullTextQuery(FullTextEntityManager fullTextEntityManager, Query query) {
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, SongItem.class); //create query

		//search
		fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.FIND_BY_ID);
		@SuppressWarnings("unchecked")
		List<SongItem> songList = fullTextQuery.getResultList();

		//mapping
		List<SongHistoryItemImageDTO> resultsList = new ArrayList<>(); //return list
		for (SongItem song : songList) {
			for (SongHistory history : song.getPlayedTimes()) {
				//fill item
				SongHistoryItemImageDTO	item = new SongHistoryItemImageDTO();
				item.getSongHistoryItemDTO().setArtist(song.getArtist());
				item.getSongHistoryItemDTO().setTitle(song.getTitle());
				item.getSongHistoryItemDTO().setPlayedDate(history.getPlayedDate());
				
				//image
				SongHistoryImage image = song.getImage();
				if (image != null) {
					item.getSongImageDTO().setFileName(image.getFileName());
					item.getSongImageDTO().setHeight(image.getHeight());
					item.getSongImageDTO().setWidth(image.getWidth());
				}
				
				//add to return list
				resultsList.add(item);
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
		Query query = queryBuilder.keyword()
				.fuzzy()
				.onFields("artist", "title")
				.matching(keywords)
				.createQuery();
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
	
}
