package com.demo.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.webapp.dao.ProfiliDao;
import com.demo.webapp.entities.Profili;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

@Service("profiliService")
@Transactional
public class ProfiliServiceImpl implements ProfiliService
{
	@Autowired
	private ProfiliDao profiliRepository;

	@Override
	public void Salva(Profili profilo)
	{
		profiliRepository.Salva(profilo);
	}

	@Override
	public void Elimina(Profili profilo)
	{
		profiliRepository.Elimina(profilo);
	}

	@Override
	public List<Profili> SelByIdFidelity(String IdFidelity)
	{
		return profiliRepository.SelByIdFidelity(IdFidelity);
	}

	@Override
	public void Aggiorna(Profili profilo)
	{
		profiliRepository.Aggiorna(profilo);
	}

	@Override
	public Profili SelById(int id)
	{
		return profiliRepository.SelById(id);
	}

}
