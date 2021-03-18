package com.demo.webapp.dao;

import com.demo.webapp.entities.Utenti;

public interface UtentiDao
{
	Utenti SelByIdFidelity(String id);
	
	void Salva(Utenti utente);
	
	void Aggiorna(Utenti utente);

	void Elimina(Utenti utente);
}
