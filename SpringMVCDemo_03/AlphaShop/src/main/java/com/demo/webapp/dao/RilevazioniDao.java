package com.demo.webapp.dao;

import java.util.List;

import com.demo.webapp.entities.Rilevazioni;

public interface RilevazioniDao 
{
	List<Rilevazioni> SelByIdTerminale(String IdTerminale);
	
	void Salva(Rilevazioni rilevazioni);
	
	void Elimina(String IdTerminale);
	
	
}
