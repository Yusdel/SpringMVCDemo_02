package com.xantrix.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterCorsConfig implements WebMvcConfigurer{
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**") // able Urls ( /** = all )
					.allowedOrigins("*") // able Client ( * = all )
					.allowedMethods("PUT", "DELETE", "GET", "POST")
					.allowedHeaders("*") // able headers ( * = all )
					.exposedHeaders("header1", "header2")
					.allowCredentials(false) // to be enable for example when sending credentials through the use of cookies
					.maxAge(3600); // duration in seconds
			}
		};
	}

}
