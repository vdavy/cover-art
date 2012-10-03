/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.domain.history.SongHistory;
import com.stationmillenium.coverart.domain.history.SongItem;
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
			
	//full text entity manager for hibernate search
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Launch indexing
	 * @throws InterruptedException
	 */
	@Transactional
	public void index() throws InterruptedException {
		LOGGER.debug("Launch indexing");
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		MassIndexer indexer = fullTextEntityManager.createIndexer(); //get indexer
		indexer.startAndWait(); //index 
		LOGGER.debug("Indexing finished");
	}
	
	/**
	 * Search song by artist across Lucene index
	 * @param artistName the artist name to search for
	 * @return the song list found (empty list if nothing found)
	 */	
	public List<SongHistoryItemDTO> searchSongsByArtist(String artistName) {				
		return searchSongsWithFuzzyField(artistName, "artist");
	}
	
	/**
	 * Search song by titke across Lucene index
	 * @param titleName the title name to search for
	 * @return the song list found (empty list if nothing found)
	 */
	public List<SongHistoryItemDTO> searchSongsByTitle(String titleName) {
		return searchSongsWithFuzzyField(titleName, "title");
	}
	
	/**
	 * Search songs between times
	 * @param beginning beginning time for search
	 * @param ending ending time for search
	 * @return the song list found (empty list if nothing found)
	 */
	@Transactional
	public List<SongHistoryItemDTO> searchSongsByTime(Calendar beginning, Calendar ending) {
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
		List<SongHistoryItemDTO> resultList = processFullTextQuery(fullTextEntityManager, query);
		Collections.sort(resultList, new Comparator<SongHistoryItemDTO>() { //sort
			
			@Override
			public int compare(SongHistoryItemDTO o1, SongHistoryItemDTO o2) {
				if ((o1 != null) && (o2 != null))
					return o1.getPlayedDate().compareTo(o2.getPlayedDate());
				else
					return -1;
			}
			
		});
		
		//refilter to be sure
		List<SongHistoryItemDTO> resultFilteredList =  new ArrayList<>(); //ending list
		for (SongHistoryItemDTO song : resultList) { //for each found song
			if ((song.getPlayedDate().after(beginning)) && (song.getPlayedDate().before(ending))) //if in time range
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
	private List<SongHistoryItemDTO> searchSongsWithFuzzyField(String keywords, String fieldName) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager); //get full text entity manager
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(SongItem.class).get(); //query builder

		//make query
		Query query = queryBuilder.keyword()
				.fuzzy()
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
	public List<SongHistoryItemDTO> searchSongs(String keywords) {				
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
	 * @return the found entities
	 */
	private List<SongHistoryItemDTO> processFullTextQuery(FullTextEntityManager fullTextEntityManager, Query query) {
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, SongItem.class); //create query

		//search
		@SuppressWarnings("unchecked")
		List<SongItem> songList = fullTextQuery.getResultList();

		//mapping
		List<SongHistoryItemDTO> resultsList = new ArrayList<>(); //return list
		for (SongItem song : songList) {
			for (SongHistory history : song.getPlayedTimes()) {
				//fill item
				SongHistoryItemDTO	item = new SongHistoryItemDTO();
				item.setArtist(song.getArtist());
				item.setTitle(song.getTitle());
				item.setPlayedDate(history.getPlayedDate());

				//add to return list
				resultsList.add(item);
			}
		}				
		
		return resultsList;
	}
	
}
