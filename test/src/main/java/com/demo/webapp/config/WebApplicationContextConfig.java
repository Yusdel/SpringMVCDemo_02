package com.demo.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


/*
 * Annotations define the purpose and nature of the Classes
 * 
 * @Contiguration = Indicates that a class declares one or more @Bean methods and can be processed by the Spring
 * container to generate Bean definitions and service requests for those beans at runtime.
 * 
 * @EnableWebMvc = Enable the features of the Spring MVC and registers the SpringMVC infrastructure 
 * components expected by DispatcherServlet
 * 
 * @ComponentScan = Tells the Dispatcher Servlet where to look for Controllers
 * (In this case I'm indicating to look for Controllers in packages that 
 * have the matrix: "com.demo.webapp")
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.demo.webapp")
public class WebApplicationContextConfig implements WebMvcConfigurer{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	/*
	 * The following Bean configures and activates the View Resolver
	 * which creates the pages to be returned to the Browser
	 */
	
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);

        /*
         * We are instructing the Spring MVC to look for pages
         * in the JSP format in the indicated folder
         */
        
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        return resolver;
    }
}
