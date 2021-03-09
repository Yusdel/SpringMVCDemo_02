package com.demo.webapp.repository;

import java.util.List;

import com.demo.webapp.domain.Articoli;

public interface ArticoliRepository {
	
	List <Articoli> SelArticoliByFilter(String Filtro);
	
	List <Articoli> SelArticoliByFilter(String Filtro, String OrderBy, String Tipo);
	
	/*Insert and Modify*/
	void InsArticolo(Articoli articolo);
	
	void DelArticolo(String CodArt);
}
