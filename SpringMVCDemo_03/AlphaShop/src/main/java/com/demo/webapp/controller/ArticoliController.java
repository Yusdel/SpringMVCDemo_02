package com.demo.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	private int NumArt = 0;
	private List<Articoli> recordset;
	/*
	 * @PathVariable = It tells SpringMVC that the "filter" element must be searched for in the
	 * path/url variables, then we pass it to the String "Filter".
	 */
	@RequestMapping(value = "/cerca/{filter}", method = RequestMethod.GET)
	public String GetArticoliByFilter(@PathVariable("filter") String Filter, Model model)
	{
		recordset = articoliService.SelArticoliByFilter(Filter);
		
		if(recordset != null)
				NumArt = recordset.size();
		
		/*pass data to the view ( ID = Articoli ) */
		model.addAttribute("NumArt", NumArt);
		model.addAttribute("Titolo", "Ricerca Articoli");
		model.addAttribute("Titolo2", "Risultati Ricerca " + Filter);
		model.addAttribute("Articoli", recordset);
		
		/*name of view ( Must be defined in the Tiles.xml file! ) */
		return "articoli";

	}
	
	// http://localhost:8080/alphashop/articoli/cerca?filter=barilla&rep=1
	@RequestMapping(value = "/cerca", method = RequestMethod.GET)
	public String getArticoliByFilter(@RequestParam("filter") String Filter, @RequestParam("rep") int IdRep, Model model) {
		
		List<Articoli> recordset = articoliService.SelArticoliByFilter(Filter)
				.stream()
				.filter(u -> u.getIdFamAss() == IdRep).collect(Collectors.toList());
		
		if (recordset != null) 
			NumArt = recordset.size();
		
		model.addAttribute("NumArt", NumArt);
		model.addAttribute("Titolo", "Ricerca Articoli");
		model.addAttribute("Titolo2", "Risultati Ricerca " + Filter);
		model.addAttribute("Articoli", recordset);
		
		return "articoli";
	}
}
