package com.demo.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.webapp.dao.ClientiDao;
import com.demo.webapp.entities.Clienti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

@Service("clientiService") // Nominative notation
@Transactional // to indicates that all activities that will be performed by this class will have to undergo transaction. 
public class ClientiServiceImpl implements ClientiService
{

	@Autowired
	private ClientiDao clientiRepository;

	@Override
	public Clienti SelCliente(String CodFidelity)
	{
		Clienti cliente = clientiRepository.SelByCodFidelity(CodFidelity);
		
		return cliente;
	}
	
	@Override
	public List<Clienti> SelByComune(String Comune)
	{
		return clientiRepository.SelByComune(Comune);
	}
	
	@Override
	public List<Clienti> SelByBollini(int NumBollini, String Tipo)
	{
		 
		return clientiRepository.SelByBollini(NumBollini, Tipo);
	}

	@Override
	public String SelLastCodFid()
	{
		return clientiRepository.SelLastCodFid();
	}
	
	@Override
	public void Salva(Clienti cliente)
	{
		clientiRepository.Salva(cliente);
	}

	@Override
	public void Aggiorna(Clienti cliente)
	{
		clientiRepository.Aggiorna(cliente);
	}

	@Override
	public List<Clienti> SelTutti()
	{
		return clientiRepository.SelTutti();
	}

	@Override
	public void Elimina(Clienti cliente)
	{
		clientiRepository.Elimina(cliente);
	}

	@Override
	public List<Clienti> SelByNominativo(String Nominativo)
	{
		return clientiRepository.SelByNominativo(Nominativo);
	}

	@Override
	public long QtaTotBollini()
	{
		return clientiRepository.QtaTotBollini();
	}

	

}
