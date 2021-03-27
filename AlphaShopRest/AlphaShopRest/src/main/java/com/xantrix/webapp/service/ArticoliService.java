package com.xantrix.webapp.service;

import java.util.List;

import com.xantrix.webapp.entities.Articoli;

public interface ArticoliService
{
	Articoli SelByBarcode (String Ean);
	
	Articoli SelByCodArt (String CodArt);
	
	List <Articoli> SelArticoliByFilter(String Filtro);
	
	List <Articoli> SelArticoliByFilter(String Filtro, String OrderBy, String Tipo);
		
	void InsArticolo(Articoli articolo);
	
	void DelArticolo(String CodArt);
}
