/**
 * 
 */
package com.stationmillenium.coverart.configuration.general;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.DefaultKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configration générale des caches du projet
 * @author vincent
 *
 */
@Configuration
@EnableCaching
public class GeneralCacheConfiguration implements CachingConfigurer {

	@Bean
	@Override
	public CacheManager cacheManager() {
		//définir les caches
		List<Cache> cacheList = new ArrayList<Cache>();
		cacheList.add(new ConcurrentMapCache("loggedUserId"));
		
		//définir le cache manager
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(cacheList);
		return cacheManager;
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}

}
