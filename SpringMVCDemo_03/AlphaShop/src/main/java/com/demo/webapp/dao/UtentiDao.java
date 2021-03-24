package com.demo.webapp.dao;

import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

public interface UtentiDao
{
	Utenti SelByIdFidelity(String id);
	
	Utenti SelByUserIdCodFid(String UserId, String CodFid);
	
	void Salva(Utenti utente);
	
	// TODO Spring Security
	void SalvaAdminUser(String Password);
	
	void Aggiorna(Utenti utente);

	void Elimina(Utenti utente);

	// TODO Custom Validation if client exist
	Utenti SelByUserId(String UserId);
	
}
