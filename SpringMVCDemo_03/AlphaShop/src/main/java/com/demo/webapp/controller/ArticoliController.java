package com.demo.webapp.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo.webapp.domain.Articoli;
import com.demo.webapp.domain.FamAssort;
import com.demo.webapp.domain.Iva;
import com.demo.webapp.exception.NoInfoArtFoundException;
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
	private final String PathImages = "static\\images\\Articoli\\";
	
	/* base url */
	@RequestMapping(method = RequestMethod.GET)
	public String GetArticoli(Model model)
	{
		model.addAttribute("Titolo", "Ricerca Articoli");
		model.addAttribute("Titolo2", "Ricerca gli articoli");
		model.addAttribute("IsArticoli", true);
		
		return "articoli";
	}
	
	@GetMapping(value = "/search")
	public String SearchItem(@RequestParam("filter") String pSearchTerm, Model model)
	{
		recordset = articoliService.SelArticoliByFilter(pSearchTerm);

		if (recordset != null)
			NumArt = recordset.size();

		model.addAttribute("NumArt", NumArt);
		model.addAttribute("Titolo", "Ricerca Articoli");
		model.addAttribute("Titolo2", "Risultati Ricerca " + pSearchTerm);
		model.addAttribute("Articoli", recordset);
		model.addAttribute("IsArticoli", true);
		model.addAttribute("filter", pSearchTerm);

		return "articoli";
	}
	
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
		public String GetDettArticolo(@PathVariable("codart") String CodArt, Model model, HttpServletRequest request)
		{
				Articoli articolo = null;
				recordset = articoliService.SelArticoliByFilter(CodArt);
				
				boolean IsFileOk = false;
				
				/* Custom Error Page */
				if (recordset == null || recordset.isEmpty())
					throw new NoInfoArtFoundException(CodArt); 
				else
					articolo = recordset.get(0);
//				
//				if (recordset!=null)
//					articolo = recordset.get(0);
				
				try
				{
					String rootDirectory = request.getSession().getServletContext().getRealPath("/");
					String PathName = rootDirectory + PathImages + articolo.getCodArt().trim() + ".png";

					File f = new File(PathName);
					
					IsFileOk = f.exists();
					
				} 
				catch (Exception ex)
				{ 
				}

				//pass data to views
				model.addAttribute("Titolo", "Dettaglio Articolo");
				model.addAttribute("Titolo2", "Dati Articolo " + CodArt);
				model.addAttribute("articolo", articolo);
				model.addAttribute("IsFileOk", IsFileOk);

				return "infoArticolo";
		}
		
		/* FORM Insert Article (first GET then POST)*/
		
		/* @RequestMapping(value = "/aggiungi", method = RequestMethod.GET) */
		/* OR */
		@GetMapping(value = "/aggiungi")
		public String InsArticoli(Model model) {
			
			/* From here we pass data to jsp and empty model to fill */
			Articoli articolo = new Articoli();
			
			//List<FamAssort> famAssort = famAssRepository.SelFamAssort();
			//List<Iva> iva = ivaRepository.SelIva();

			model.addAttribute("Titolo", "Inserimento Nuovo Articolo");
			model.addAttribute("famAssort", getFamAssort());
			model.addAttribute("iva", getIva());
			/* newArticolo = name of modelAttribute in form (data binding). */
			model.addAttribute("newArticolo", articolo);
			
			return "insArticolo";
		}
		
		@ModelAttribute("famAssort")
		public List<FamAssort> getFamAssort()
		{
			List<FamAssort> famAssort = famAssRepository.SelFamAssort();

			return famAssort;
		}

		@ModelAttribute("iva")
		public List<Iva> getIva()
		{
			List<Iva> iva = ivaRepository.SelIva();

			return iva;
		}
		
		/* 
		 * FORM method POST, as params we pass the ID (modelAttribute) of form 
		 * 
		 * @Valid = Active validation of data 
		 */
		@PostMapping(value = "/aggiungi")
		public String GestInsArticoli(@Valid @ModelAttribute("newArticolo") Articoli articolo, BindingResult result, HttpServletRequest request) {
			
			MultipartFile productImage = articolo.getImmage();
			
			/* check error messages - validation */
			if (result.hasErrors())
				return "insArticolo";
			
			/* Upload IMG */
			if(productImage != null && !productImage.isEmpty()) {
				try
				{
					String rootDirectory = request.getSession().getServletContext().getRealPath("/");
					String PathName = rootDirectory + PathImages + articolo.getCodArt().trim() + ".png";

					/* transfertTo = from temporary folder to Articoli folder */
					productImage.transferTo(new File(PathName));
					
				} 
				catch (Exception ex)
				{
					throw new RuntimeException("Errore trasferimento file", ex);
				}
			}
			
			if(result.getSuppressedFields().length > 0)
				throw new RuntimeException("ERROR binding in the following fields: " + 
						StringUtils.arrayToCommaDelimitedString(result.getSuppressedFields()));
			else
				articoliService.InsArticolo(articolo);
			
			return "redirect:/articoli/infoart/" + articolo.getCodArt().trim();
			/* return "redirect:/articoli/cerca/" + articolo.getCodArt(); */
		}
		
		/*
		 * @InitBinder = It allows us to create a mapping of the fields authorized
		 * to perform Data Binding or of those fields that are explicitly declared excluded from Data Binding.
		 */
		@InitBinder
		public void initializerBinder(WebDataBinder binder) {
			/* Fields Allowed : Specify the fields that the Binder will have to populate! */
			binder.setAllowedFields("CodArt","codArt","descrizione","um","pzCart","pesoNetto","idIva","idStatoArt","idFamAss","immage");
			
			/* Fields Not Allowed : Black List, fields that must not be enabled for Data Binding! */
			binder.setDisallowedFields("Yusdel","Morales");
		}
		
		/* Method of handling the error */
		@ExceptionHandler(NoInfoArtFoundException.class)
		public ModelAndView handleError(HttpServletRequest request, NoInfoArtFoundException exception)
		{
			/* ModelAndView = Is a variant of Model */
			ModelAndView mav = new ModelAndView();

			mav.addObject("codice", exception.getCodArt());
			mav.addObject("exception", exception);
			mav.addObject("url", request.getRequestURL() + "?" + request.getQueryString());
			
			mav.setViewName("noInfoArt");

			return mav;
		}
		
		/* 
		 * TODO Return JSON/XML/Excel/PDF/CSV data 
		 * http://localhost:8080/AlphaShop/articoli/cerca/pasta/download.pdf
		 * (download.csv - download.xlsx)
		 */
		@RequestMapping(value = "/cerca/{filter}/download", method = RequestMethod.GET)
		public String GetArticoliByFilterDwld(@PathVariable("filter") String Filter, Model model)
		{
			recordset = articoliService.SelArticoliByFilter(Filter);
			model.addAttribute("Articoli", recordset);

			return "";
		}
		
		@GetMapping(value = "/modifica/{CodArt}")
		public String UpdArticoli(Model model, @PathVariable("CodArt") String CodArt)
		{
			Articoli articolo =  articoliService.SelArticoliByFilter(CodArt).get(0);
			
			if (articolo == null)
				throw new NoInfoArtFoundException(CodArt); 
			
			model.addAttribute("Titolo", "Modifica Articolo");
			model.addAttribute("newArticolo", articolo);
			model.addAttribute("famAssort", getFamAssort());
			model.addAttribute("Iva", getIva());

			return "insArticolo";
		}
		
		// http://localhost:8080/AlphaShop/articoli/modifica/000021301
		@RequestMapping(value = "/modifica/{CodArt}", method = RequestMethod.POST)
		public String GestUpdArticoli(@Valid @ModelAttribute("newArticolo") Articoli articolo, BindingResult result,
				@PathVariable("CodArt") String CodArt, Model model, HttpServletRequest request)
		{

			if (result.hasErrors())
			{
				return "insArticolo";
			}

			MultipartFile productImage = articolo.getImmage();

			if (productImage != null && !productImage.isEmpty())
			{
				try
				{
					String rootDirectory = request.getSession().getServletContext().getRealPath("/");
					String PathName = rootDirectory + PathImages + articolo.getCodArt().trim() + ".png";

					productImage.transferTo(new File(PathName));
					
				} 
				catch (Exception ex)
				{
					throw new RuntimeException("Errore trasferimento file", ex);
				}
			}

			if (result.getSuppressedFields().length > 0)
				throw new RuntimeException("ERRORE: Tentativo di eseguire il binding dei seguenti campi NON consentiti: "
						+ StringUtils.arrayToCommaDelimitedString(result.getSuppressedFields()));
			else
			{
				articoliService.InsArticolo(articolo);
			}

			return "redirect:/articoli/infoart/" + CodArt.trim();
		}
		
		@GetMapping(value = "/elimina/{CodArt}")
		public String DelArticolo(@PathVariable("CodArt") String codArt, Model model)
		{
			try
			{
				if (codArt.length() > 0)
				{
					articoliService.DelArticolo(codArt);
				}
			} 
			catch (Exception ex)
			{
				throw new RuntimeException("Errore eliminazione articolo", ex);
			}

			return "redirect:/articoli/";
		}
}
