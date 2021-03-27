package com.xantrix.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.service.ArticoliService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/articoli")
@Slf4j // logger Lombok
@CrossOrigin(origins="http://localhost:4200") // Per ovviare al CORS (accetta le chiamate da localhost:4200)
public class ArticoliRestController
{

	@Autowired
	private ArticoliService articoliService;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	// ------------------- Ricerca Per Barcode ------------------------------------
	@GetMapping(value = "/cerca/ean/{barcode}", produces = "application/json")
	public ResponseEntity<Articoli> listArtByEan(@PathVariable("barcode") String Barcode)
			throws NotFoundException	
	{
		log.info("****** Otteniamo l'articolo con barcode " + Barcode + " *******");
		
		Articoli articolo = articoliService.SelByBarcode(Barcode);
		
		if (articolo == null)
		{
			String ErrMsg = String.format("Il barcode %s non è stato trovato!", Barcode);
			
			log.warn(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}
		
		return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
	}
	
	// ------------------- Ricerca Per Codice ------------------------------------
	@GetMapping(value = "/cerca/codice/{codart}", produces = "application/json")
	public ResponseEntity<Articoli> listArtByCodArt(@PathVariable("codart") String CodArt)  
			throws NotFoundException
	{
		log.info("****** Otteniamo l'articolo con codice " + CodArt + " *******");

		Articoli articolo = articoliService.SelByCodArt(CodArt);

		if (articolo == null)
		{
			String ErrMsg = String.format("L'articolo con codice %s non è stato trovato!", CodArt);
			
			log.warn(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}

		return new ResponseEntity<Articoli>(articolo, HttpStatus.OK);
	}
	
	// ------------------- Ricerca Per Descrizione ------------------------------------
	@RequestMapping(value = "/cerca/descrizione/{filter}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Articoli>> listArtByDesc(@PathVariable("filter") String Filter)
			throws NotFoundException
	{
		log.info("****** Otteniamo gli articoli con Descrizione: " + Filter + " *******");

		List<Articoli> articoli = articoliService.SelArticoliByFilter(Filter);

		if (articoli.size() == 0)
		{
			String ErrMsg = String.format("Non è stato trovato alcun articolo avente descrizione %s", Filter);
			
			log.warn(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}

		return new ResponseEntity<List<Articoli>>(articoli, HttpStatus.OK);
	}
	
	@PostMapping(value = "/inserisci")
	public ResponseEntity<?> createArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult) 
			throws BindingException, DuplicateException
	{
		log.info("Salviamo l'articolo con codice " + articolo.getCodArt());
		
		if (bindingResult.hasErrors())
		{
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			
			log.warn(MsgErr);

			throw new BindingException(MsgErr);
		}
		
		//Disabilitare se si vuole gestire anche la modifica 
		Articoli checkArt =  articoliService.SelByCodArt(articolo.getCodArt());
		
		if (checkArt != null)
		{
			String MsgErr = String.format("Articolo %s presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", articolo.getCodArt());
			
			log.warn(MsgErr);
			
			throw new DuplicateException(MsgErr);
		}
		
		articoliService.InsArticolo(articolo);
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		
		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Inserimento Articolo " + articolo.getCodArt() + " eseguito con successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	}
	
	/*
	 * ResponseEntity<?> = non c'è alcun valore generico di ritorno
	 */
	// ------------------- MODIFICA ARTICOLO ------------------------------------
	@RequestMapping(value = "/modifica", method = RequestMethod.PUT)
	public ResponseEntity<?> updateArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult,
				UriComponentsBuilder ucBuilder) throws BindingException,NotFoundException  
	{
		log.info("Modifichiamo l'articolo con codice " + articolo.getCodArt());
		
		if (bindingResult.hasErrors())
		{
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			
			log.warn(MsgErr);

			throw new BindingException(MsgErr);
		}
		
		Articoli checkArt =  articoliService.SelByCodArt(articolo.getCodArt());

		if (checkArt == null)
		{
			String MsgErr = String.format("Articolo %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo PUT", articolo.getCodArt());
			
			log.warn(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		
		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		articoliService.InsArticolo(articolo);
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Modifica Articolo " + articolo.getCodArt() + " Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	}

	// ------------------- ELIMINAZIONE ARTICOLO ------------------------------------
	@RequestMapping(value = "/elimina/{codart}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt)
			throws  Exception 
	{
		log.info("Eliminiamo l'articolo con codice " + CodArt);

		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		Articoli articolo = articoliService.SelByCodArt(CodArt);

		if (articolo == null)
		{
			String MsgErr = String.format("Articolo %s non presente in anagrafica!",CodArt);
			
			log.warn(MsgErr);
			
			throw new Exception(MsgErr);
		}

		articoliService.DelArticolo(CodArt);

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Articolo " + CodArt + " Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
