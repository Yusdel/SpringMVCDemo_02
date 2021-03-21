package com.demo.webapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.webapp.entities.Rilevazioni;

@Repository
public class RilevazioniDaoImpl extends AbstractDao<Rilevazioni, Integer> 
	implements RilevazioniDao 
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Rilevazioni> SelByIdTerminale(String IdTerminale) 
	{
		List<Rilevazioni> retVal;
		
		String JPQL = "SELECT a FROM Rilevazioni a WHERE a.idTerminale = :idTerminale";
		
		retVal = entityManager.createQuery(JPQL)
				.setParameter("idTerminale", IdTerminale)
				.getResultList();
		
		return retVal;
	}

	@Override
	public void Salva(Rilevazioni rilevazioni) 
	{
		super.entityManager.persist(rilevazioni);
	}
	
	@Override
	public void Elimina(String IdTerminale) 
	{
		
		String JPQL = "DELETE FROM Rilevazioni a WHERE a.idTerminale = :idTerminale";
		
		entityManager.createQuery(JPQL)
			.setParameter("idTerminale", IdTerminale)
			.executeUpdate();
		
	}
	
	
	

}
