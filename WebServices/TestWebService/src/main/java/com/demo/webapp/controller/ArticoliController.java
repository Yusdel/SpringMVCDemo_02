package com.demo.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapp.entities.Articoli;

@RestController
@RequestMapping("/api/articoli")
public class ArticoliController 
{
	private static final Logger logger = LoggerFactory.getLogger(ArticoliController.class);
	
	// metodo che simula l'aquisizione dei dati dal db
	private List<Articoli> initData()
	{
		List<Articoli> articoli = new ArrayList<Articoli>();

		articoli.add(new Articoli("014600301","BARILLA FARINA 1 KG","PZ",24,1.0,1.09));
		articoli.add(new Articoli("013500121","BARILLA PASTA GR.500 N.70 1/2 PENNE","PZ",30,0.500,1.30));
		articoli.add(new Articoli("007686402","FINDUS FIOR DI NASELLO 300 GR","PZ",8,0.300,6.46));
		articoli.add(new Articoli("057549001","FINDUS CROCCOLE 400 GR","PZ",12,0.400,5.97));
		articoli.add(new Articoli("002000301","ACQUA ULIVETO 15 LT","PZ",6,1.5,0.87));

		return articoli;
	}
	
	@GetMapping(value = "/cerca/codice/{codart}", produces = "application/json") // produces = tipo di ritorno
	public ResponseEntity<Articoli> listArtByCodArt(@PathVariable("codart") String CodArt) 
	{
		logger.info("****** Otteniamo l'articolo con codice " + CodArt + " *******");

	
		List<Articoli> articolo = this.initData()
			.stream()
			.filter(u -> u.getCodArt().equals(CodArt))
			.collect(Collectors.toList());

		
		return new ResponseEntity<Articoli>(articolo.get(0), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/cerca/descrizione/{filter}", produces = "application/json")
	public ResponseEntity<List<Articoli>> listArtByDesc(@PathVariable("filter") String Filter)
	{
		logger.info("****** Otteniamo l'articolo con descrizione " + Filter + " *******");

		List<Articoli> articoli = this.initData()
			.stream()
			.filter(u -> u.getDescrizione().toUpperCase().contains(Filter.toUpperCase()))
			.collect(Collectors.toList());

		return new ResponseEntity<List<Articoli>>(articoli, HttpStatus.OK);
		
	}
}
