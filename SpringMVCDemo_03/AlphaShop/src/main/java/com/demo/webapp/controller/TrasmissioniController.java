package com.demo.webapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo.webapp.domain.Articoli;
import com.demo.webapp.domain.Rilevazioni;
import com.demo.webapp.domain.Trasmissioni;
import com.demo.webapp.repository.RilevazioniRepository;
import com.demo.webapp.service.GestTrasmissioni;
import com.demo.webapp.views.RilevazioniPdfView;

/**
 * 
 * @author Yusdel Morales
 * 
 * TODO Reading and Processing a file 
 */
@Controller
@RequestMapping("/trasmissioni")
public class TrasmissioniController 
{
	@Autowired
	private RilevazioniRepository rilevRepository;
	
	@Autowired
	private GestTrasmissioni gestTrasmissioni;
	
	private final String PathImages = "static\\trasmissioni\\";
	
	@RequestMapping(value = "/cerca/{filter}/download", method = RequestMethod.GET)
	public ModelAndView GetTrasmissioneByFilterDwld(@PathVariable("filter") String Filter, Model model)
	{
		List<Rilevazioni> recordset = rilevRepository.SelTrasmByFilter(Filter);
		
		/*
		 * NOTE
		 * We return as View the class that creates the PDF file.
		 * In this way we have the possibility to create different views for different models/data-set.
		 */
		ModelAndView mav = new ModelAndView();
		mav.addObject("Rilevazioni", recordset);
		mav.setView(new RilevazioniPdfView());
	
		return mav;
	}
	
	// form to upload file 
	@GetMapping(value = "/aggiungi")
	public String InsTrasmissione(Model model)
	{
		Trasmissioni trasmissione = new Trasmissioni();
		
		model.addAttribute("Titolo", "Inserimento Nuova Trasmissione");
		model.addAttribute("trasm", trasmissione);
		
		return "insTrasm";
	}
	
	@PostMapping(value="/aggiungi")
	public String GestInsTrasmissione(@ModelAttribute("Trasm") Trasmissioni trasmissione, 
			HttpServletRequest request)
	{
		
		MultipartFile fileTerminale = trasmissione.getFileTerminale();
		
		String NomeFile =  fileTerminale.getOriginalFilename();
		
		if (fileTerminale != null && !fileTerminale.isEmpty())
		{
			try
			{
				String rootDirectory = request.getSession().getServletContext().getRealPath("/");
				String PathName = rootDirectory + PathImages + NomeFile;
				
				fileTerminale.transferTo(new File(PathName));
				
				int retVal = gestTrasmissioni.GestFile(rootDirectory + PathImages, NomeFile);		 
			} 
			catch (Exception ex)
			{
				throw new RuntimeException("Errore trasferimento file", ex);
			}
		}
		
		return "redirect:/trasmissioni/cerca/" + NomeFile.substring(0,5) + "/download";
	}
}
