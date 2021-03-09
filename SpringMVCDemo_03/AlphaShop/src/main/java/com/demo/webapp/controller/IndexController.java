package com.demo.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Using the @Controller annotation, the Dispatcher Servlet identifies
 * the controller classes 
 */

@Controller
@RequestMapping("/") /* base request mapping*/
public class IndexController {
	
	/*
	  * @RequestMapping
     * This notation Maps requests, that is, it specifies the URL to which 
     * "getWelcome" should respond, that is "index"
     */
	
	@RequestMapping(value="index")
	public String getWelcome(Model model)
	{
		 /* addAttribute(<ID>, <data>) */
		model.addAttribute("intestazione", "Benvenuti nel sito Alpha Shop 2");
		model.addAttribute("saluti", "Seleziona i prodotti da acquistare");
		
		 /* "index" = JSP page name that the getWelcome method will return */
		return "index";
	}
	
	@RequestMapping /* root */
	public String getWelcome2(Model model)
	{
		model.addAttribute("intestazione", "Benvenuti nel sito Alpha Shop");
		model.addAttribute("saluti", "Seleziona i prodotti da acquistare");
		
		return "index";
	}
}
