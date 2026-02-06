package com.enotes.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.enotes.controller.HomeController;

@Service
public class CacheManagerServiceImpl implements CacheManagerService{

	//Logger implementation manually
	Logger log = LoggerFactory.getLogger(HomeController.class);
		
	private final CacheManager cacheManager;
	
	public CacheManagerServiceImpl(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}
	
	
	@Override
	public Collection<String> getCache()
	{
		Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName : cacheNames)
		{
			
			Cache cache = cacheManager.getCache(cacheName);
			
			log.info("Cache Name: {}", cache);
			
		}
		
		return cacheNames;
	}


	@Override
	public Cache getCacheName(String cacheName) {
		// TODO Auto-generated method stub
		
		Cache cache = cacheManager.getCache(cacheName);
		
		log.info("Cache Name: {}", cache);
		return cache;
	}


	@Override
	public void reoveAllCache() {
		// TODO Auto-generated method stub
		
    Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName : cacheNames)
		{
			
			Cache cache = cacheManager.getCache(cacheName);
			
			log.info("Cache Name: {}", cacheName);
			
			cache.clear();
			
		}
	}


	@Override
	public void removeCacheByName(List<String> cacheNames) {
		// TODO Auto-generated method stub
		
		for(String cacheName : cacheNames)
		{
			
			Cache cache = cacheManager.getCache(cacheName);
			
			log.info("Cache Name: {}", cacheName);
			
			cache.clear();
			
		}
	}
	
	
	
	
}
