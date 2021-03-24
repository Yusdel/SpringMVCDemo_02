package com.demo.webapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.webapp.service.UtentiService;

// TODO Spring Security
@Controller
@RequestMapping("/login/form") // set in Config class
public class LoginController 
{
//	@GetMapping
//	public String getLogin(Model model)
//	{
//		return "login";
//	}
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	@Qualifier("persistentTokenRepository")
    private PersistentTokenRepository persistentTokenRepository;
	
	@Autowired
	private UtentiService utentiService;
	
	@Value( "${app.admin.password}" )
	private String adminPwd;
	
	@GetMapping
	public String getLogin(Model model)
	{
		utentiService.SalvaAdminUser(adminPwd);
		return "login";
	}
	
	@PostMapping
	public String getLoginPost(HttpServletRequest request, HttpServletResponse response)
	{
		String[] test = request.getParameterValues("logout");
		
		if (test != null)
		{
			Cookie cookieWithSlash = new Cookie("JSESSIONID", null);
	        //Tomcat adds extra slash at the end of context path (e.g. "/foo/")
	        cookieWithSlash.setPath(request.getContextPath() + "/"); 
	        cookieWithSlash.setMaxAge(0); 

	        Cookie cookieWithoutSlash = new Cookie("JSESSIONID", null);
	        //JBoss doesn't add extra slash at the end of context path (e.g. "/foo")
	        cookieWithoutSlash.setPath(request.getContextPath()); 
	        cookieWithoutSlash.setMaxAge(0); 

	        //Remove cookies on logout so that invalidSessionURL (session timeout) is not displayed on proper logout event
	        response.addCookie(cookieWithSlash); //For cookie added by Tomcat 
	        response.addCookie(cookieWithoutSlash); //For cookie added by JBoss
	        
	        
	        if (test.length == 2)
	        {
	        	 logger.info("utente: " + test[1]); 
				 persistentTokenRepository.removeUserTokens(test[1]);
	        }
	        
	        
		}
		
		return "redirect:/login/form?logout";
	}
}
