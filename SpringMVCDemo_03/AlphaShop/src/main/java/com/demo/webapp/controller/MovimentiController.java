package com.demo.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.webapp.domain.Movimenti;
import com.demo.webapp.service.MovimentiService;

@Controller
@RequestMapping("/movimenti")
public class MovimentiController 
{
	@Autowired
	private MovimentiService movimentiService;
	
	// http://localhost:8080/alphashop/movimenti/cerca?codart=000081602
	@RequestMapping(value = "/cerca", method = RequestMethod.GET)
	public String GetMovimentiByCodArt(@RequestParam("codart") String CodArt, Model model)
	{
		
		Movimenti movimento =  movimentiService.SelMovimentiByCodArt(CodArt);
		
		List<Movimenti> movimenti = new ArrayList<Movimenti>();
		movimenti.add(movimento);
		
		model.addAttribute("Titolo", "Movimenti Articoli");
		model.addAttribute("Titolo2", "Risultati Movimenti " + CodArt);
		model.addAttribute("Movimenti", movimenti);
		
		return "movimenti";

	}
	
	//  http://localhost:8080/alphashop/movimenti/cerca/barilla
	@RequestMapping(value = "/cerca/{filter}", method = RequestMethod.GET)
	public String GetArticoliByFilter(@PathVariable("filter") String Filter, Model model)
	{	
		List<Movimenti> movimenti =  movimentiService.SelMovimentiByFilter(Filter);
		
		model.addAttribute("Titolo", "Movimenti Articoli");
		model.addAttribute("Titolo2", "Risultati Movimenti " + Filter);
		model.addAttribute("Movimenti", movimenti);
		
		return "movimenti";
	}
	
	// http://localhost:8080/alphashop/movimenti/cerca/barilla/parametri;magazzino=>0;orderby=codart,desc;paging=0,10
	@RequestMapping(value = "/cerca/{filter}/{parametri}", method = RequestMethod.GET)
	public String GetArticoliByFilterMatrix(@PathVariable("filter") String Filter,
			@MatrixVariable(pathVar = "parametri") Map<String, List<String>> parametri, Model model)
	{
		
		String orderBy = "codart";
		String tipo = "desc";
		String filterMag = "";
		
		Long SkipValue = (long) 0;
		Long LimitValue = (long) 10;

		List<String> FilterMag = parametri.get("magazzino");
		
		List<String> OrderBy = parametri.get("orderby");
		List<String> Paging = parametri.get("paging");

		if (OrderBy != null)
		{
			orderBy = OrderBy.get(0);
			tipo = OrderBy.get(1);
		}

		if (Paging != null)
		{
			SkipValue = Long.parseLong(Paging.get(0));
			LimitValue = Long.parseLong(Paging.get(1));
		}
		
		if (FilterMag != null)
		{
			filterMag = FilterMag.get(0);
		}

		List<Movimenti> recordset = movimentiService.SelMovimentiByFilter(Filter, orderBy, tipo, filterMag);
		
		recordset = recordset
				.stream()
				.skip(SkipValue)
				.limit(LimitValue)
				.collect(Collectors.toList());
 
		model.addAttribute("Titolo", "Movimenti Articoli");
		model.addAttribute("Titolo2", "Risultati Movimenti " + Filter);
		model.addAttribute("Movimenti", recordset);

		return "movimenti";
	}


}
