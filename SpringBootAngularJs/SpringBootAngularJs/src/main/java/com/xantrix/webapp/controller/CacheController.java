package com.xantrix.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	
//	@Autowired
//	private CacheManager cacheManager;
//	
//	@RequestMapping(value = "cleancache")
//	public void clearCache() {
//		for(String name:cacheManager.getCacheNames()){
//            cacheManager.getCache(name).clear();            // clear cache by name
//        }
//	}
	
}
