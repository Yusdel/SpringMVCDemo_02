package com.demo.webapp.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.util.UrlPathHelper;


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
	
	/* To tell SpringMVC that it should not only use the viewResolver but also the Tiles */
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry)
	{
		TilesViewResolver viewResolver = new TilesViewResolver();
		registry.viewResolver(viewResolver);
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
    
    /* Disable Spring Security Url Path To use @MatrixVariable*/
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    	
    	UrlPathHelper urlPathHelper = new UrlPathHelper();
    	/* Ignore the deletion of the Semicolon*/
    	urlPathHelper.setRemoveSemicolonContent(false);
    	
    	configurer.setUrlPathHelper(urlPathHelper);
    }
    
    /* MessageSource = Outsourcing of Labels entered manually in the html code.
     * Use for several languages web site! 
     * (file path in "src/main/resources")
     */
    
    @Bean
    public MessageSource messageSource() {
    	
    	/* ResourceBundleMessageSource = To specify the origin, the source, of our messages. */
    	ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
    	resource.setBasename("messages"); /* name of file = messages.properties */
    	
    	return resource;
    }
    
    /*
     * Interceptors = Elements that can be activated before or after a specific call. 
     * 
     * This interceptor activates at both boot and run time.
     */
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    	localeChangeInterceptor.setParamName("language"); /* language = String used in jsp links */
    	
    	registry.addInterceptor(localeChangeInterceptor);
    }
    
    /* Set Default Language */
    @Bean
	public LocaleResolver localeResolver()
	{
		/*
		 * SessionLocaleResolver resolver = new SessionLocaleResolver();
		 * resolver.setDefaultLocale(new Locale("it")); return resolver;
		 */

		CookieLocaleResolver r = new CookieLocaleResolver();
		r.setCookieName("localeInfo");
		r.setCookieMaxAge(24 * 60 * 60);
		r.setDefaultLocale(new Locale("it")); /* If no result (messages_it.properties) will take messages.properties */

		return r;

	}
}
