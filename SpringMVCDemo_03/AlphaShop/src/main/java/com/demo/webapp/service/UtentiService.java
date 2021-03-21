package com.demo.webapp.service;

import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

public interface UtentiService
{
	Utenti SelByIdFidelity(String idFidelity);
	
	void Salva(Utenti utente);

	void Aggiorna(Utenti utente);

	void Elimina(Utenti utente);
}
