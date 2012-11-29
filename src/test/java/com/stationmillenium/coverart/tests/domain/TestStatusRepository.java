package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.repositories.StatusRepository;

/**
 * Tests of the server status repository
 * @author vincent
 *
 */
public class TestStatusRepository {
	
	//the repository
	@Autowired
	private StatusRepository repository;

	/**
	 * Test the {@link StatusRepository} server status update
	 */
	@Test
	public void testServerStatusUpdate() {
		repository.recordServerUp();
		assertTrue(repository.getLastServerStatus());
		repository.recordServerDown();
		assertFalse(repository.getLastServerStatus());
	}
	
	/**
	 * Test the {@link StatusRepository} FM status update
	 */
	@Test
	public void testFMStatusUpdate() {
		repository.recordFMUp();
		assertTrue(repository.getLastFMStatus());
		repository.recordFMDown();
		assertFalse(repository.getLastFMStatus());
	}
	
	/**
	 * Test playlist timeout update process
	 */
	@Test
	public void testPlaylistTimeoutUpdate() {
		assertTrue(repository.recordPlaylistUpdateTimeout());
		assertFalse(repository.recordPlaylistUpdateTimeout());
		assertTrue(repository.recordPlaylistUpdated());
		assertFalse(repository.recordPlaylistUpdated());
	}
	
}
