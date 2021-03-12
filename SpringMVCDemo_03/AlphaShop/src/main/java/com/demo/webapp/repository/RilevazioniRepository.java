package com.demo.webapp.repository;

import java.util.List;

import com.demo.webapp.domain.Rilevazioni;

/**
 * 
 * @author Yusdel Morales
 * 
 * TODO Reading and Processing a file 
 */
public interface RilevazioniRepository 
{
	List <Rilevazioni> SelTrasmByFilter(String Filtro);
		
	void InsTrasm(Rilevazioni Rilev);
	
	void DelTrasm(String IdTerminale);
}

