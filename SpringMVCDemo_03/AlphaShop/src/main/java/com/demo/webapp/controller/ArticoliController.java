package com.demo.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.webapp.domain.Articoli;
import com.demo.webapp.service.ArticoliService;

/*
 * URL : "/articoli/cerca/{filter}"
 */

@Controller
@RequestMapping("/articoli")
public class ArticoliController {
	
	@Autowired /*Code Injection of the Service Layer*/
	private ArticoliService articoliService;
	
	/*
	 * @PathVariable = It tells SpringMVC that the "filter" element must be searched for in the
	 * path/url variables, then we pass it to the String "Filter".
	 */
	@RequestMapping(value = "/cerca/{filter}", method = RequestMethod.GET)
	public String GetArticoliByFilter(@PathVariable("filter") String Filter, Model model)
	{
		List<Articoli> articoli = articoliService.SelArticoliByFilter(Filter);
		
		/*pass data to the view ( ID = Articoli ) */
		model.addAttribute("Articoli", articoli);
		
		/*name of view ( Must be defined in the Tiles.xml file! ) */
		return "articoli";

	}
}
