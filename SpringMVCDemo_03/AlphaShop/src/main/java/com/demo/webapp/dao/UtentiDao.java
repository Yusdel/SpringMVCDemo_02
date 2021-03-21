package com.demo.webapp.dao;

import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

public interface UtentiDao
{
	Utenti SelByIdFidelity(String id);
	
	void Salva(Utenti utente);
	
	void Aggiorna(Utenti utente);

	void Elimina(Utenti utente);

	// TODO Custom Validation if client exist
	Utenti SelByUserId(String UserId);
}
