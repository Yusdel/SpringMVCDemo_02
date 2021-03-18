package com.demo.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * @EnableTransactionManagement = Enable transaction management, all queries will be subject to transaction
 * 
 * @PropertySource = It's used by the jdbcConfig class to access the application.properties configuration
 * file available in the classpath
 */

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.demo.webapp.config"}) /* to jdbc because it will use other classes besides this one*/
@PropertySource(value = {"classpath:application.properties"})
public class jdbcConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		/* to create our database connection */
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public NamedParameterJdbcTemplate geJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	/* to read params in application.properties file */
	@Bean(name = "dataSource")
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		
		return dataSource;
	}
	
	/* 
	 * manage transactions
	 * STEP A-Config: TODO Hibernate and JPA Framework 
	 * Transactions will be manage by Hibernate not Jdbc
	 *
	@Bean
	public DataSourceTransactionManager transactionManager()
	{
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		
		return transactionManager;
	} */
}
