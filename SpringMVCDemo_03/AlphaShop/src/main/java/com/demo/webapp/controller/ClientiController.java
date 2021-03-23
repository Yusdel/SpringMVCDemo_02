package com.demo.webapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.webapp.service.ClientiService;
import com.demo.webapp.service.ProfiliService;
import com.demo.webapp.service.UtentiService;
import com.demo.webapp.entities.Cards;
import com.demo.webapp.entities.Clienti;
import com.demo.webapp.entities.Profili;
import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 * 
 * Nota. Add new Cliente parameter Provincia needed 2 char only
 */

@Controller
@RequestMapping("/clienti")
public class ClientiController 
{
	// slf4j Framework
	private static final Logger logger = LoggerFactory.getLogger(ClientiController.class);
	
	@Autowired
	private ClientiService clientiService;
	
	@Autowired
	private UtentiService utentiService;

	@Autowired
	private ProfiliService profiliService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*
	 * Code manipulation Instead Query Specify through Lambda Function
	 * Get one time all data and then manipulate in Java instead of execute different specified Queries
	 * 
	 * Pro Vs Contro
	 * Java : Consumes more memory but reduce the Traffic between Application and Database
	 * Query : More work is given to Database
	 */
	List<Clienti> MainRecordset;
	
	private String OrderType = "DESC";
	private int OrderBy = 0;
	
	List<PagingData> Pages = new ArrayList<PagingData>();
	private int PageNum = 1;
	private int RecForPage = 10;
	
	private Date date = new Date();
	//private DateFormat date = new SimpleDateFormat("yyyy-mm-dd");

	private boolean IsClienti = true;
	private boolean IsSaved = false;
	
	
	private void GetAllClienti()
	{
		MainRecordset = clientiService.SelTutti();
	}
		
	@GetMapping
	public String GetClienti(Model model)
	{
		logger.info("Otteniamo tutti i clienti");
		
		List<Clienti> recordset = null;
		long NumRecords = 0;
		
		GetAllClienti();
		
		if (MainRecordset != null)
		{
			 NumRecords = MainRecordset.size();
			
			 recordset = MainRecordset
					 .stream()
					 .filter(u -> !u.getCodFidelity().equals("-1")) // get all those with CodFidelity != -1
					 .sorted(Comparator.comparing(Clienti::getCodFidelity).reversed()) // order the collection by CodFidelity inverse
					 .skip(0) // get (start) from 0 (to get only first 10 records)
					 .limit(RecForPage) // to 10 (to get only first 10 records)
					 .collect(Collectors.toList());
		}
		
		logger.info("Numero di record per pagina: " + RecForPage);
		
		setPages(PageNum, NumRecords);
		
		model.addAttribute("Titolo", "Ricerca Clienti");
		model.addAttribute("Titolo2", "Risultati Ricerca ");
		model.addAttribute("NumRecords", NumRecords);
		model.addAttribute("clienti", recordset);
		model.addAttribute("filter", "");
		model.addAttribute("OrderType", OrderType);
		model.addAttribute("OrderBy", OrderBy);
		model.addAttribute("PageNum", PageNum);
		model.addAttribute("RecPage", RecForPage);
		model.addAttribute("Pages", Pages);
		model.addAttribute("IsClienti", IsClienti);
		model.addAttribute("PageLink", getPageLink(""));

		return "clienti";
		
		
	}
	
	@GetMapping(value = "/search")
	public String SearchItem(@RequestParam("filter") String pSearchTerm, ModelMap model)
	{
		List<Clienti> recordset;
		
		long BolliniByFilter = 0;
		long BolliniTot = 0;
		
		PageNum = 1;
		
		recordset = clientiService.SelByNominativo(pSearchTerm)
				.stream()
				.filter(u -> !u.getCodFidelity().equals("-1"))
				.sorted(Comparator.comparing(Clienti::getCodFidelity))
				.collect(Collectors.toList());
		
		LongSummaryStatistics BolliniStatistics = recordset
				.stream()
				.filter(u -> u.getCard() != null)
				.collect(Collectors.summarizingLong(p -> p.getCard().getBollini()));
		
		long NumRecords = BolliniStatistics.getCount();
		
		BolliniByFilter = BolliniStatistics.getSum();
		BolliniTot = clientiService.QtaTotBollini();
		
		recordset = recordset.stream()
				.skip(0)
				.limit(RecForPage)
				.collect(Collectors.toList());
		
		setPages(PageNum, NumRecords);
		 
		model.addAttribute("Titolo", "Ricerca Clienti");
		model.addAttribute("Titolo2", "Risultati Ricerca ");
		model.addAttribute("NumRecords", NumRecords);
		model.addAttribute("clienti", recordset);
		model.addAttribute("filter", pSearchTerm);
		model.addAttribute("OrderType", OrderType);
		model.addAttribute("OrderBy", OrderBy);
		model.addAttribute("PageNum", PageNum);
		model.addAttribute("RecPage", RecForPage);
		model.addAttribute("Pages", Pages);
		model.addAttribute("IsClienti", IsClienti);
		model.addAttribute("BolFil", BolliniByFilter);
		model.addAttribute("BolTot", BolliniTot);
		model.addAttribute("PageLink", getPageLink(pSearchTerm));

		return "clienti";
		
	}
	
	//http://localhost:8090/alphashop/clienti/cerca/parametri;filtro=BIANCHI,0;orderby=0,DESC,0;paging=1,10,0
	@GetMapping(value = "/cerca/{parametri}")
	public String GetClientiByFilterMatrix(@MatrixVariable(pathVar = "parametri") Map<String, List<String>> parametri, Model model)
	{
		long NumRecords = 0;
		long SkipValue = 0;
		long BolliniByFilter = 0;
		long BolliniTot = 0;

		String Filter = "";
		String TypeFilter = "";

		boolean ChangeOrder = false;

		List<Clienti> recordset;
		
		//PARAMETRI FILTRO
		List<String> ParamFiltro = parametri.get("filtro");
		if (ParamFiltro != null)
		{
			Filter = ParamFiltro.get(0);  //Filtro applicato 
			TypeFilter = ParamFiltro.get(1); //Classe filtr (DA ABILITARE)
		}
		
		//PARAMETRI ORDINAMENTO
		List<String> ParamOrderBy = parametri.get("orderby");
		if (ParamOrderBy != null)
		{
			try
			{
				OrderBy = Integer.parseInt(ParamOrderBy.get(0)); //Colonna del filtro 
				OrderType = ParamOrderBy.get(1); //Tipo di ordinamento (ASC,DESC)
				ChangeOrder = (ParamOrderBy.get(2).equals("1")) ? true : false; //Inversione dell'ordinamento
			} 
			catch (NumberFormatException ex)
			{
				OrderBy = 0;
			}
		}
		
		//PARAMETRI PAGING
		List<String> ParamPaging = parametri.get("paging");
		if (ParamPaging != null)
		{
			try
			{
				PageNum = Integer.parseInt(ParamPaging.get(0)); //Numero della pagina
				RecForPage = Integer.parseInt(ParamPaging.get(1)); //Record per pagina
				int DiffPage = Integer.parseInt(ParamPaging.get(2)); 

				if (PageNum >= 1)
					PageNum += DiffPage;
				else
					PageNum = 1;

			} 
			catch (NumberFormatException ex)
			{
				PageNum = 1;
				RecForPage = 10;
			}
		}
		
		if (Filter.length() > 0)
			recordset = clientiService.SelByNominativo(Filter); //Otteniamo i clienti per nominativo
		else
		{
			if (MainRecordset == null)
				GetAllClienti(); //otteniamo tutti i clienti

			recordset = MainRecordset;
		}
		
		if (recordset != null)
		{
			recordset = recordset
					.stream()
					.filter(u -> !u.getCodFidelity().equals("-1"))
					.collect(Collectors.toList());
			
			// LongSummaryStatistics = To perform calculations and groupings
			LongSummaryStatistics BolliniStatistics = recordset
					.stream()
					.filter(u -> u.getCard() != null)
					.collect(Collectors.summarizingLong(p -> p.getCard().getBollini()));
			
			NumRecords = BolliniStatistics.getCount();
			BolliniByFilter = BolliniStatistics.getSum();
			BolliniTot = clientiService.QtaTotBollini();
			
			SkipValue = GetSkipValue(PageNum, NumRecords); // entry point of lambda
			
			recordset = GestOrderRecordset(recordset, OrderBy, ChangeOrder)
					.stream()
					.skip(SkipValue)
					.limit(RecForPage)
					.collect(Collectors.toList());
			
		}
		
		setPages(PageNum, NumRecords);
		
		model.addAttribute("Titolo", "Ricerca Clienti");
		model.addAttribute("Titolo2", "Risultati Ricerca ");
		model.addAttribute("NumRecords", NumRecords);
		model.addAttribute("clienti", recordset);
		model.addAttribute("filter", Filter);
		model.addAttribute("OrderType", OrderType);
		model.addAttribute("OrderBy", OrderBy);
		model.addAttribute("PageNum", PageNum);
		model.addAttribute("RecPage", RecForPage);
		model.addAttribute("Pages", Pages);
		model.addAttribute("IsClienti", IsClienti);
		model.addAttribute("BolTot", BolliniTot);
		model.addAttribute("BolFil", BolliniByFilter);
		model.addAttribute("PageLink", getPageLink(Filter));
		
		return "clienti";

	}
	
	@RequestMapping(value = "/cerca/comune", method = RequestMethod.GET)
	public String GetClientiByComune(@RequestParam("filter") String Comune, Model model)
	{
		List<Clienti> recordset;
		
		MainRecordset = clientiService.SelByComune(Comune);
		
		MainRecordset = MainRecordset
				.stream()
				.filter(u -> !u.getCodFidelity().equals("-1"))
				.sorted(Comparator.comparing(Clienti::getCodFidelity))
				.collect(Collectors.toList());

		LongSummaryStatistics BolliniStatistics = MainRecordset
				.stream()
				.filter(u -> u.getCard() != null)
				.collect(Collectors.summarizingLong(p -> p.getCard().getBollini()));
		
		long BolliniByFilter = BolliniStatistics.getSum();
		long NumRecords = BolliniStatistics.getCount();
		
		long BolliniTot = clientiService.QtaTotBollini();
		
		// paging system
		recordset = MainRecordset.stream()
				.skip(0).limit(RecForPage)
				.collect(Collectors.toList());
		
		setPages(PageNum, NumRecords);
		

		model.addAttribute("Titolo", "Ricerca Clienti per Comune");
		model.addAttribute("Titolo2", "Risultati Ricerca ");
		model.addAttribute("NumRecords", NumRecords);
		model.addAttribute("clienti", recordset);
		model.addAttribute("filter", "");
		model.addAttribute("OrderType", OrderType);
		model.addAttribute("OrderBy", OrderBy);
		model.addAttribute("PageNum", PageNum);
		model.addAttribute("RecPage", RecForPage);
		model.addAttribute("Pages", Pages);
		model.addAttribute("IsClienti", IsClienti);
		model.addAttribute("BolTot", BolliniTot);
		model.addAttribute("BolFil", BolliniByFilter);
		model.addAttribute("PageLink", getPageLink(""));

		return "clienti"; 
	}
	
	@GetMapping(value = "/cerca/bollini")
	public String GetClientiByBollini(@RequestParam("filter") int Bollini, @RequestParam("type") String Tipo, Model model)
	{
		MainRecordset = clientiService.SelByBollini(Bollini, Tipo);
		
		List<Clienti> recordset = MainRecordset;
		
		recordset = recordset
				.stream()
				.filter(u -> !u.getCodFidelity().equals("-1"))
				//.sorted(Comparator.comparing(Clienti::getCodFidelity))
				.collect(Collectors.toList());

		LongSummaryStatistics BolliniStatistics = recordset
				.stream()
				.filter(u -> u.getCard() != null)
				.collect(Collectors.summarizingLong(p -> p.getCard().getBollini()));
		
		long BolliniByFilter = BolliniStatistics.getSum();
		long NumRecords = BolliniStatistics.getCount();
		
		long BolliniTot = clientiService.QtaTotBollini();

		recordset = recordset.stream()
				.skip(0)
				.limit(RecForPage)
				.collect(Collectors.toList());
		
		setPages(PageNum,NumRecords);
		
		model.addAttribute("Titolo", "Ricerca Clienti per limite Bollini");
		model.addAttribute("Titolo2", "Risultati Ricerca ");
		model.addAttribute("NumRecords", NumRecords);
		model.addAttribute("clienti", recordset);
		model.addAttribute("filter", "");
		model.addAttribute("OrderType", OrderType);
		model.addAttribute("OrderBy", OrderBy);
		model.addAttribute("PageNum", PageNum);
		model.addAttribute("RecPage", RecForPage);
		model.addAttribute("Pages", Pages);
		model.addAttribute("IsClienti", IsClienti);
		model.addAttribute("BolTot", BolliniTot);
		model.addAttribute("BolFil", BolliniByFilter);
		model.addAttribute("PageLink", getPageLink(""));

		return "clienti"; 
	}
	
	@ModelAttribute("pagingLink")
	public List<PagingLink> getPageLink(String Filter)
	{
		List<PagingLink> pageLink = new ArrayList<PagingLink>();
		
		String Host = "http://localhost:8080/"; //TODO Ottenere le informazioni del server e della porta
		
		pageLink.add(new PagingLink(Host + "AlphaShop/clienti/cerca/parametri;filtro=" + Filter + ",0;orderby=0,DESC,0;paging=1,10,0", "10"));
		pageLink.add(new PagingLink(Host + "AlphaShop/clienti/cerca/parametri;filtro=" + Filter + ",0;orderby=0,DESC,0;paging=1,20,0", "20"));
		pageLink.add(new PagingLink(Host + "AlphaShop/clienti/cerca/parametri;filtro=" + Filter + ",0;orderby=0,DESC,0;paging=1,30,0", "30"));
		pageLink.add(new PagingLink(Host + "AlphaShop/clienti/cerca/parametri;filtro=" + Filter + ",0;orderby=0,DESC,0;paging=1,50,0", "50"));
		
		return pageLink;
	}
	
	@GetMapping(value = "/aggiungi")
	public String InsCliente(Model model)
	{
		Clienti cliente = new Clienti();
		
		int LastCodFid = Integer.parseInt(clientiService.SelLastCodFid()); 
		
		cliente.setCodFidelity(Integer.toString(LastCodFid + 1));
		
		model.addAttribute("Titolo", "Inserimento Nuovo Cliente");
		model.addAttribute("Cliente", cliente);
		model.addAttribute("Utente", getUtente());
		model.addAttribute("Profilo", getProfilo());
		model.addAttribute("edit", false);
		model.addAttribute("saved", false);
		 
		return "insCliente";
	}
	
	@ModelAttribute("Utente")
	public Utenti getUtente()
	{
		return new Utenti();
	}

	@ModelAttribute("Profilo")
	public Profili getProfilo()
	{
		return new Profili();
	}
	
	@PostMapping(value = "/aggiungi")
	public String GestInsCliente(@Valid @ModelAttribute("Cliente") Clienti cliente, BindingResult result, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
	{
		//Date date = new Date();
		
		if (result.hasErrors())
		{
			return "insCliente";
		}
		
		cliente.setDataCreaz(date);
		
		clientiService.Salva(cliente);
		
		/*
		 * addFlashAttribute = to preserve attributes during a redirect, otherwise they would be lost
		 */
		redirectAttributes.addFlashAttribute("saved", true);
		
		return "redirect:/clienti/modifica/" + cliente.getCodFidelity().trim();
	}
	
	@RequestMapping(value = "/modifica/{idCliente}", method = RequestMethod.GET)
	public String UpdClienti(@PathVariable("idCliente") String IdCliente, Model model)
	{
		Clienti cliente = clientiService.SelCliente(IdCliente);

		Utenti utente = utentiService.SelByIdFidelity(IdCliente);
		utente.setCodFidelity(IdCliente);
		utente.setPwd("");

		model.addAttribute("Titolo", "Modifica Cliente");
		model.addAttribute("Cliente", cliente);
		model.addAttribute("Utente", utente);
		model.addAttribute("Profilo", getProfilo());
		model.addAttribute("edit", true);
		model.addAttribute("saved", IsSaved ? true : false);

		IsSaved = false;

		return "insCliente";
	}
	
	@RequestMapping(value = "/modifica/{idCliente}", method = RequestMethod.POST)
	public String GestUpdClienti(
			@Valid @ModelAttribute("Cliente") Clienti cliente,
			BindingResult result, //OBBLIGATORIO IN QUESTA POSIZIONE
			@Valid @ModelAttribute("Utente") Utenti utente, // @Valid to TODO Custom Validation if client exist
			BindingResult result2,
			@ModelAttribute("Profilo") Profili profilo,
			@PathVariable("idCliente") String IdCliente, 
			 Model model, HttpServletRequest request)
	{
		Set<Profili> profili = new HashSet<>();
		
		//Modifica Dati Cliente
		if (cliente.getNome() != null)
		{
			if (result.hasErrors())
			{
				return "insCliente";
			}
			
			cliente.setDataCreaz(date);
			
			clientiService.Aggiorna(cliente);
		}
		
		//Modifica Dati Utente
		if (utente.getUserId() != null)
		{
			// TODO Custom Validation if client exist
			if (result2.hasErrors())
			{
				utente.setPwd("");
				
				model.addAttribute("Titolo", "Modifica Cliente");
				model.addAttribute("Cliente",clientiService.SelCliente(IdCliente));
				model.addAttribute("edit", true);
				model.addAttribute("error", true);
				
				return "insCliente";
			}
			
			//CRYPT PASSWORD
			utente.setPwd(passwordEncoder.encode(utente.getPwd()));
			
			Utenti test = utentiService.SelByIdFidelity(utente.getCodFidelity());

			profili.add(new Profili("USER", utente));

			utente.setProfili(profili);

			if (test.getUserId() != null)
				utentiService.Aggiorna(utente);
			else
				utentiService.Salva(utente);
		}

		//Modifica Profilo
		if (profilo.getTipo() != null)
		{

			Utenti Utente = new Utenti(IdCliente);
			String Tipo = profilo.getTipo();
			
			Profili newProfilo = new Profili(Tipo, Utente);

			profiliService.Salva(newProfilo);
		}

		IsSaved = true;

		return "redirect:/clienti/modifica/" + IdCliente;
	}
	
	@RequestMapping(value = "/elimina/{idCliente}", method = RequestMethod.GET)
	public String DelClienti(@PathVariable("idCliente") String IdCliente, Model model)
	{
		try
		{
			logger.info("Eliminazione Cliente con Codice: " + IdCliente);

			// -1 = non fidelity client
			if (!IdCliente.equals("-1"))
			{
				Clienti cliente = clientiService.SelCliente(IdCliente);
				clientiService.Elimina(cliente);
			}
			
		} 
		catch (Exception ex)
		{
			logger.debug("ERRORE: " + ex.getMessage());
		}

		return "redirect:/clienti/";

	}
	
	// http://localhost:8080/alphashop/clienti/modifica/delprofilo/67100950/14
	@RequestMapping(value = { "modifica/delprofilo/{idCliente}/{id}" }, method = RequestMethod.GET)
	public String DelProfilo(@PathVariable("idCliente") int IdCliente, @PathVariable("id") int Id)
	{
		Profili Profilo = profiliService.SelById(Id);

		profiliService.Elimina(Profilo);

		return "redirect:/clienti/modifica/" + IdCliente;
	}

	
	
	//Sistema di riordino del recordset
	private List<Clienti> GestOrderRecordset(List<Clienti> recordset, int OrderBy, boolean ChangeOrder)
	{
		if (ChangeOrder)
			OrderType = (OrderType.toUpperCase().equals("ASC")) ? "DESC" : "ASC";

		switch (OrderBy)
		{
			case 0: //colonna 1
				if (OrderType.toUpperCase().equals("ASC"))
				{
					recordset = recordset.stream().sorted(Comparator.comparing(Clienti::getCodFidelity))
							.collect(Collectors.toList());
				} else
				{
					recordset = recordset.stream().sorted(Comparator.comparing(Clienti::getCodFidelity).reversed())
							.collect(Collectors.toList());
				}
				break;
			case 1: //colonna 2
				if (OrderType.toUpperCase().equals("ASC"))
				{
					recordset = recordset.stream().sorted(Comparator.comparing(Clienti::getCognome))
							.collect(Collectors.toList());
				} else
				{
					recordset = recordset.stream().sorted(Comparator.comparing(Clienti::getCognome).reversed())
							.collect(Collectors.toList());
				}
				break;
			case 3: //colonna 4
	
				Comparator<Clienti> byBollini = (e1, e2) -> (Integer.compare(getBollini(e1.getCard()), getBollini(e2.getCard())));
	
				if (OrderType.toUpperCase().equals("ASC"))
				{
	
					recordset = recordset.stream().sorted(byBollini).collect(Collectors.toList());
				} 
				else
				{
	
					recordset = recordset.stream().sorted(byBollini.reversed()).collect(Collectors.toList());
				}
				break;
		}

		return recordset;
	}

	private int getBollini(Cards card)
	{
		return (card == null) ? 0 : card.getBollini();
	}

	//Calcolo del punto di partenza del recordset
	private int GetSkipValue(int PageNum, long numRecords)
	{
		int retVal = 0;

		if (numRecords > RecForPage)
		{
			
			int NumTotPage = (int) Math.ceil((double)numRecords / (double)RecForPage);

			if (PageNum <= NumTotPage)
				retVal = (PageNum - 1) * RecForPage;
		}

		return retVal;
	}
	
	//Metodo di creazione classi Pages
	private void setPages(int Page, long numRecords)
	{
		int Min = 1;
		int ValMin = 1;
		int Max = 5;
		
		if (Pages != null)
			Pages.clear();
		
		int Group = (int) Math.ceil((double)Page / 5);
		
		Max = Group * 5;
		Min = (Max - 5 == 0) ? 1 : Max - 4;
		
		ValMin = Min;
		
		int MaxPages = (numRecords > 0) ? (int) Math.ceil((double)numRecords / (double)RecForPage) : 5;
		
		while (Min <= Max)
		{
			if (Min > MaxPages)
				break;
			
			Pages.add(new PagingData(Min,false));
			
			Min++;
		}
		
		try
		{
			if (Page - ValMin > 0)
				Pages.get(Page - ValMin).setIsSelected(true);
			else
				Pages.get(0).setIsSelected(true);
		}
		catch (Exception ex)
		{
			Pages.get(0).setIsSelected(true);
		}
	}
	
	
}
