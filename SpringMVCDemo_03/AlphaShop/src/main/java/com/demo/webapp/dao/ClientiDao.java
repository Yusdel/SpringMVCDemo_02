package com.demo.webapp.dao;

import java.util.List;

import com.demo.webapp.entities.Clienti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

public interface ClientiDao  
{
	Clienti SelByCodFidelity(String CodFidelity);
	
	List<Clienti> SelTutti();
	
	List<Clienti> SelByComune(String Comune);
	
	List<Clienti> SelByNominativo(String Nome);
	
	List<Clienti> SelByBollini(int NumBollini, String Tipo);
	
	String SelLastCodFid();
	
	long QtaTotBollini();

	void Salva(Clienti cliente);

	void Aggiorna(Clienti cliente);

	void Elimina(Clienti cliente);

}
