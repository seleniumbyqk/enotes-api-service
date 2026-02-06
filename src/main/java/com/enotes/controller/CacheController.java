package com.enotes.controller;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.endpoint.CacheControllerEndpoint;
import com.enotes.service.CacheManagerService;
import com.enotes.util.CommonUtil;

@RestController
public class CacheController implements CacheControllerEndpoint{

	private final CacheManagerService cacheManagerService;
	
	public CacheController(CacheManagerService cacheManagerService)
	{
		this.cacheManagerService = cacheManagerService;
	}
	
	@Override
	public ResponseEntity<?> getAllCache() {
		// TODO Auto-generated method stub
		
		Collection<String> cache = cacheManagerService.getCache();
		
		return CommonUtil.createBuildResponse(cache, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCache(String cache_name) {
		// TODO Auto-generated method stub
		
		Cache cacheName = cacheManagerService.getCacheName(cache_name);
		
		return CommonUtil.createBuildResponse(cacheName, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeAllCache() {
		// TODO Auto-generated method stub
		
		cacheManagerService.reoveAllCache();
		
		return CommonUtil.createBuildResponseMessage("Remove all cache", HttpStatus.OK);
	}

	
}
