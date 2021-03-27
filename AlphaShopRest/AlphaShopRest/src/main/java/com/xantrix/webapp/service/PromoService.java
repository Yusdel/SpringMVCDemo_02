package com.xantrix.webapp.service;

import java.util.List;

import com.xantrix.webapp.entities.Promo;

public interface PromoService
{
	public List<Promo> SelTutti();
	
	public Promo SelByIdPromo(String IdPromo);
		
	public List<Promo> SelActivePromo();
	
	public void InsPromo(Promo promo);
	
	public void DelPromo(Promo promo);
	
}
