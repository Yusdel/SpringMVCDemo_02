package com.demo.webapp.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.webapp.domain.Articoli;
import com.demo.webapp.domain.FamAssort;
import com.demo.webapp.domain.Iva;
import com.demo.webapp.repository.FamAssRepository;
import com.demo.webapp.repository.IvaRepository;
import com.demo.webapp.service.ArticoliService;

/*
 * URL : "/articoli/cerca/{filter}"
 */

@Controller
@RequestMapping("/articoli")
public class ArticoliController {
	
	@Autowired /*Code Injection of the Service Layer*/
	private ArticoliService articoliService;
	
	@Autowired /* NOt used Service Layer because we have only one element. */
	private FamAssRepository famAssRepository;
	
	@Autowired /* NOt used Service Layer because we have only one element. */
	private IvaRepository ivaRepository;
	
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
	
	/* http://localhost:8080/alphashop/articoli/cerca?filter=barilla&rep=1 */
	@RequestMapping(value = "/cerca", method = RequestMethod.GET)
	public String GetArticoliByFilter(@RequestParam("filter") String Filter, @RequestParam("rep") int IdRep, Model model) {
		
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
	
	/*
	 * The @MatrixVariable uses special characters, which could be used
	 * for a SQL Injection, for this, basic Spring Framework disable this notation.
	 * 
	 * TODO To enable the @MatrixVariable, it's necessary to disable the Spring Security for this case
	 * in the Configuration Class. (WebApplicationContextClass)
	 */
	
	/* http://localhost:8080/alphashop/articoli/cerca/barilla/parametri;reparti=1,10,15;orderby=codart,desc;paging=0,10 */
	@RequestMapping(value = "/cerca/{filter}/{parametri}", method = RequestMethod.GET)
	public String GetArticoliByFilterMatrix(@PathVariable("filter") String Filter,
			@MatrixVariable(pathVar = "parametri") Map<String, List<String>> parametri, Model model) {
		
			int NumArt = 0;
			String orderBy = "codart";
			String tipo = "desc";
			Long SkipValue = (long) 0;
			Long LimitValue = (long) 10;

			List<String> IdRep = parametri.get("reparti");
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

			List<Articoli> recordset = articoliService.SelArticoliByFilter(Filter, orderBy, tipo);

			//Lambda Expression
			recordset = recordset
					.stream()
					.filter(u -> IdRep.contains(Integer.toString(u.getIdFamAss())))
					.filter(u -> u.getQtaMag() > 0)
					.filter(u -> u.getPrezzo() > 0)
					.collect(Collectors.toList()); /* return filtered list */

			if (recordset != null)
				NumArt = recordset.size();

			/*pagination*/
			recordset = recordset
					.stream()
					.skip(SkipValue) /* start point */
					.limit(LimitValue) /* end point */
					.collect(Collectors.toList());

			/*
			 * if (orderBy.equals("codart") && tipo.equals("asc")) recordset =
			 * recordset.stream().sorted(Comparator.comparing(Articoli::getCodArt))
			 * .collect(Collectors.toList()); else if (orderBy.equals("codart") &&
			 * tipo.equals("desc")) recordset =
			 * recordset.stream().sorted(Comparator.comparing(Articoli::getCodArt).
			 * reversed()) .collect(Collectors.toList());
			 */
			
			 //List<String> Categorie = recordset.stream().map(Articoli::getDesFamAss).distinct().collect(Collectors.toList());
			 

			model.addAttribute("Articoli", recordset);
			model.addAttribute("NumArt", NumArt);
			model.addAttribute("Titolo", "Ricerca Articoli");

			return "articoli";
		}
		
		/* http://localhost:8080/AlphaShop/articoli/cerca/barilla/creati?daData=2010-10-31&aData=2015-10-31 */
		@RequestMapping(value = "/cerca/{filter}/creati", method = RequestMethod.GET)
		public String GetArticoliByFilterDate(@PathVariable("filter") String Filter,
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("daData") Date startDate,
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("aData") Date endDate, 
					Model model)
		{

				List<Articoli> recordset = articoliService.SelArticoliByFilter(Filter)
						.stream()
						.filter(u -> u.getDataCreaz().after(startDate))
						.filter(U -> U.getDataCreaz().before(endDate))
						.collect(Collectors.toList());

				if (recordset != null)
					NumArt = recordset.size();

				model.addAttribute("NumArt", NumArt);
				model.addAttribute("Titolo", "Ricerca Articoli");
				model.addAttribute("Titolo2", "Risultati Ricerca " + Filter);
				model.addAttribute("Articoli", recordset);

				return "articoli";
		}
		
		/* http://localhost:8080/alphashop/articoli/infoart/000087101 */
		@RequestMapping(value = "/infoart/{codart}", method = RequestMethod.GET)
		public String GetDettArticolo(@PathVariable("codart") String CodArt, Model model)
		{
				Articoli articolo = null;
				recordset = articoliService.SelArticoliByFilter(CodArt);
				
				if (recordset != null)
					articolo = recordset.get(0);

				model.addAttribute("Titolo", "Dettaglio Articolo");
				model.addAttribute("Titolo2", "Dati Articolo " + CodArt);
				model.addAttribute("articolo", articolo);

				return "infoArticolo";
		}
		
		/* FORM Insert Article (first GET then POST)*/
		
		/* @RequestMapping(value = "/aggiungi", method = RequestMethod.GET) */
		/* OR */
		@GetMapping(value = "/aggiungi")
		public String InsArticoli(Model model) {
			
			/* From here we pass data to jsp and empty model to fill */
			Articoli articolo = new Articoli();
			
			List<FamAssort> famAssort = famAssRepository.SelFamAssort();
			List<Iva> iva = ivaRepository.SelIva();

			model.addAttribute("Titolo", "Inserimento Nuovo Articolo");
			model.addAttribute("famAssort", famAssort);
			model.addAttribute("iva", iva);
			/* newArticolo = name of modelAttribute in form (data binding). */
			model.addAttribute("newArticolo", articolo);
			
			return "insArticolo";
		}
		
		/* FORM method POST, as params we pass the ID (modelAttribute) of form */
		@PostMapping(value = "/aggiungi")
		public String GestInsArticoli(@ModelAttribute("newArticolo") Articoli articolo, BindingResult result) {
			
			if(result.getSuppressedFields().length > 0)
				throw new RuntimeException("ERROR binding in the following fields: " + 
						StringUtils.arrayToCommaDelimitedString(result.getSuppressedFields()));
			else
				articoliService.InsArticolo(articolo);
			
			return "redirect:/articoli/lastart";
			/* return "redirect:/articoli/cerca/" + articolo.getCodArt(); */
		}
		
		/*
		 * @InitBinder = It allows us to create a mapping of the fields authorized
		 * to perform Data Binding or of those fields that are explicitly declared excluded from Data Binding.
		 */
		@InitBinder
		public void initializerBinder(WebDataBinder binder) {
			/* Fields Allowed : Specify the fields that the Binder will have to populate! */
			binder.setAllowedFields("codArt","descrizione","um","pzCart","pesoNetto","idIva","idStatoArt","idFamAss");
			
			/* Fields Not Allowed : Black List, fields that must not be enabled for Data Binding! */
			binder.setDisallowedFields("Yusdel","Morales");
		}
}
