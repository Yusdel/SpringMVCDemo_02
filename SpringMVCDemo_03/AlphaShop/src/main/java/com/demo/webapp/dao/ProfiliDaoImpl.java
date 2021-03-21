package com.demo.webapp.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.demo.webapp.entities.Profili;
 
/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */
 
@Repository
public class ProfiliDaoImpl extends AbstractDao<Profili, Integer> 
implements ProfiliDao
{
	@Override
	public Profili SelById(int id)
	{
		// call Abstract class method! For this we use "super."
		return super.SelById(id); // call the superclass method
	}

	
	@Override
	public List<Profili> SelByIdFidelity(String IdFidelity)
	{
		List<Profili> recordset = super.SelTutti();
		
		recordset = recordset.stream()
				.filter(u -> IdFidelity.contains(u.getUtente().getCodFidelity()))
				.collect(Collectors.toList());
		
		return recordset;
	}
	
	@Override
	public void Salva(Profili profilo)
	{
		 super.Inserisci(profilo);
	}

	@Override
	public void Aggiorna(Profili profilo)
	{
		super.Aggiorna(profilo);
		
	}
	
	@Override
	public void Elimina(Profili profilo)
	{
		//UTILIZZIAMO IL JPQL
		entityManager.createQuery("delete from Profili where id = :id")
		  .setParameter("id", profilo.getId())
		  .executeUpdate();
	}
	

}
