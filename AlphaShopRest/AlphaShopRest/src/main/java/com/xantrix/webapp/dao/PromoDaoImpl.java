package com.xantrix.webapp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xantrix.webapp.entities.Promo;

@Repository 
public class PromoDaoImpl extends AbstractDao<Promo, Integer>  
	implements PromoDao
{

	@Override
	public Promo SelByIdPromo(String IdPromo)
	{
		Promo retVal;
		
		String JPQL = "SELECT a FROM Promo a WHERE a.idPromo = :idPromo";
		
		retVal = (Promo) entityManager.createQuery(JPQL)
				.setParameter("idPromo", IdPromo)
				.getSingleResult();
		
		return retVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Promo> SelActivePromo(Date Data)
	{
		List<Promo> retVal;
		
		String JPQL = "SELECT a FROM Promo a JOIN a.dettPromo b where :Data between b.inizio and b.fine";
		
		retVal = entityManager.createQuery(JPQL)
				.setParameter("Data", Data)
				.getResultList();
				
		return retVal;
		
	}
	
	@Override
	public List<Promo> SelTutti()
	{
		return super.SelTutti();
	}

	@Override
	public void Salva(Promo promo)
	{
		super.Aggiorna(promo);
	}
	
	@Override
	public void Elimina(Promo promo)
	{
		super.Elimina(promo);
	}

	

}
