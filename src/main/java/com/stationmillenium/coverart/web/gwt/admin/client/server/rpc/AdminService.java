/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.ServicesStatuses;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Service for the admin GWT module
 * @author vincent
 *
 */
@RemoteServiceRelativePath("service")
public interface AdminService extends RemoteService {

	/**
	 * Get the services statuses as bean
	 * @return the {@link ServicesStatuses} containing statuses
	 */
	ServicesStatuses getServicesStatuses();
	
	/**
	 * Set the polling service status
	 * @param enabled <code>true</code> to enable, <code>false</code> to disable
	 */
	void setPollingServiceStatus(boolean enabled);
	
	/**
	 * Set the FM alert status
	 * @param enabled <code>true</code> to enable, <code>false</code> to disable
	 */
	void setFMAlertStatus(boolean enabled);
	
	/**
	 * Get the current title 
	 * @return the current title as {@link String} 
	 */
	String getCurrentTitle();

	/**
	 * Launch the indexing
	 */
	void launchIndexing();
	
	/**
	 * Is the indexing is still alive ?
	 * @return <code>true</code> if active, <code>false</code> if not
	 */
	boolean isIndexingActive();
	
	/**
	 * Launch the missing images recovery
	 */
	void launchMissingImagesRecovery();
	
	/**
	 * Is the recovery finished ?
	 * @return <code>true</code> if done, <code>false</code> if not
	 */
	boolean isRecoveryFinished();
	
	/**
	 * Get the list of recovered songs
	 * @return list of {@link SongGWT}
	 */
	List<SongGWT> getRecoveredSongs();
	
	/**
	 * Search songs without custom images across Lucene index
	 * @param keywords keywords for songs search
	 * @param limit the max number of result
	 * @return the list of found songs as {@link SongGWT}
	 */
	List<SongGWT> searchSongsWithoutCustomImage(String keywords, int limit);
	
	/**
	 * Search songs without custom images across Lucene index without limit
	 * @param keywords keywords for songs search
	 * @return the list of found songs as {@link SongGWT}
	 */
	List<SongGWT> searchSongsWithoutCustomImage(String keywords);
	
	/**
	 * Search songs with custom images across Lucene index
	 * @param keywords keywords for songs search
	 * @param limit the max number of result
	 * @return the list of found songs as {@link SongGWT}
	 */
	List<SongGWT> searchSongsWithCustomImage(String keywords, int limit);
	
	/**
	 * Search songs with custom images across Lucene index without limit
	 * @param keywords keywords for songs search
	 * @return the list of found songs as {@link SongGWT}
	 */
	List<SongGWT> searchSongsWithCustomImage(String keywords);
	
	/**
	 * Get all the songs with custom image
	 * @return the list of {@link SongGWT}
	 */
	List<SongGWT> getAllCustomImageSong();
	
	/**
	 * Set a song as a song with custom image
	 * @param songToSet the song to set
	 */
	void setSongAsSongWithCustomImage(SongGWT songToSet);
	
	/**
	 * Remove the custom image on a song - recover previous one
	 * @param songToRemoveCustomImage the song to remove custom image
	 */
	void removeCustomImageOnSong(SongGWT songToRemoveCustomImage);
	
}
