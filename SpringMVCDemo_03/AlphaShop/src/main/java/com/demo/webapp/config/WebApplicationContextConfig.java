package com.demo.webapp.config;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;
import org.springframework.web.util.UrlPathHelper;

import com.demo.webapp.domain.Articoli;
import com.demo.webapp.views.ArticoliCsvView;
import com.demo.webapp.views.ArticoliExcelView;
import com.demo.webapp.views.ArticoliPdfView;


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
    
    /* Resource Handlers */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	/* "/static/images/" => "/img/**" */
    	registry.addResourceHandler("/img/**").addResourceLocations("/static/images/");
    }
    
    /* 
     * Start Hibernate Validator 
     * https://howtodoinjava.com/hibernate/hibernate-validator-java-bean-validation/
     */
    @Bean(name = "validator")
	public LocalValidatorFactoryBean validator()
	{
    	/* When error get message from messageSource() */
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());

		return bean;
	}
   
    @Override
    public Validator getValidator() {
    	
    	return validator();
    }
    /* End Hibernate Validator*/
    
    /* 
     * TODO Return JSON/XML/Excel/PDF/CSV data
     * Bean to return JSON Model (Generic) 
     */
    @Bean
	public MappingJackson2JsonView jsonView()
	{
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);

		return jsonView;
	}
    
    /* 
     * TODO Return JSON/XML/Excel/PDF/CSV data
     * To return XML Model (Is necessary specify classes) 
     * 
     */
    @Bean
	public MarshallingView xmlView()
	{
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Articoli.class);
		
		MarshallingView xmlView = new MarshallingView(marshaller);

		return xmlView;
	}
    
    /*
     * TODO Return JSON/XML/Excel/PDF/CSV data
     * Initialized
     * http://localhost:8080/AlphaShop/articoli/cerca/nutella.pdf
     */
    @Bean
	public ArticoliPdfView articoliPdfView()
	{
		return new ArticoliPdfView("Articoli.pdf");
	}
    
    /*
     * TODO Return JSON/XML/Excel/PDF/CSV data
     * Initialized
     * http://localhost:8080/AlphaShop/articoli/cerca/nutella.xlsx
     */
    @Bean
	public ArticoliExcelView articoliExcelView()
	{
		return new ArticoliExcelView("Articoli.xlsx");
	}
	
    /*
     * TODO Return JSON/XML/Excel/PDF/CSV data
     * Initialized
     * http://localhost:8080/AlphaShop/articoli/cerca/nutella.csv
     */
	@Bean
	public ArticoliCsvView articoliCsvView()
	{
		return new ArticoliCsvView("Articoli.csv");
	}
    
    /* 
     * TODO Return JSON/XML/Excel/PDF/CSV data
     * View Resolver = to convert JSON/XML Model to JSON/XML format/view
     */
    @Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager)
	{
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		
		ArrayList<View> views = new ArrayList<>();
		views.add(jsonView()); // Formato JSON
		views.add(xmlView()); // Formato XML
		views.add(articoliPdfView());
		views.add(articoliExcelView());
		views.add(articoliCsvView());
		 
		resolver.setDefaultViews(views);
		
		return resolver;
	}
}
