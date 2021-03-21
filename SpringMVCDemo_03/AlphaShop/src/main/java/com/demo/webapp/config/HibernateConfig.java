package com.demo.webapp.config;

import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.hibernate.cfg.Environment.*;

/*
 * @EnableTransactionManagement = Manages Transactions and execute auto-ROLLBACK in case of errors.
 * 
 * STEP A-Config: TODO Hibernate and JPA Framework
 */

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.demo.webapp.config"})
@PropertySource("classpath:application.properties")
public class HibernateConfig {
	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource; //fonte dati
	
	@Bean // Container to Manages Entity
	LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		
		// properties of Container
		factory.setJpaVendorAdapter(this.jpaVendorAdapter());
		factory.setDataSource(this.dataSource); // ... where to get the data
		factory.setPackagesToScan("com.demo.webapp.entities"); // ... where to go to get the entities
		factory.setJpaProperties(this.hibernateProperties()); // ... set Hibernate JPA properties
		factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		factory.setValidationMode(ValidationMode.NONE);
		
		return factory;
		
	}
	
	@Bean // Some Options
	public JpaVendorAdapter jpaVendorAdapter()
	{
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		hibernateJpaVendorAdapter.setShowSql(true);
		// To tell Hibernate that can't modify table structure
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		// To tell Hibernate which dialect he should speak with 
		hibernateJpaVendorAdapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect")); 

		return hibernateJpaVendorAdapter;
	}
	
	// get properties from application.properties 
	private Properties hibernateProperties()
	{
		Properties jpaProperties = new Properties();
		
		jpaProperties.put("javax.persistence.schema-generation.database.action", "none"); // added param
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

		// Setting C3P0 properties
		jpaProperties.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
		jpaProperties.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
		jpaProperties.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.acquire_increment"));
		jpaProperties.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
		jpaProperties.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
		
		return jpaProperties;
	}
	
	@Bean
	public JpaTransactionManager transactionManager()
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return transactionManager;
	}
}
