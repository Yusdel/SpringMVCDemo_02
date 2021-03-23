package com.demo.webapp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/*
 * TODO Spring Security | new class
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter
{
	@Autowired
	@Qualifier
	private UserDetailsService userDetailsService;
	
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
	 
	
	/*
	 * Service that allows us to specify the users who will be able 
	 * to access our WebApp
	 * Case 2 : Data from DB
	 * 
	 * The methods used for the Auth must be entered in this override
	 */
	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception
	{
		
		auth.authenticationProvider(authenticationProvider());
		
		/*
		 * Case 1 : Hard Code
		auth
			.userDetailsService(userDetailsService()) // Specifies the method to use to retrieve the user's detail
			.passwordEncoder(passwordEncoder()); // Specifies the Method to use to password encoder
		*/
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	// Admin URLs
	private static final String[] ADMIN_CLIENTI_MATCHER =
	{
			"/clienti/aggiungi/**",
			"/clienti/modifica/**",
			"/clienti/elimina/**"
	};
	
	// Admin URLs
		private static final String[] ADMIN_ARTICOLI_MATCHER =
		{
				"/articoli/aggiungi/**",
				"/articoli/modifica/**",
				"/articoli/elimina/**",
				"/articoli/**"
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
		.antMatchers("/articoli/**").hasRole("USER") 
		.and() // configuration login form
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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
	
	public AuthenticationFilter authenticationFilter() 
			throws Exception 
	{
		
		 AuthenticationFilter filter = new AuthenticationFilter();
		 
		 filter.setAuthenticationManager(authenticationManagerBean());
		 filter.setAuthenticationFailureHandler(failureHandler());
		 filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		 filter.setRememberMeServices(customRememberMeServices());
		 
		 
		 return filter;
		 
	}
	
	public SimpleUrlAuthenticationFailureHandler failureHandler() 
	{ 
        return new SimpleUrlAuthenticationFailureHandler("/login/form?error"); 
    } 
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() 
	{
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		
		return auth;
	}
	
	@Bean
	public PersistentTokenBasedRememberMeServices customRememberMeServices()
	{
		String Key = "ricordamiKey";
		
		PersistentTokenBasedRememberMeServices rememberMeServices = 
      			new CustomRememberMeServices(Key, userDetailsService, persistentTokenRepository);
		
		rememberMeServices.setCookieName("ricordami");
      	rememberMeServices.setTokenValiditySeconds(60 * 60 * 4);
      	rememberMeServices.setParameter("ricordami");
      	rememberMeServices.setUseSecureCookie(false); //todo Abilitare con l'SSL
      	
      	return rememberMeServices;
		
		
	}
	/*
	 * Service that allows us to specify the users who will be able 
	 * to access our WebApp
	 * Case 1 : Hard coded
	 
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
	*/

}
