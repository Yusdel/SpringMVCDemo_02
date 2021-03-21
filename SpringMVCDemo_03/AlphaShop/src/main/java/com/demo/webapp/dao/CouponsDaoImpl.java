package com.demo.webapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.webapp.entities.Coupons;

//TODO Generation Type Strategy
@Repository
public class CouponsDaoImpl extends AbstractDao<Coupons, Integer> 
	implements CouponsDao
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Coupons> SelByCodFidelity(String CodFidelity) 
	{
		List<Coupons> retVal;
		
		String JPQL = "SELECT a FROM Coupons a JOIN a.cliente b WHERE b.codFidelity = :CodFid";
		
		retVal = (List<Coupons>) entityManager.createQuery(JPQL)
				.setParameter("CodFid", CodFidelity)
				.getResultList();
	
		
		return retVal;
	}

	@Override
	public void Salva(Coupons coupon) 
	{
		super.Aggiorna(coupon);
	}
	 
}
