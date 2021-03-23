package com.demo.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.webapp.dao.UtentiDao;
import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

@Service("utentiService")
@Transactional
public class UtentiServiceImpl implements UtentiService
{

	@Autowired
	private UtentiDao utentiRepository;
	
	@Override
	public void Salva(Utenti utente)
	{
		utentiRepository.Salva(utente);
	}

	@Override
	public void Aggiorna(Utenti utente)
	{
		utentiRepository.Aggiorna(utente);
	}

	@Override
	public void Elimina(Utenti utente)
	{
		utentiRepository.Elimina(utente);
	}

	@Override
	public Utenti SelByIdFidelity(String idFidelity)
	{
		return utentiRepository.SelByIdFidelity(idFidelity);
	}

	@Override
	public Utenti SelByUserIdCodFid(String UserId, String CodFid) {
		
		return utentiRepository.SelByUserIdCodFid(UserId, CodFid);
	}

}
