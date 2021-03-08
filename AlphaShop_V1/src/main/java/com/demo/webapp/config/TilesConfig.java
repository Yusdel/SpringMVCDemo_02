package com.demo.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@Configuration
public class TilesConfig {
	
	@Bean
	public TilesConfigurer tilesConfigurer()
	{
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		
		/* path to configuration file xml*/
		tilesConfigurer.setDefinitions("/WEB-INF/layouts/definitions/tiles.xml");
		tilesConfigurer.setCheckRefresh(true);
		
		return tilesConfigurer;
	}
}
