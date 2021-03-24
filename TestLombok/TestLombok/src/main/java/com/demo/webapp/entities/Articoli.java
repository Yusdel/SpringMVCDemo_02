package com.demo.webapp.entities;

import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Data
@Log // = private static final Logger Logger = LoggerFactory.getLogger(Articoli.class)
public class Articoli {
			
	@NonNull
	private String codArt;
	@NonNull
	private String descrizione;
	private String um;
	private String CodStato;
	private Integer pzCart;
	private double pesoNetto;
	private String idStatoArt;
	
	//public Articoli() {};
	
	/*
	 * Without Lombok we must add all getter and setter and empty constructor 
	 */
}
