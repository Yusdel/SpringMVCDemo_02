package com.demo.webapp.service;

import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

public interface UtentiService
{
	Utenti SelByIdFidelity(String idFidelity);
	
	// TODO Spring Security
	Utenti SelByUserIdCodFid(String UserId, String CodFid);
	
	void Salva(Utenti utente);
	
	// TODO Spring Security
	void SalvaAdminUser(String Password);

	void Aggiorna(Utenti utente);

	void Elimina(Utenti utente);
}
