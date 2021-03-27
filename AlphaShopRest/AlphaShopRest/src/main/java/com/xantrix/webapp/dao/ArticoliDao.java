package com.xantrix.webapp.dao;

import java.util.List;

import com.xantrix.webapp.entities.Articoli;

public interface ArticoliDao
{
	Articoli SelByCodArt(String CodArt);
	
	List<Articoli> SelByDescrizione(String Descrizione);
	
	Articoli SelByEan(String Ean);
	
	void Salva(Articoli Articolo);

	void Aggiorna(Articoli Articolo);

	void Elimina(Articoli Articolo);
}
