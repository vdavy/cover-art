package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.repositories.ServerStatusRepository;

/**
 * Tests of the server status repository
 * @author vincent
 *
 */
public class TestServerStatusRepository {
	
	//the repository
	@Autowired
	private ServerStatusRepository repository;

	/**
	 * Test the {@link ServerStatusRepository} methods
	 */
	@Test
	public void testRepository() {
		repository.recordServerUp();
		assertTrue(repository.getLastServerStatus());
		repository.recordServerDown();
		assertFalse(repository.getLastServerStatus());
	}
}
