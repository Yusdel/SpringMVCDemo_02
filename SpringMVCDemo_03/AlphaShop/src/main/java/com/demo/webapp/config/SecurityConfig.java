package com.demo.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/*
 * TODO Spring Security | new class
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter
{
	/*
	 * Activate BCryptPasswordEncoder
	 * To Crypt Password in the database
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	};
	
	/*
	 * Service that allows us to specify the users who will be able 
	 * to access our WebApp
	 * Case 1 : Hard coded
	 * Case 2 : Using DB data
	 */
	@Bean
	@Override
	public UserDetailsService userDetailsService()
	{
		UserBuilder users = User.builder();
		
		// manage user in local memory (only for test)
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		
		//Utente 1
		manager.createUser(
				users
				.username("Yus")
				.password(new BCryptPasswordEncoder().encode("123Stella"))
				.roles("USER")
				.build());
		
		manager.createUser(
				users
				.username("Admin")
				.password(new BCryptPasswordEncoder().encode("VerySecretPwd"))
				.roles("USER", "ADMIN")
				.build());
		
		return manager;

	}
	
	/*
	 * To Solve the follow Exception
	 * org.springframework.security.web.firewall.RequestRejectedException: 
	 * The request was rejected because the URL contained a potentially malicious String ";"
	 */
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() 
	{
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);
	    firewall.setAllowSemicolon(true); // able ";"
	    
	    return firewall;
	}
	
	/*
	 * (Allow MatrixVariable)
	 * Re-Setting the Spring internal Firewall with the new settings
	 */
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
	  super.configure(web);
	  
	  web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	 
	}
	
	/*
	 * The methods used for the Auth must be entered in this override
	 */
	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception
	{
		auth
			.userDetailsService(userDetailsService()) // Specifies the method to use to retrieve the user's detail
			.passwordEncoder(passwordEncoder()); // Specifies the Method to use to password encoder
	}
	
	// Admin URLs
	private static final String[] ADMIN_CLIENTI_MATCHER =
	{
			"/clienti/aggiungi/**",
			"/clienti/modifica/**",
			"/clienti/elimina/**"
	};
	
	/*
	 * Definition of the permissions that users can have 
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		http
		.authorizeRequests()
		.antMatchers("/resources/**").permitAll() // no Auth required
		.antMatchers("/login/**").permitAll() // no Auth required
		.antMatchers("/").hasAnyRole("ANONYMOUS", "USER") // no Auth required (remove ANONYMOUS to require Auth for the HomePage)
		.antMatchers(ADMIN_CLIENTI_MATCHER).access("hasRole('ADMIN')")
		.antMatchers("/clienti/**").hasRole("USER")
		.and() // configuration login form
			.formLogin()
				.loginPage("/login/form") // URL login form
				.loginProcessingUrl("/login")
				.failureUrl("/login/form?error") // Failed login URL
					.usernameParameter("userId")
					.passwordParameter("password")
		.and() // configuration exception
			.exceptionHandling()
			.accessDeniedPage("/login/form?forbidden") // URL for all not Authorization Pages
		.and()
			.logout()
			.logoutUrl("/login/form?logout");
		//.and().csrf().disable(); // csrf() is only for test
				
	}

}
