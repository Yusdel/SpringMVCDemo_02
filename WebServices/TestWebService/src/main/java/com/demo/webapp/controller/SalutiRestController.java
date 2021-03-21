package com.demo.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapp.service.PremiService;

@RestController
@RequestMapping("/api")
public class SalutiRestController {
	
	@GetMapping(value = "/test")
	public String getGreetings() {
		return ("Saluti, sono Yusdel!");
	}
	
	@Autowired
	@Qualifier("STD") // identificativo della implementazione dell'interfaccia
	PremiService premiserviceSTD;
	
	@Autowired
	@Qualifier("TOP")
	PremiService premiserviceTOP;
	
	@RequestMapping(value = "/cerca/codice/{idfidelity}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Double> getPremio(@PathVariable("idfidelity") String IdFidelity){
		
		Double premio = 0.00;
		
		if(IdFidelity.equals("1"))
			premio = premiserviceSTD.GetPremio();
		else
			premio = premiserviceTOP.GetPremio();
		
		return new ResponseEntity<Double>(premio, HttpStatus.OK);
	}
}
